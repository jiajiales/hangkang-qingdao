package com.hotcomm.data.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.service.DataPushReady;
import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.entity.sys.Customer;
import com.hotcomm.data.bean.entity.sys.Member;
import com.hotcomm.data.bean.enums.MemberEnum;
import com.hotcomm.data.bean.enums.MemberEnum.MemberDeleteStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberType;
import com.hotcomm.data.bean.enums.MemberVhostEnum.MemberVhostStatus;
import com.hotcomm.data.bean.enums.QueueEnum.QueueType;
import com.hotcomm.data.bean.enums.RoleEnum.RoleType;
import com.hotcomm.data.bean.params.sys.CustomerPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberVhostParams;
import com.hotcomm.data.bean.vo.sys.CustomerVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.DeviceGroupMapper;
import com.hotcomm.data.db.DeviceGroupMemberMapper;
import com.hotcomm.data.db.DeviceMapper;
import com.hotcomm.data.db.MemberMapper;
import com.hotcomm.data.db.MemberVhostMapper;
import com.hotcomm.data.db.QueueMapper;
import com.hotcomm.data.model.rabbitmq.RabbitQueueManager;
import com.hotcomm.data.model.rabbitmq.RabbitRestTool;
import com.hotcomm.data.model.rabbitmq.RabbitVhostManager;
import com.hotcomm.data.model.rabbitmq.common.QueueCustomer;
import com.hotcomm.data.service.CustomerService;
import com.hotcomm.data.service.DataPushReadyService;
import com.hotcomm.data.service.DeviceGroupService;
import com.hotcomm.data.service.QueueService;
import com.hotcomm.data.service.UserService;
import com.hotcomm.data.service.common.AbstractFunServiceImpl;
import com.hotcomm.data.utils.LogUtil;
import com.hotcomm.data.utils.UUIDUtils;
import com.hotcomm.framework.utils.CodeUtils;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class CustomerServiceImpl extends AbstractFunServiceImpl<CustomerVO, Customer> implements CustomerService {

	@Resource
	private MemberMapper memberMapper;

	@Resource
	private MemberVhostMapper vhostMapper;

	@Resource
	private QueueMapper queueMapper;

	@Resource
	private DeviceMapper deviceMapper;

	@Resource
	private DeviceGroupMapper deviceGroupMapper;

	@Resource
	private DeviceGroupMemberMapper deviceGroupMemberMapper;

	@Autowired
	private RabbitVhostManager rabbitVhostManager;

	@Autowired
	private QueueService queueService;

	@Autowired
	private RabbitRestTool rabbitRestTool;

	@Autowired
	private RabbitQueueManager rabbitQueueManager;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;

	@Autowired
	UserService userService;

	@Autowired
	DeviceGroupService deviceGroupService;
	
	@Autowired
	DataPushReadyService dataPushReadyService;
	
	@Override
	public List<MemberVhost> listAuthors() throws HKException {
		return vhostMapper.listActive();
	}

	/*
	 * 客户 创建
	 */
	@Override
	@Transactional
	public Integer addBean(MemberParams params) throws HKException {
		// 判断用户名是否已存在
		if (memberMapper.selectOne(new Member(params.getMemberName())) != null)
			throw manager.create("EC00009");

		Integer customerId = 0;
		Member member = new Member();

		BeanUtils.copyProperties(params, member);
		member.setStatus(MemberStatus.ENABLE.getValue());
		member.setPassword(CodeUtils.md5EncodeData(member.getPassword()));
		member.setCreateTime(new Date());
		member.setUpdateTime(new Date());
		memberMapper.insertSelective(member);
		customerId = member.getId();

		String roles[] = { "" + RoleType.ENTERPRISE_ADMIN.getValue() };
		memberMapper.batchAddMemberRole(customerId, roles);
		vhostMapper.batchAddMemberVhost(customerId, MemberVhostStatus.UNALLOCATED.getValue());
		String[] customerIds = new String[1];
		customerIds[0] = customerId.toString();
		memberMapper.addCustomerMember(params.getCreateUserId(), customerIds);

		return customerId;
	}

	/*
	 * 客户 删除
	 */
	@Override
	@Transactional
	public void delBean(Integer id) throws HKException {
		// TODO 删除客户
		Member member = getMember(id);
		MemberVhost memberVhost = this.getVhost(id);
		boolean isVhostAuthorizePass = false;

//		// 判断客户是否与设备组有关联
//		if (memberMapper.checkDeviceGroupRelation(id) != null)
//			throw manager.create("EC00008");

		if (memberVhost.getVhostStatus() == MemberVhostStatus.AUTHORIZE_PASS.getValue()) {
			isVhostAuthorizePass = true;

			// 判断该客户关联队列中待发送数据是否为0
			Long waitSendNums = queueService.queryQueueWatiSendNums(id);

			if (waitSendNums != null && waitSendNums > 0)
				throw manager.create("EC00010");

			String upStreamQueueName = queueService.getQueueName(id, QueueType.UPSTREAM);
			QueueCustomer queueCustomer = rabbitRestTool.getQueueMsg(memberVhost.getVhost(), upStreamQueueName);

			// 判断该客户同步线上队列服务器中是否尚有待发送数据
			if (queueCustomer.getMessages() > 0)
				throw manager.create("VHOST1012");
		}

		// 1.删除hk_device_group_member中跟member_id关联的记录 - 数据库
		deviceGroupMemberMapper.delMemberGroupByGroupId(null, null, id);

		// 2.删除客户关联的虚拟机信息 - 数据库
		vhostMapper.delete(new MemberVhost(id));

        // 3.删除客户关联的上行队列、下行队列(软删除) - 数据库
        queueMapper.delQueueByCustomerId(id);

		// 4.删除客户(软删除) - 数据库
		member.setMemberName(member.getMemberName() + "DEL@" + id);
		member.setIsDelete(MemberDeleteStatus.YES.getValue());
		member.setStatus(MemberStatus.DISABLE.getValue());
		member.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(member);

		// 5.删除sys_member_role中跟member_id关联的记录 - 数据库
		memberMapper.delMemberRoleByMemberId(id);

		// 6.删除hk_customer_member中跟customer_id关联的记录 - 数据库
		memberMapper.delCustomerMemberByCustomerId(id);

		if (isVhostAuthorizePass) {
			// 7.删除客户关联的虚拟机地址、虚拟机账号、上行队列、下行队列 - 【线上】
			rabbitVhostManager.deleteVhostObject(memberVhost.getVhost(), memberVhost.getVhostAccount());

			// TODO 删除客户关联下行队列-队列监听器(SpringBean)对象

			// 8.删除原先缓存过的虚拟机工厂对象
			rabbitVhostManager.removeVhostFactorySpringBean(memberVhost.getVhostCode());
		}
		
		dataPushReadyService.customerDel(member.getId());
	}

	/*
	 * 客户 强制删除
	 */
	@Override
	public void forceDelCustomer(Integer id) throws HKException {
		// TODO 强制删除客户
		Member member = getMember(id);
		MemberVhost memberVhost = this.getVhost(id);
		boolean isVhostAuthorizePass = false;

		if (memberVhost.getVhostStatus() == MemberVhostStatus.AUTHORIZE_PASS.getValue())
			isVhostAuthorizePass = true;

		// 1.删除hk_device_group_member中跟member_id关联的记录 - 数据库
		deviceGroupMemberMapper.delMemberGroupByGroupId(null, null, id);

		// 2.删除客户关联的虚拟机信息 - 数据库
		vhostMapper.delete(new MemberVhost(id));

        // 3.删除客户关联的上行队列、下行队列(软删除) - 数据库
        queueMapper.delQueueByCustomerId(id);

		// 4.删除客户(软删除) - 数据库
		member.setMemberName(member.getMemberName() + "DEL@" + id);
		member.setIsDelete(MemberDeleteStatus.YES.getValue());
		member.setStatus(MemberStatus.DISABLE.getValue());
		member.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(member);

		// 5.删除sys_member_role中跟member_id关联的记录 - 数据库
		memberMapper.delMemberRoleByMemberId(id);

		// 6.删除hk_customer_member中跟customer_id关联的记录 - 数据库
		memberMapper.delCustomerMemberByCustomerId(id);

		if (isVhostAuthorizePass) {
			// 7.删除客户关联的虚拟机地址、虚拟机账号、上行队列、下行队列 - 【线上】
			rabbitVhostManager.deleteVhostObject(memberVhost.getVhost(), memberVhost.getVhostAccount());

			// TODO 删除客户关联下行队列-队列监听器(SpringBean)对象

			// 8.删除原先缓存过的虚拟机工厂对象
			rabbitVhostManager.removeVhostFactorySpringBean(memberVhost.getVhostCode());
		}
		
		dataPushReadyService.customerDel(member.getId());
	}

	/*
	 * 客户 更新
	 */
	@Override
	@Transactional
	public void updateBean(MemberParams params) throws HKException {
		// 如果修改前和修改后的用户名一样,就无需判断用户名是否已存在
		if (!(getMember(params.getId()).getMemberName()).equals(params.getMemberName())) {
			// 判断用户名是否已存在
			if (memberMapper.selectOne(new Member(params.getMemberName())) != null)
				throw manager.create("EC00009");
		}

		Member result = new Member();
		params.setPassword(null);
		BeanUtils.copyProperties(params, result);
		result.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(result);
		String roles[] = { "" + RoleType.ENTERPRISE_ADMIN.getValue() };

		if (roles != null && roles.length > 0) {
			Integer memberId = params.getId();
			memberMapper.delMemberRoleByMemberId(memberId);
			memberMapper.batchAddMemberRole(memberId, roles);
		}
	}

	/*
	 * 客户 变更 状态
	 */
	@Override
	@Transactional
	public void updateCustomerStates(MemberParams params) throws HKException {
		Integer enable = MemberStatus.ENABLE.getValue();
		Integer disable = MemberStatus.DISABLE.getValue();
		Integer memberId = params.getId();
		Member member = getMemberByid(memberId);

		// 判断客户状态
		if (member.getStatus() == enable)
			member.setStatus(disable);
		else
			member.setStatus(enable);

		memberMapper.updateByPrimaryKeySelective(member);

		// 当客户状态变更为无效时，会同步暂停该客户所关联的队列
		if (member.getStatus() == disable)
			queueService.stopQueue(memberId);
		
		dataPushReadyService.deviceUserUpdate(memberId, MemberStatus.getByValue(params.getStatus()));
	}

	/*
	 * 客户 查询
	 */
	@Override
	public MemberVO getBean(Integer id) throws HKException {
		MemberVO result = new MemberVO();
		Member member = memberMapper.selectByPrimaryKey(id);

		if (member != null) {
			BeanUtils.copyProperties(member, result);
		}

		List<Integer> roles = memberMapper.getRoles(id);
		String rolesStr = "";

		if (roles != null && roles.size() > 0) {
			for (Integer r : roles) {
				rolesStr = rolesStr.concat(r.toString()).concat(",");
			}

			rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
			result.setRoles(rolesStr);
		}

		return result;
	}

	/*
	 * 客户 分页 查询
	 */
	@Override
	public PageInfo<CustomerVO> queryPage(CustomerPageParams params) throws HKException {
		PageHelper.startPage(params.getPage(), params.getRows());
		Page<Customer> pagelist = memberMapper.queryPageCustomer(params);
		List<CustomerVO> volist = converPage(pagelist, CustomerVO.class);

		for (Iterator<CustomerVO> iterator = volist.iterator(); iterator.hasNext();) {
			CustomerVO vo = iterator.next();
			Integer memberId = vo.getId();
			List<String> roles = memberMapper.getRoleNames(memberId);
			String rolesStr = "";

			if (roles != null && roles.size() > 0) {
				for (String r : roles) {
					rolesStr = rolesStr.concat(r.toString()).concat(",");
				}

				rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
				vo.setRoles(rolesStr);
			}
		}

		PageInfo<CustomerVO> info = new PageInfo<>(pagelist.getTotal(), volist);
		return info;
	}

	/*
	 * 客户 列表
	 */
	@Override
	public List<MemberVO> queryCustomerList(MemberParams params) throws HKException {
		return memberMapper.queryCustomerList(params);
	}

	/*
	 * 虚拟主机 信息 配置
	 */
	@Override
	public void configVhost(MemberVhostParams params) throws HKException {
		// 判断虚拟主机信息是否未分配
		if (getVhost(params.getMemberId()).getVhostStatus() != MemberVhostStatus.UNALLOCATED.getValue())
			throw manager.create("VHOST0004");

		String vhost = params.getVhost();

		// 判断虚拟主机地址是否已存在
		if (getVhost(vhost) != null)
			throw manager.create("VHOST0005");

		String vhostCode = UUIDUtils.get32BitUUID();
		Integer vhostStatus = MemberVhostStatus.ALLOCATED.getValue();
		params.setVhostCode(vhostCode);
		params.setVhostStatus(vhostStatus);
		vhostMapper.updateVhost(params);

		// 更新数据库->hk_device_push_message{vhost_code,vhost_status,vhost_name}
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setMemberId(params.getMemberId());
		dataPushReady.setVhostCode(vhostCode);
		dataPushReady.setVhostStatus(vhostStatus);
		dataPushReady.setVhostName(vhost);
		dataPushReadyService.vhostInfoUpdate(dataPushReady);
	}

	/*
	 * 虚拟主机 信息 变更
	 */
	@Override
	public void updateVhost(MemberVhostParams params) throws HKException {
		Integer memberId = params.getMemberId();
		Member member = this.getMember(memberId);

		// 判断客户状态是否有效, 无效不允许变更
		if (member.getStatus() == MemberStatus.DISABLE.getValue())
			throw manager.create("MEMBER10003");

		// 变更member_vhost表记录-vhost_code建议重新变更(UUID)
		String newVhostCode = UUIDUtils.get32BitUUID();
		params.setVhostCode(newVhostCode);
		Integer vhostStatus = getVhostStatus(memberId);

		// 判断虚拟机是否已授权, 无授权则直接更新数据库
		if (vhostStatus == MemberVhostStatus.AUTHORIZE_PASS.getValue())
			throw manager.create("VHOST1013");

		vhostMapper.updateVhost(params);

		// 更新数据库->hk_device_push_message{vhost_code,vhost_name}
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setMemberId(memberId);
		dataPushReady.setVhostCode(newVhostCode);
		dataPushReady.setVhostName(params.getVhost());
		dataPushReadyService.vhostInfoUpdate(dataPushReady);

		// // 判断队列关联的未发送数据为0
		// Long waitSendNums = queueService.queryQueueWatiSendNums(memberId);
		// if (waitSendNums > 0)
		// throw manager.create("VHOST1011");
		//
		// // 队列服务器中的队列中没有数据待发送 -- 先观察上行队列,下行队列下一步看业务需求待定
		// MemberVhost oldVhost = this.getVhost(memberId);
		// String oldVhostName = oldVhost.getVhost();
		// String upStreamQueueName = queueService.getQueueName(memberId,
		// QueueType.UPSTREAM);
		// QueueCustomer queueCustomer = rabbitRestTool.getQueueMessage(oldVhostName,
		// upStreamQueueName);
		// if (queueCustomer.getMessages() > 0)
		// throw manager.create("VHOST1012");
		//
		// String newVhostName = params.getVhost();
		// // 判断虚拟机信息与队列队列服务器中匹配判断
		// rabbitVhostManager.validateExists(newVhostName, params.getVhostAccount(),
		// params.getVhostPassword());
		// // 开始执行后续合法步骤
		//
		// // 删除原虚拟主机工厂对象
		// rabbitVhostManager.removeVhostFactorySpringBean(oldVhost.getVhostCode());
		// // 添加新虚拟主机工厂对象
		// rabbitVhostManager.createVhostSpringAmqpFactory(newVhostName, newVhostCode);
		//
		// // 更新关联队列名称-数据库(hk_queue)
		// String oldPrifix = oldVhostName.contains("/") ? oldVhostName.replace("/", "")
		// : oldVhostName;
		// String newPrifix = newVhostName.contains("/") ? newVhostName.replace("/", "")
		// : newVhostName;
		// String newUpStreamQueueName = upStreamQueueName.startsWith(oldPrifix) ?
		// upStreamQueueName.replace(oldPrifix, newPrifix) : newPrifix.concat("." +
		// upStreamQueueName);
		// queueService.updateQueueName(memberId, QueueType.UPSTREAM,
		// newUpStreamQueueName);
		// String downStreamQueueName = queueService.getQueueName(memberId,
		// QueueType.DOWNSTREAM);
		// String newDownSteamQueueName = downStreamQueueName.startsWith(oldPrifix) ?
		// downStreamQueueName.replace(oldPrifix, newPrifix) : newPrifix.concat("." +
		// downStreamQueueName);
		// queueService.updateQueueName(memberId, QueueType.DOWNSTREAM,
		// newDownSteamQueueName);
		// queueService.stopQueue(memberId);
		//
		// // 同步更新RabbitMQ服务器队列
		// rabbitQueueManager.createQueueBingVhost(newVhostCode, newUpStreamQueueName);
		// rabbitQueueManager.createQueueBingVhost(newVhostCode, newDownSteamQueueName);
		// // 创建下行队列监听器对象--先截留暂停
		//
		// // 更新虚拟机缓存,更新队列缓存
		// rabbitVhostManager.updateCache();
		// rabbitQueueManager.updateQueue();
		// // 更新数据库记录
		// vhostMapper.updateVhost(params);
	}

	/*
	 * 虚拟主机 授权通过
	 */
	@Override
	public void authorVhost(Integer memberId) throws HKException {
		Logger log = LogUtil.SERVICE_LOG;
		Member member = this.getMember(memberId);
		if (member.getStatus() == MemberStatus.DISABLE.getValue())
			throw manager.create("MEMBER10003");

		// 判断虚拟机信息与队列服务器中是否匹配
		MemberVhost memberVhost = vhostMapper.getVhostByMemberId(memberId);
		String vhostName = memberVhost.getVhost();
		String vhostCode = memberVhost.getVhostCode();
		rabbitVhostManager.validateExists(vhostName, memberVhost.getVhostAccount(), memberVhost.getVhostPassword());
		rabbitVhostManager.createRabbitVhostMessage(vhostName, memberVhost.getVhostAccount(), memberVhost.getVhostPassword());

		log.info("虚拟机信息匹配成功 执行后续步骤");

		// 更新数据库->hk_membr_vhost{vhost_status=3}
		MemberVhostParams params = new MemberVhostParams();
		params.setMemberId(memberId);
		params.setVhostStatus(MemberVhostStatus.AUTHORIZE_PASS.getValue());
		vhostMapper.updateVhost(params);

		// 更新数据库->hk_device_push_message{vhost_status=3}
		dataPushReadyService.vhostAuthor(vhostCode);

		// 添加虚拟主机工厂对象
		rabbitVhostManager.createVhostSpringAmqpFactory(memberVhost.getVhost(), vhostCode);

		// 动态添加上行,下行队列--->数据库,队列服务器同步
		vhostName = vhostName.contains("/") ? vhostName.replace("/", "") : vhostName;
		String upQueue = vhostName.concat(Constant.DEFAULT_UPSTREAM_PRIFIX);
		String downQueue = vhostName.concat(Constant.DEFAULT_DOWNSTREAM_PRIFIX);
		queueService.saveQueue(upQueue, QueueType.UPSTREAM, memberId);
		queueService.saveQueue(downQueue, QueueType.DOWNSTREAM, memberId);
		rabbitQueueManager.createQueueBingVhost(vhostCode, upQueue);
		rabbitQueueManager.createQueueBingVhost(vhostCode, downQueue);

		// TODO 动态添加下行队列监听器对象-->对象Bean-->name->${vhost}DownStreamQueueListenerFactory
		// 后期有需求再弄
	}

	public Member getMember(Integer memberId) throws HKException {
		Member member = null;
		if (memberId != null)
			member = memberMapper.selectOne(new Member(memberId));
		if (member == null)
			throw manager.create("EC00007");
		return member;
	}

	public MemberVhost getVhost(Integer memberId) throws HKException {
		MemberVhost vhost = null;
		if (memberId != null)
			vhost = vhostMapper.selectOne(new MemberVhost(memberId));
		if (vhost == null)
			throw manager.create("EC00007");
		return vhost;
	}

	public MemberVhost getVhost(String vhostName) throws HKException {
		MemberVhost vhost = null;
		if (vhostName != null && vhostName.trim().length() > 0)
			vhost = vhostMapper.selectOne(new MemberVhost(vhostName));
		return vhost;
	}

	public Integer getVhostStatus(Integer memberId) throws HKException {
		return getVhost(memberId).getVhostStatus();
	}

	@Override
	public Member getMemberByid(Integer memebrId) throws HKException {
		Member member = new Member();
		member.setId(memebrId);
		member.setUserType(MemberType.CUSTOMER.getValue());
		member.setIsDelete(MemberEnum.MemberDeleteStatus.NO.getValue());
		member = memberMapper.selectByPrimaryKey(member);
		if (member == null)
			throw manager.create("MEMBER10001");
		return member;
	}

	@Override
	public MemberVhost getVhostByMemberId(Integer memberId) throws HKException {
		MemberVhost vhost = new MemberVhost();
		vhost.setMemberId(memberId);
		vhost.setVhostStatus(MemberVhostStatus.AUTHORIZE_PASS.getValue());
		vhost = vhostMapper.selectOne(vhost);
		if (vhost == null)
			throw manager.create("VHOST1010");
		return vhost;
	}

}

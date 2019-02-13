package com.hot.manage.service.common.AppPush;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.manage.db.common.AppPush.AppPushMapper;
import com.hot.manage.db.common.AppPush.AppPushMsgMapper;
import com.hot.manage.entity.common.AppPush.T_hk_apppush;
import com.hot.manage.entity.common.AppPush.T_hk_apppush_msg;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.JSONUtil;
import com.hot.manage.utils.PushUtil;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class AppPushServiceImpl implements AppPushService {

	@Autowired
	private AppPushMapper apppushmapper;

	@Autowired
	private AppPushMsgMapper appPushMsgMapper;

	// 查询用户regid
	@Override
	public T_hk_apppush selectRegid(Integer userid) {
		Example example = new Example(T_hk_apppush.class);
		example.createCriteria().andEqualTo("userid", userid);
		List<T_hk_apppush> t = apppushmapper.selectByExample(example);
		if (t.size() > 0) {
			return t.get(0);
		}
		return null;
	}

	// 向所有用户推送消息
	@Override
	public Integer sendAll(String title, String message) {
		PushUtil.sendAllsetNotification(title, message, message, null, 86400);
		return null;
	}

	// 更新用户regid信息
	@Override
	public Integer setAppPush(T_hk_apppush t_hk_apppush) {
		// 查出该用户未接收到的信息
		Example e1 = new Example(T_hk_apppush_msg.class);
		e1.createCriteria().andEqualTo("userid", t_hk_apppush.getUserid());
		List<T_hk_apppush_msg> msg = appPushMsgMapper.selectByExample(e1);
		for (int i = 0; i < msg.size(); i++) {
			// 向用户推送消息
			PushUtil.sendAllsetNotification(msg.get(i).getTitle(), msg.get(i).getContent(), msg.get(i).getMessage(),
					t_hk_apppush.getRegid(), Long.valueOf(msg.get(i).getTimeToLive()));
		}
		// 删除已推送的消息
		appPushMsgMapper.deleteByExample(e1);
		// 查询用户regid
		T_hk_apppush t = selectRegid(t_hk_apppush.getUserid());
		if (t != null) {
			// 用户regid已存在
			// 判断本次登录regid是否与上次一致
			if (!t.getRegid().equals(t_hk_apppush.getRegid())) {
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				Map<String, String> map = new HashMap<String, String>();
				map.put("time", df.format(day));
				// 如果不一致则向之前设备发送一条下线消息
				PushUtil.sendAllsetNotification("下线通知", "账号已在别处登录",
						JSONUtil.toJson(ApiResult.resultInfo("1", "账号已在别处登录", map)), t.getRegid(), 864000);
				// 然后修改用户regid信息
				Example example = new Example(T_hk_apppush.class);
				example.createCriteria().andEqualTo("userid", t_hk_apppush.getUserid());
				apppushmapper.updateByExampleSelective(t_hk_apppush, example);
			}
			// 把数据库跟此regid相同的修改为默认0
			Example e = new Example(T_hk_apppush.class);
			e.createCriteria().andEqualTo("regid", t_hk_apppush.getRegid()).andNotEqualTo("userid",
					t_hk_apppush.getUserid());
			T_hk_apppush t_hk_apppush2 = new T_hk_apppush();
			t_hk_apppush2.setRegid("0");
			apppushmapper.updateByExampleSelective(t_hk_apppush2, e);
			// 一致的话直接返回不用操作
			return null;
		}
		// 把数据库跟此regid相同的修改为默认0
		Example e = new Example(T_hk_apppush.class);
		e.createCriteria().andEqualTo("regid", t_hk_apppush.getRegid()).andNotEqualTo("userid",
				t_hk_apppush.getUserid());
		T_hk_apppush t_hk_apppush2 = new T_hk_apppush();
		t_hk_apppush2.setRegid("0");
		apppushmapper.updateByExampleSelective(t_hk_apppush2, e);
		return apppushmapper.insertSelective(t_hk_apppush);
	}

}

package com.hot.parse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.service.dc.DcService;
import com.hot.parse.service.hw.HwService;
import com.hot.parse.service.jg.JgService;
import com.hot.parse.service.krq.KrqService;
import com.hot.parse.service.ljt.LjtService;
import com.hot.parse.service.mc.McService;
import com.hot.parse.service.pm.PmService;
import com.hot.parse.service.sj.SjService;
import com.hot.parse.service.sxdl.SxdlService;
import com.hot.parse.service.sxdy.SxdyService;
import com.hot.parse.service.sy.SyService;
import com.hot.parse.service.sydl.SydlService;
import com.hot.parse.service.yg.YgService;

//线程池任务类
public class MyTask implements Runnable {

	private YgService ygService;

	private DcService dcService;

	private JgService jgService;

	private LjtService ljtService;

	private PmService pmService;

	private HwService hwService;

	private SjService sjService;

	private McService mcService;

	private KrqService krqService;

	private SxdlService sxdlService;

	private SxdyService sxdyService;

	private SyService syService;

	private SydlService sydlService;

	private Logger logger;

	private int moduleid;

	private ReceiveModel model;

	public MyTask(int moduleid, ReceiveModel model) {
		this.moduleid = moduleid;
		this.model = model;
		this.ygService = new YgService();
		this.dcService = new DcService();
		this.jgService = new JgService();
		this.ljtService = new LjtService();
		this.pmService = new PmService();
		this.hwService = new HwService();
		this.sjService = new SjService();
		this.mcService = new McService();
		this.krqService = new KrqService();
		this.sxdlService = new SxdlService();
		this.sxdyService = new SxdyService();
		this.syService = new SyService();
		this.sydlService = new SydlService();
		this.logger = LoggerFactory.getLogger(MyTask.class);
	}

	@Override
	public void run() {
		try {
			switch (moduleid) {
			case 1:// 地磁
				dcService.dc_AnalysisFun(moduleid, model);
				break;
			case 3:// 井盖
				jgService.jg_AnalysisFun(moduleid, model);
				break;
			case 2:// 烟感
				ygService.yg_AnalysisFunHotcomm(moduleid, model);
				break;
			case 9:// 垃圾桶
					// ljtService.ljt_AnalysisFun(moduleid, model);
				break;
			case 4:// 暂定甲烷监测////pm2.5
				pmService.pm_AnalysisFun(moduleid, model);
				break;
			case 8:// 红外
				hwService.hw_AnalysisFun(moduleid, model);
				break;
			case 10:// 水浸
				sjService.sj_AnalysisFun(moduleid, model);
				break;
			case 11:// 门磁
				mcService.mc_AnalysisFun(moduleid, model);
				break;
			case 12:// 可燃气
				krqService.krq_AnalysisFunHotcomm(moduleid, model);
				break;
			case 14:// 三相电流
				// sxdlService.sxdl_AnalysisFun(moduleid, model);
				break;
			case 15:// 三相电压
				// sxdyService.sxdy_AnalysisFun(moduleid, model);
				break;
			case 16:// 水压
				// syService.sy_AnalysisFunXiaoan(moduleid, model);
				// syService.sy_AnalysisFunTuoPuSR(moduleid, model);
				break;
			case 17:// 剩余电流
				// sydlService.sydl_AnalysisFun(moduleid, model);
				break;
			case 99:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
			e.printStackTrace();
			return;
		}
	}
}

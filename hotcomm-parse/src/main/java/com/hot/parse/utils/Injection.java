package com.hot.parse.utils;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hot.parse.db.common.DevMapper;
import com.hot.parse.db.dc.LogDcMapper;
import com.hot.parse.db.hw.LogHwMapper;
import com.hot.parse.db.jg.LogJgMapper;
import com.hot.parse.db.krq.LogKrqMapper;
import com.hot.parse.db.ljt.LogLjtMapper;
import com.hot.parse.db.mc.LogMcMapper;
import com.hot.parse.db.pm.LogPmMapper;
import com.hot.parse.db.sj.LogSjMapper;
import com.hot.parse.db.sxdl.LogSxdlMapper;
import com.hot.parse.db.sxdy.LogSxdyMapper;
import com.hot.parse.db.sy.LogSyMapper;
import com.hot.parse.db.sydl.LogSydlMapper;
import com.hot.parse.db.yg.LogYgMapper;
import com.hot.parse.db.ywj.LogYwjMapper;

//初始化mapper
@Component
public class Injection {

	@Autowired
	public DevMapper devMapper;

	@Autowired
	public LogYgMapper ygMapper;

	@Autowired
	public LogDcMapper dcMapper;

	@Autowired
	public LogJgMapper jgMapper;

	@Autowired
	public LogLjtMapper ljtMapper;

	@Autowired
	public LogPmMapper pmMapper;

	@Autowired
	public LogHwMapper hwMapper;

	@Autowired
	public LogSjMapper sjMapper;

	@Autowired
	public LogMcMapper mcMapper;

	@Autowired
	public LogKrqMapper krqMapper;

	@Autowired
	public LogYwjMapper ywjMapper;

	@Autowired
	public LogSyMapper syMapper;

	@Autowired
	public LogSxdlMapper sxdlMapper;

	@Autowired
	public LogSxdyMapper sxdyMapper;

	@Autowired
	public LogSydlMapper sydlMapper;

	public static Injection injection;

	@PostConstruct
	public void init() {
		injection = this;
	}

}

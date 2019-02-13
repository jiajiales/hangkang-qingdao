package com.hot.analysis.service.mc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.analysis.db.mc.McMapper;

@Service
@Transactional
public class McServiceImpl implements McService {

	@Autowired
	private McMapper mcMapper;


}

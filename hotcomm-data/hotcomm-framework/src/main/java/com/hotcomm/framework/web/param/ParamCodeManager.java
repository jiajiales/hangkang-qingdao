package com.hotcomm.framework.web.param;

import org.springframework.core.env.Environment;

public class ParamCodeManager {

  Environment environment;

  public ParamCodeManager() {
  }

  public ParamCodeManager(Environment environment) {
    this.environment = environment;
  }

  public String getDesc(String code) {
    String desc = environment.getProperty(code);
    return desc == null ? "" : desc;
  }

}

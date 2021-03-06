package com.hot.manage.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ModuleAlarmCount {
	Integer id;
	String modulename;
	Integer alarmcount;//报警未处理数量
}

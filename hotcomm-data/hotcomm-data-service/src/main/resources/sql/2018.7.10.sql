DROP TABLE IF EXISTS hk_device_push_message;
CREATE TABLE `hk_device_push_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(50) DEFAULT NULL COMMENT '设备编号',
  `device_status` tinyint(2) DEFAULT NULL COMMENT '设备状态',
  `device_typeCode` varchar(30) DEFAULT NULL COMMENT '设备类型别名',
  `device_typeId` int(11) DEFAULT NULL COMMENT '设备类型编号',
  `device_groupId` int(11) DEFAULT NULL COMMENT '设备组编号',
  `member_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `member_status` int(11) DEFAULT NULL COMMENT '用户状态',
  `vhost_code` varchar(80) DEFAULT NULL COMMENT '虚拟机账号',
  `vhost_status` tinyint(2) DEFAULT NULL COMMENT '虚拟机状态',
  `vhost_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '虚拟机名称',
  `queue_name` varchar(50) DEFAULT NULL COMMENT '队列名称',
  `queue_status` int(11) DEFAULT NULL COMMENT '队列状态',
  `queue_id` int(11) DEFAULT NULL COMMENT '队列编号',
  `queue_holeTime` date DEFAULT NULL COMMENT '队列有效期',
  `queue_sendFilterNums` bigint(20) DEFAULT NULL COMMENT '队列发送拦截基数',
  PRIMARY KEY (`id`),
  KEY `devicecode` (`device_code`)
) ENGINE=MyISAM AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;



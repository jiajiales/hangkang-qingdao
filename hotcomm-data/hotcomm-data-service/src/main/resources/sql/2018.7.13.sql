ALTER TABLE hk_device_push_message ADD COLUMN device_typeCode VARCHAR(30) COMMENT '设备类型编号';
ALTER TABLE hk_device_push_message ADD COLUMN device_groupId INT(11) COMMENT '设备组编号';
ALTER TABLE `hk_device_push_message` CHANGE `queue_holdtime` `queue_holeTime` DATETIME  COMMENT '队列有效期';
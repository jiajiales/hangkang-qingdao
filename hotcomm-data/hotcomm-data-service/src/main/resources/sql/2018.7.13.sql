ALTER TABLE hk_device_push_message ADD COLUMN device_typeCode VARCHAR(30) COMMENT '�豸���ͱ��';
ALTER TABLE hk_device_push_message ADD COLUMN device_groupId INT(11) COMMENT '�豸����';
ALTER TABLE `hk_device_push_message` CHANGE `queue_holdtime` `queue_holeTime` DATETIME  COMMENT '������Ч��';
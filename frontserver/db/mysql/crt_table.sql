CREATE TABLE `file_proc_info` (
  `file_name` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `file_busi_code` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `file_proc_time` VARCHAR(14) COLLATE utf8_general_ci DEFAULT NULL,
  `file_proc_status` VARCHAR(2) COLLATE utf8_general_ci DEFAULT NULL,
  `file_type` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `file_curr_path` VARCHAR(1000) COLLATE utf8_general_ci DEFAULT NULL,
  `file_ext_rep` VARCHAR(2) COLLATE utf8_general_ci DEFAULT NULL,
  `file_ext_dest` VARCHAR(1000) COLLATE utf8_general_ci DEFAULT NULL,
  `last_time` VARCHAR(14) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`file_busi_code`, `file_name`) USING BTREE
) ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
;


CREATE TABLE `rpt_txn_sale` (
  `businessday` VARCHAR(10) COLLATE utf8_general_ci DEFAULT NULL,
  `station` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `equipment_id` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `operatorid` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `count` INTEGER(100) DEFAULT NULL,
  `txn_amount` INTEGER(100) DEFAULT NULL,
  `last_time` VARCHAR(14) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`businessday`, `station`, `equipment_id`, `operatorid`) USING BTREE
) ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
;
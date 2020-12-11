use autoium;

#
# Structure for table `project_param`
#

DROP TABLE IF EXISTS `project_param`;

CREATE TABLE `project_param` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '参数ID',
	`project_id` CHAR ( 19 ) DEFAULT '-1' COMMENT '所属项目ID',
	`name` VARCHAR ( 19 ) NOT NULL COMMENT '参数名称',
	`type` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '参数类型：0:字符串;1:数值;2:数据库;3:布尔;4:数',
	`environment` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '参数环境',
	`key` VARCHAR ( 255 ) NOT NULL COMMENT '参数键',
	`value` VARCHAR ( 255 ) NOT NULL COMMENT '参数值',
	`binding` VARCHAR ( 1900 ) DEFAULT '' COMMENT '绑定的用例',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '显示排序',
	`description` VARCHAR ( 255 ) DEFAULT '' COMMENT '参数描述',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ),
	KEY `idx_project_id` ( `project_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '参数化';
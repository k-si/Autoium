create database autoium;

#
# Structure for table `project`
#

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '项目ID',
	`name` VARCHAR ( 19 ) NOT NULL COMMENT '项目名称',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '显示排序',
	`description` text COMMENT '项目介绍',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '项目';

#
# Structure for table `project_param`
#

DROP TABLE IF EXISTS `project_param`;

CREATE TABLE `project_param` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '参数ID',
	`project_id` CHAR ( 19 ) DEFAULT '-1' COMMENT '所属项目ID',
	`name` VARCHAR ( 19 ) NOT NULL COMMENT '参数名称',
	`type` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '参数类型：0:字符串;1:数值;2:数据库;3:布尔;4:数',
	`key` text NOT NULL COMMENT '参数键',
	`value` text NOT NULL COMMENT '参数值',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '显示排序',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ),
	KEY `idx_project_id` ( `project_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '参数化';

#
# Structure for table `api_case_suite`
#

DROP TABLE IF EXISTS `api_case_suite`;

CREATE TABLE `api_case_suite` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '用例模块ID',
	`name` VARCHAR ( 19 ) NOT NULL COMMENT '用例模块名称',
	`project_id` CHAR ( 19 ) NOT NULL  COMMENT '所属项目ID',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序字段',
	`description` VARCHAR ( 255 ) COMMENT '用例模块简介',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ),
	KEY `idx_project_id` ( `project_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '用例模块';

#
# Structure for table `api_case`
#

DROP TABLE IF EXISTS `api_case`;

CREATE TABLE `api_case` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '用例ID',
	`name` VARCHAR ( 19 ) NOT NULL COMMENT '用例名称',
	`project_id` CHAR ( 19 ) NOT NULL COMMENT '所属模块ID',
	`api_case_suite_id` CHAR ( 19 ) NOT NULL COMMENT '所属模块ID',
	`host` VARCHAR ( 255 ) CHARACTER SET utf8 DEFAULT ' ' COMMENT '协议和域名',
	`path` VARCHAR ( 255 ) CHARACTER SET utf8 DEFAULT ' ' COMMENT '路径',
	`req_method` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '请求类型;0:GET;1:PUT;2:PUT;3:DELETE',
	`req_header` VARCHAR ( 255 ) DEFAULT ' ' COMMENT '用例的请求头',
	`req_params` VARCHAR ( 255 ) DEFAULT ' ' COMMENT '用例的请求参数',
	`req_body_type` INT ( 2 ) DEFAULT '0' COMMENT '请求body的类型;0:json;1:form;2:file',
	`req_body_json` VARCHAR ( 1024 ) DEFAULT ' ' COMMENT '用例的json类型请求体',
	`req_body_form` VARCHAR ( 1024 ) DEFAULT ' ' COMMENT '用例的form类型请求体',
	`req_body_file` VARCHAR ( 255 ) CHARACTER SET utf8 DEFAULT ' ' COMMENT '用例的文件类型请求体,用阿里OSS存储',
	`examine` VARCHAR ( 1024 ) DEFAULT ' ' COMMENT '请求的断言',
	`pre_action` VARCHAR ( 1024 ) DEFAULT ' ' COMMENT '前置操作例如登录,是所有接口用例中的一个',
	`description` VARCHAR ( 255 ) DEFAULT ' ' COMMENT '用例的描述',
	`status` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '用例执行状态 0未执行 1执行成功 2执行失败',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序字段',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ),
	KEY `idx_project_id` ( `project_id` ),
	KEY `idx_api_case_suite_id` ( `api_case_suite_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '接口用例';




#
# Structure for table `api_case_result`
#

DROP TABLE IF EXISTS `api_case_result`;

CREATE TABLE `api_case_result` (
	`id` CHAR ( 19 ) NOT NULL COMMENT '用例结果ID',
	`project_id` CHAR ( 19 ) NOT NULL  COMMENT '所属项目ID',
	`api_case_suite_id` CHAR ( 19 ) NOT NULL COMMENT '所属模块ID',
	`case_id` CHAR ( 19 ) NOT NULL  COMMENT '对应接口ID',

	`status` INT ( 2 ) NOT NULL DEFAULT '0' COMMENT '用例执行状态 0未执行 1执行成功 2执行失败',
	`sort` INT ( 10 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序字段',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ),
	KEY `idx_project_id` ( `project_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = COMPACT COMMENT = '用例执行结果';


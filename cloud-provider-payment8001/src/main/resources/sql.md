payment建表语句
```
CREATE TABLE `payment`(
	`id` BIGINT(20) NOT NULL auto_increment COMMENT '主键ID',
	`serial` VARCHAR(200) DEFAULT '',
	PRIMARY KEY(`id`)
)ENGINE=INNODB auto_increment=1 DEFAULT CHARSET=utf8
```
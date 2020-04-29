/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.27 : Database - appraisal
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`appraisal` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `appraisal`;

/*Table structure for table `ap_appraisal` */

DROP TABLE IF EXISTS `ap_appraisal`;

CREATE TABLE `ap_appraisal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '鉴定物品表',
  `user_id` int(11) DEFAULT NULL COMMENT '发布人id',
  `appraisal_type_id` int(11) DEFAULT NULL COMMENT '商品类型id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `describc` text COMMENT '商品描述',
  `appraisal_state` varchar(255) DEFAULT 'undetermined' COMMENT '商品状态 待鉴定undetermined 通过adopt 未通过failedpass',
  `browse` int(11) DEFAULT '0' COMMENT '浏览量',
  `ap_case` varchar(25) DEFAULT 'no' COMMENT '是否可以成为案例 yes|no',
  `status` varchar(255) DEFAULT 'yes' COMMENT '状态(yes 上架，no下架)',
  `ap_images` text COMMENT '存放图片url,',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `ap_appraisal` */

insert  into `ap_appraisal`(`id`,`user_id`,`appraisal_type_id`,`title`,`describc`,`appraisal_state`,`browse`,`ap_case`,`status`,`ap_images`,`create_time`,`update_time`) values (1,5,1,'test','test','adopt',0,'yes','yes','https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807932.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807074.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808379.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808103.jpg,','2019-09-04 11:28:13','2019-09-05 11:28:16'),(2,6,2,'拼夕夕买的劳力士','拼夕夕买的劳力士大家帮我鉴定下','undetermined',0,'no','yes','https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807932.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807074.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808379.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808103.jpg,','2019-09-03 15:56:24','2019-09-03 15:56:24'),(11,4,3,'扫把簸箕','这是描述这是描述','undetermined',0,'no','yes','https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568193598649.jpg,','2019-09-11 17:19:52','2019-09-11 17:19:52'),(12,9,2,'啊写行不行','突突突突突突','undetermined',0,'yes','yes','https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807932.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795807074.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808379.jpg,https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568795808103.jpg,','2019-09-18 16:36:46','2019-09-18 16:36:46');

/*Table structure for table `ap_appraisal_images` */

DROP TABLE IF EXISTS `ap_appraisal_images`;

CREATE TABLE `ap_appraisal_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品图片信息表',
  `appraisal_id` int(11) NOT NULL COMMENT '商品id',
  `image_url` varchar(255) NOT NULL COMMENT '图片路径',
  `sort` int(11) NOT NULL COMMENT '排序',
  `status` varchar(255) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_appraisal_images` */

/*Table structure for table `ap_appraisal_type` */

DROP TABLE IF EXISTS `ap_appraisal_type`;

CREATE TABLE `ap_appraisal_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '鉴定类型表',
  `type_name` varchar(55) NOT NULL COMMENT '类型名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `ap_show` varchar(5) NOT NULL COMMENT '状态 是否显示 yes|no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `ap_appraisal_type` */

insert  into `ap_appraisal_type`(`id`,`type_name`,`create_time`,`update_time`,`ap_show`) values (1,'鞋子','2019-08-27 16:12:26','2019-08-27 16:12:32','yes'),(2,'包包','2019-08-26 16:12:43','2019-08-28 16:12:46','yes'),(3,'电脑','2019-08-28 16:12:55','2019-08-28 16:12:59','yes'),(4,'手机','2019-09-11 17:12:53','2019-09-11 17:12:56','yes'),(5,'香水','2019-09-10 17:13:04','2019-09-10 17:13:09','yes'),(6,'手表','2019-09-11 17:13:27','2019-09-12 17:13:30','yes');

/*Table structure for table `ap_banner` */

DROP TABLE IF EXISTS `ap_banner`;

CREATE TABLE `ap_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '首页banner',
  `picture_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `skip_url` varchar(255) DEFAULT NULL COMMENT '跳转路径（拼接好的）',
  `skip_id` int(11) DEFAULT NULL COMMENT '跳转id',
  `type` varchar(255) DEFAULT NULL COMMENT '跳转类型',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `state` varchar(255) DEFAULT NULL COMMENT '状态(yes上架，no下架)',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `ap_banner` */

insert  into `ap_banner`(`id`,`picture_url`,`skip_url`,`skip_id`,`type`,`title`,`sort`,`state`,`create_user_id`,`update_user_id`,`create_time`,`update_time`) values (1,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568204218332&di=bb5050416c00f2a376c67a2ca3ded3c8&imgtype=0&src=http%3A%2F%2Fpic.globalimporter.net%2Fupload8%2F2012-10-18%2F8821991418594422.jpg','test',NULL,'test','test',1,'yes',NULL,NULL,'2019-09-10 11:33:02','2019-09-10 11:33:04'),(2,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568204145823&di=224933b1ef8c030f5d17420232feac3c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01966b568b914132f8754c803ba464.jpg%40900w_1l_2o_100sh.jpg','test',NULL,'test','test',0,'yes',NULL,NULL,'2019-09-10 11:33:04','2019-09-10 11:33:04'),(5,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568204105560&di=27dbfddab36b46c6f09cc21ff4cdb8c8&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fdac8af4f5a02a6d2e0c700306a20696f27a6633fc75f-LVcZA4_fw658','test',NULL,'test','test',0,'yes',NULL,NULL,'2019-09-11 17:30:42','2019-09-11 17:30:45');

/*Table structure for table `ap_comment` */

DROP TABLE IF EXISTS `ap_comment`;

CREATE TABLE `ap_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论表',
  `appraisal_id` int(11) NOT NULL COMMENT '鉴定id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `cn_comment` text NOT NULL COMMENT '评论内容 仅限256个字符',
  `goods_state` varchar(50) NOT NULL COMMENT '状态 是否被采纳 yes| no  默认no',
  `judge` varchar(50) NOT NULL COMMENT '判断  真genuine   假counterfeit',
  `cm_show` varchar(50) NOT NULL COMMENT '是否显示 yes|no 默认yes',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `ap_comment` */

insert  into `ap_comment`(`id`,`appraisal_id`,`user_id`,`cn_comment`,`goods_state`,`judge`,`cm_show`,`create_time`,`update_time`) values (1,1,4,'124','yes','counterfeit','yes','2019-09-11 15:17:01','2019-09-03 16:24:18'),(2,1,6,'142','no','counterfeit','yes','2019-09-03 14:40:16','2019-09-03 14:40:21'),(3,1,6,'****旗下的***的全是假货','no','counterfeit','yes','2019-09-03 15:13:46','2019-09-03 15:13:46'),(4,1,6,'31231','no','genuine','yes','2019-09-03 16:30:33','2019-09-26 16:30:38'),(5,2,4,'123213','yes','genuine','yes','2019-09-17 14:50:07','2019-09-17 14:50:08');

/*Table structure for table `ap_enshrine` */

DROP TABLE IF EXISTS `ap_enshrine`;

CREATE TABLE `ap_enshrine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏表',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `appraisal_id` int(11) NOT NULL COMMENT '鉴定id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `ap_enshrine` */

/*Table structure for table `ap_grade` */

DROP TABLE IF EXISTS `ap_grade`;

CREATE TABLE `ap_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '等级表',
  `empirical` int(11) NOT NULL COMMENT '经验值',
  `empirical_end` int(11) NOT NULL COMMENT '截止',
  `grade_name` varchar(255) NOT NULL COMMENT '名称',
  `ap_show` varchar(50) NOT NULL DEFAULT 'yes' COMMENT '是否展示 yes|no 默认yes',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `ap_grade` */

insert  into `ap_grade`(`id`,`empirical`,`empirical_end`,`grade_name`,`ap_show`,`create_time`,`update_time`) values (1,0,1,'一级','yes','2019-09-17 10:16:46','2019-09-17 10:16:48'),(2,2,4,'二级','yes','2019-09-17 10:17:13','2019-09-17 10:17:14');

/*Table structure for table `ap_key_val` */

DROP TABLE IF EXISTS `ap_key_val`;

CREATE TABLE `ap_key_val` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '键值表',
  `key` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `values` varchar(255) DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_key_val` */

/*Table structure for table `ap_order` */

DROP TABLE IF EXISTS `ap_order`;

CREATE TABLE `ap_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付信息表',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台 1-支付宝 2-微信 3-IOS内购  4-鉴定余额',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT ' 0-已取消 10-未付款 20-已付款',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_order` */

/*Table structure for table `ap_order_item` */

DROP TABLE IF EXISTS `ap_order_item`;

CREATE TABLE `ap_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单表详情表',
  `order_number` bigint(11) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际支付金额，单位元，保留两位小数',
  `state` int(10) DEFAULT NULL COMMENT '订单状态 0-已取消 10-未付款 20-已付款',
  `reward_id` int(11) DEFAULT NULL COMMENT '被打赏人id',
  `reward_amount` decimal(20,2) DEFAULT NULL COMMENT '被打赏人实际收到金额',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论id',
  `appraisal_id` int(11) DEFAULT NULL COMMENT '鉴定id',
  `pay_platform` varchar(256) DEFAULT NULL COMMENT '1-支付宝 2-微信 3-IOS内购 4-鉴定余额',
  `platform_number` varchar(256) DEFAULT NULL COMMENT '支付流水号',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_order_item` */

/*Table structure for table `ap_reised` */

DROP TABLE IF EXISTS `ap_reised`;

CREATE TABLE `ap_reised` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '纠错表',
  `appraisal_id` int(11) NOT NULL COMMENT '纠错的物品id',
  `comment_id` int(11) NOT NULL COMMENT '原被采纳的评论id',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `user_id` int(11) NOT NULL COMMENT '发起纠错请求的用户id',
  `status` varchar(10) NOT NULL COMMENT '状态 采纳accept 忽略ignore',
  `create_time` datetime NOT NULL COMMENT '发起请求的时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `ap_show` varchar(11) NOT NULL DEFAULT 'yes' COMMENT '是否显示 yes | no',
  `ap_read` int(11) NOT NULL COMMENT '已读 1  未读 0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_reised` */

/*Table structure for table `ap_statistical` */

DROP TABLE IF EXISTS `ap_statistical`;

CREATE TABLE `ap_statistical` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录表',
  `active_count` varchar(255) DEFAULT NULL COMMENT '当天活跃数',
  `registration_count` varchar(255) DEFAULT NULL COMMENT '当天注册数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ap_statistical` */

/*Table structure for table `ap_user` */

DROP TABLE IF EXISTS `ap_user`;

CREATE TABLE `ap_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用戶表',
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `integral` int(11) DEFAULT '0' COMMENT '经验值',
  `money` double(11,2) DEFAULT '0.00' COMMENT '账户金额',
  `qq_openid` varchar(255) DEFAULT NULL COMMENT 'qq登录id',
  `wx_openid` varchar(255) DEFAULT NULL COMMENT '微信登录id',
  `status` varchar(255) DEFAULT 'no' COMMENT '状态(yes为拉黑，no未拉黑)',
  `registered_channels` varchar(255) DEFAULT NULL COMMENT '注册渠道',
  `id_number` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `birthday` varchar(255) DEFAULT NULL COMMENT '生日',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `ap_user` */

insert  into `ap_user`(`id`,`name`,`phone`,`head_url`,`integral`,`money`,`qq_openid`,`wx_openid`,`status`,`registered_channels`,`id_number`,`birthday`,`email`,`create_time`,`update_time`) values (4,'王洋','15690469182','https://jianding-wy.oss-cn-beijing.aliyuncs.com/1568098164196.jpg',0,0.00,'123',NULL,'no',NULL,'131022200106291658','2019-2-2','12312312@163.com','2019-08-27 15:23:16','2019-09-10 14:49:18'),(6,'用户_17600334372','17600334372',NULL,0,0.00,NULL,NULL,'no',NULL,NULL,NULL,NULL,'2019-08-27 15:37:07','2019-09-17 17:22:36'),(7,'用户_18731612878','18731612878',NULL,0,0.00,'qq18731612878',NULL,'no',NULL,NULL,NULL,NULL,'2019-08-27 15:55:14','2019-09-04 15:37:07'),(8,'测试用户','18888888888',NULL,0,0.00,NULL,NULL,'no',NULL,'123457984561523','2019-2-2','12312312@163.com','2019-08-27 15:55:14','2019-08-27 15:55:14'),(9,'用户_15033345680','15033345680',NULL,0,0.00,NULL,NULL,'no','Android',NULL,NULL,NULL,'2019-09-17 16:50:24',NULL);

/*Table structure for table `sys_admin` */

DROP TABLE IF EXISTS `sys_admin`;

CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员表',
  `phone_number` varchar(55) NOT NULL COMMENT '手机号',
  `name` varchar(55) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别  man| woman',
  `email` varchar(125) DEFAULT NULL COMMENT '邮箱',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `status` varchar(50) NOT NULL DEFAULT 'yes' COMMENT '状态是否启用 yes|no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_admin` */

insert  into `sys_admin`(`id`,`phone_number`,`name`,`password`,`gender`,`email`,`role_id`,`status`) values (1,'123','王洋','40bd001563085fc35165329ea1ff5c5ecbdbbeef',NULL,NULL,1,'normal');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单表',
  `menu_name` varchar(256) DEFAULT NULL COMMENT '菜单名称',
  `status` varchar(256) DEFAULT NULL COMMENT '状态(菜单/按钮)',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `url` varchar(700) DEFAULT NULL COMMENT '路径',
  `index` varchar(255) DEFAULT NULL COMMENT '前端路由地址',
  `icon` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`menu_name`,`status`,`pid`,`create_time`,`update_time`,`url`,`index`,`icon`) values (1,'设置',NULL,0,NULL,NULL,'test','test',NULL),(2,'用户',NULL,0,NULL,NULL,'test2','test2',NULL),(3,'物品',NULL,0,NULL,NULL,'test3','test3',NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `role_name` varchar(256) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`create_time`,`update_time`) values (1,'管理员1','2019-09-03 13:01:46','2019-09-03 13:01:46'),(3,'123','2019-09-03 13:02:14','2019-09-03 13:25:03');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色菜单表',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`,`create_time`,`update_time`) values (1,1,1,NULL,NULL),(2,2,1,NULL,NULL),(3,2,2,NULL,NULL),(7,3,1,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

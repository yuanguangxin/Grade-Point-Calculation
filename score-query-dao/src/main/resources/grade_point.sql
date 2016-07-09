/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50549
 Source Host           : localhost
 Source Database       : grade_point

 Target Server Version : 50549
 File Encoding         : utf-8

 Date: 07/08/2016 22:24:26 PM
*/
use grade_point;
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `rank`
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `point` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

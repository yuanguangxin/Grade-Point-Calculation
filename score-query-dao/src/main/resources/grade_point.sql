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
USE grade_point;
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `rank`
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id`     INT(11)      NOT NULL AUTO_INCREMENT,
  `stu_id` VARCHAR(255) NOT NULL,
  `info`   VARCHAR(255) NOT NULL,
  `point`  VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id`       INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255)     DEFAULT NULL,
  `password` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 164
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `user_key`;
CREATE TABLE `user_key` (
  `id`           BIGINT      NOT NULL AUTO_INCREMENT,
  `school_num`   VARCHAR(45) NOT NULL UNIQUE,
  `public_key`   VARCHAR(1024)        DEFAULT NULL,
  `private_key`  VARCHAR(1024)        DEFAULT NULL,
  `login_cookie` VARCHAR(1024)        DEFAULT NULL,
  `update_date`  DATETIME,
  PRIMARY KEY (id)
);
SET FOREIGN_KEY_CHECKS = 1;

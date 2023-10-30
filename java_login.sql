/*
 Navicat Premium Data Transfer

 Source Server         : link01
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : java_login

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 29/10/2023 15:21:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_approval
-- ----------------------------
DROP TABLE IF EXISTS `tb_approval`;
CREATE TABLE `tb_approval`  (
  `suid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `courseName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `proof` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rejection_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lt` int(0) NULL DEFAULT -1,
  `st` int(0) NULL DEFAULT -1,
  `rjlt` int(0) NULL DEFAULT -1,
  `rjst` int(0) NULL DEFAULT -1,
  PRIMARY KEY (`suid`, `courseName`) USING BTREE,
  INDEX `courseName`(`courseName`) USING BTREE,
  CONSTRAINT `courseName` FOREIGN KEY (`courseName`) REFERENCES `tb_course` (`courseName`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `suid` FOREIGN KEY (`suid`) REFERENCES `tb_user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_approval
-- ----------------------------
INSERT INTO `tb_approval` VALUES ('3', 'ios开发', 'q', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F1312e63f-243c-46cc-bf7c-c7648bf89bd1%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '审批成功', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('3', 'web开发', 'q', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F1519fa62-7426-4771-8979-a17e6de0af44%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '审批成功', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('3', '数值分析', 'q', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F4a2285ed-71bb-4e49-9ec8-58aef8ddd7d1%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '审批成功', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('xy', 'ios开发', 'qqq', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F6cf1629d-9334-4b9b-b2db-d61ce37163b8%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '课程主讲教师审批中', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('xy', 'web开发', 'ee', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2Fb7e2e825-cce7-456e-a5d8-8c4bf8fe3806%2F%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE+2023-05-03+224844.png', '主讲教师 :你', '申请驳回', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('xy', '数值分析', 'rr', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2Fe94120fb-afaf-466b-81be-383dc1d1c2b0%2F%E8%AE%A1%E7%BD%91%E4%BD%9C%E4%B8%9A-20216934-%E8%BE%9B%E4%BD%B3-%E8%BD%AF%E4%BB%B62108.docx', NULL, '审批成功', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('zn', 'ios开发', 's', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2Ff8ad3362-5cad-4921-8975-ea18fb84b8b6%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '课程主讲教师审批中', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('zn', 'test', 'qqq', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F826027e3-aa25-4b22-a65b-4a931dc0f2ce%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '结束-审批成功', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('zn', 'test123', 'qqq', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2Fb3475775-48b7-4ea3-b61d-27b1c1b60d72%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '课程主讲教师审批中', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('zn', 'web开发', 's', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2F6a3ef743-345c-4d2a-abcf-4bab76b1c999%2F20216934%E8%BE%9B%E4%BD%B3.docx', NULL, '课程主讲教师审批中', -1, -1, -1, -1);
INSERT INTO `tb_approval` VALUES ('zn', '数值分析', 's', 'D%3A%5Cjavaexercises%5Cideaprojects%5CMaven_javaweb%5Ctarget%5CMaven_javaweb%5Cdownload%2Fb370fe16-90af-4e69-b44e-484ecee1f561%2FScreenshot_2023-05-10-15-58-52-778_com.tencent.mo.jpg', NULL, '课程主讲教师审批中', -1, -1, -1, -1);

-- ----------------------------
-- Table structure for tb_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course`  (
  `courseName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `begTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上课时间',
  `ltuid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stuid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`courseName`) USING BTREE,
  INDEX `ltuid`(`ltuid`) USING BTREE,
  INDEX `stuid`(`stuid`) USING BTREE,
  CONSTRAINT `ltuid` FOREIGN KEY (`ltuid`) REFERENCES `tb_user` (`userId`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `stuid` FOREIGN KEY (`stuid`) REFERENCES `tb_user` (`userId`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('ios开发', '每周三下午15：00', 'zz', 'nn');
INSERT INTO `tb_course` VALUES ('test', '每周三下午15：00', 'DynamicApproval', 'DynamicApproval');
INSERT INTO `tb_course` VALUES ('test123', '每周三下午15：00', '66', '19');
INSERT INTO `tb_course` VALUES ('web开发', '每周六14：00', 'zz', 'nn');
INSERT INTO `tb_course` VALUES ('数值分析', '每周二9：00', 'zz', 'nn');
INSERT INTO `tb_course` VALUES ('软件创新', '每周三下午15：00', '18', '19');
INSERT INTO `tb_course` VALUES ('软件测试', '每周三下午15：00', 'zz', 'nn');

-- ----------------------------
-- Table structure for tb_da_lt
-- ----------------------------
DROP TABLE IF EXISTS `tb_da_lt`;
CREATE TABLE `tb_da_lt`  (
  `courseName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tuid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order` int(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order`, `courseName`, `tuid`) USING BTREE,
  INDEX `ltuid1`(`tuid`) USING BTREE,
  INDEX `courseName1`(`courseName`) USING BTREE,
  CONSTRAINT `courseName1` FOREIGN KEY (`courseName`) REFERENCES `tb_course` (`courseName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ltuid1` FOREIGN KEY (`tuid`) REFERENCES `tb_user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_da_st
-- ----------------------------
DROP TABLE IF EXISTS `tb_da_st`;
CREATE TABLE `tb_da_st`  (
  `courseName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tuid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order` int(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order`, `courseName`, `tuid`) USING BTREE,
  INDEX `stuid2`(`tuid`) USING BTREE,
  INDEX `courseName2`(`courseName`) USING BTREE,
  CONSTRAINT `courseName2` FOREIGN KEY (`courseName`) REFERENCES `tb_course` (`courseName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stuid2` FOREIGN KEY (`tuid`) REFERENCES `tb_user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `userId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `userPwd` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `userMail` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户邮箱',
  `userJob` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('15', '555', '1185911254@qq.com', '主讲教师');
INSERT INTO `tb_user` VALUES ('18', '888', '888@163.com', '主讲教师');
INSERT INTO `tb_user` VALUES ('19', '999', '999', '主管教师');
INSERT INTO `tb_user` VALUES ('3', '123', '456@qq.com', '学生');
INSERT INTO `tb_user` VALUES ('66', '666', '888@gmail.com', '主讲教师');
INSERT INTO `tb_user` VALUES ('admin', 'admin', '123@qq.com', '管理员');
INSERT INTO `tb_user` VALUES ('DynamicApproval', 'DynamicApproval', 'DynamicApproval', 'DynamicApproval');
INSERT INTO `tb_user` VALUES ('gg', '123', '1185911254@qq.com', '主管教师');
INSERT INTO `tb_user` VALUES ('nn', '123', '123', '主管教师');
INSERT INTO `tb_user` VALUES ('xy', '123', '456@qq.com', '学生');
INSERT INTO `tb_user` VALUES ('zn', '123', '123', '学生');
INSERT INTO `tb_user` VALUES ('zz', '123', '123', '主讲教师');

SET FOREIGN_KEY_CHECKS = 1;

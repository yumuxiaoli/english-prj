/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : english

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 07/08/2023 15:10:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assort
-- ----------------------------
DROP TABLE IF EXISTS `assort`;
CREATE TABLE `assort`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位名称|唯一|列表字段|查询字段|input',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'V' COMMENT '状态|列表字段|select[enums:{key: \'V\',value: \'有效\'},{key: \'I\',value: \'无效\'}]',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of assort
-- ----------------------------
INSERT INTO `assort` VALUES (1, '阅读', 'V', '2023-07-27 19:50:49', '2023-07-27 19:50:49');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名|唯一|列表字段|查询字段|input|排序',
  `introduce` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介|rich',
  `details` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详情|rich',
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片|upload',
  `image_size` int NULL DEFAULT NULL COMMENT '大小|列表字段|input',
  `assort_id` int NULL DEFAULT NULL COMMENT '分类|列表字段|select[db:assort]',
  `teacher_id` int NULL DEFAULT NULL COMMENT '老师|列表字段|select[db:teacher]',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_nickname`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '阅读1', '<p>无</p>', '<p>无</p>', 'http://ryalk03vf.hn-bkt.clouddn.com/1.png', 69636, 1, 1, '2023-07-31 08:56:20', '2023-07-31 08:56:20');
INSERT INTO `course` VALUES (2, 'coure', '<p>无</p>', '<p>无</p>', 'http://ryalk03vf.hn-bkt.clouddn.com/1.png', 69636, 1, 1, '2023-08-01 14:24:19', '2023-08-01 14:24:19');

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES (1, 'yumu', '5243bd5c57b79e2b3d38511f3b27c230', '86419751da454d5db5daf18023c3b215', '2023-07-26 14:20:30', NULL);

-- ----------------------------
-- Table structure for span
-- ----------------------------
DROP TABLE IF EXISTS `span`;
CREATE TABLE `span`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目|唯一|列表字段|查询字段|input|排序',
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容|唯一|列表字段|查询字段|rich',
  `course_id` int NULL DEFAULT NULL COMMENT '分类|列表字段|select[db:course]',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_name`(`title` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of span
-- ----------------------------
INSERT INTO `span` VALUES (1, '阅读1', '<p>无</p>', 1, '2023-07-31 08:53:52', '2023-07-31 08:53:52');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号|唯一|列表字段|查询字段|input|排序',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名|列表字段|查询字段|input|排序',
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像|upload',
  `image_size` int NULL DEFAULT NULL COMMENT '大小|列表字段|input',
  `introduce` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介|列表字段|textarea',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_number`(`number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '讲师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '213123', '余木', 'http://ryalk03vf.hn-bkt.clouddn.com/1.png', 69636, '余木', '2023-07-30 23:44:58', '2023-07-30 23:44:58');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名|列表字段|查询字段|input',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称|唯一|列表字段|input|排序',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值',
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像|upload',
  `image_size` int NULL DEFAULT NULL COMMENT '大小|列表字段|input',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱|列表字段|input',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话|列表字段|input',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_nickname`(`nickname` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'xiaoyu', 'yumu', '', '', NULL, NULL, '2816083598@qq.com', '1312312', '2023-07-27 11:13:19', NULL);
INSERT INTO `user` VALUES (2, 'yumu', 'xiaoyu', '17f2d694f528d57a81e14133f2b339b2', '5039fcdd7208499a9ecdfb3af4603246', 'http://ryalk03vf.hn-bkt.clouddn.com/1.png', 69636, '12312@we.com', '17667336538', '2023-07-31 15:13:40', '2023-07-31 15:13:40');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目|唯一|列表字段|查询字段|input|排序',
  `video` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频|upload',
  `video_size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大小|列表字段|查询字段|排序',
  `course_id` int NULL DEFAULT NULL COMMENT '课程|列表字段|排序|select[db:course]',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_name`(`title` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES (1, '视频1', 'http://ryalk03vf.hn-bkt.clouddn.com/QQ录屏20230724142807.mp4', '637116', 1, '2023-07-31 09:00:19', '2023-07-31 09:00:19');

-- ----------------------------
-- Table structure for words
-- ----------------------------
DROP TABLE IF EXISTS `words`;
CREATE TABLE `words`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键|列表字段|input',
  `words` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单词|唯一|列表字段|查询字段|input|排序',
  `pron` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读音|列表字段|input',
  `explains` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '解释|列表字段|input',
  `grades` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '等级|列表字段、input',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '修改时间|列表字段|date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_words`(`words` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '单词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of words
-- ----------------------------
INSERT INTO `words` VALUES (1, 'a', '/ə/\n ', 'art.\n一;任何一;每一', '四级', '2023-07-31 17:10:30', NULL);
INSERT INTO `words` VALUES (2, 'words', '[wc:d]', '单词', '四级', '2023-07-31 21:46:41', '2023-07-31 21:46:41');
INSERT INTO `words` VALUES (3, 'primary', 'primary', '主要的', '四级', NULL, NULL);
INSERT INTO `words` VALUES (4, 'modify', 'modify', '修改', '四级', NULL, NULL);
INSERT INTO `words` VALUES (5, 'alter', 'alter', '改变', '四级', NULL, NULL);
INSERT INTO `words` VALUES (6, 'change', 'change', '改变', '四级', NULL, NULL);
INSERT INTO `words` VALUES (7, 'column:', 'column:', '纵队，列', '四级', NULL, NULL);
INSERT INTO `words` VALUES (8, 'between', 'between', '在......之间', '四级', NULL, NULL);
INSERT INTO `words` VALUES (9, 'like', 'like', '喜欢，像', '四级', NULL, NULL);
INSERT INTO `words` VALUES (10, 'database', 'database', '数据库', '四级', NULL, NULL);
INSERT INTO `words` VALUES (11, 'table', 'table', '桌子，制表', '四级', NULL, NULL);
INSERT INTO `words` VALUES (12, 'update', 'update', '更新，校正', '四级', NULL, NULL);
INSERT INTO `words` VALUES (13, 'select', 'select', '挑选', '四级', NULL, NULL);
INSERT INTO `words` VALUES (14, 'drop', 'drop', '落下', '四级', NULL, NULL);
INSERT INTO `words` VALUES (15, 'delete', 'delete', '删除', '四级', NULL, NULL);
INSERT INTO `words` VALUES (16, 'check', 'check', '检查，核实', '四级', NULL, NULL);
INSERT INTO `words` VALUES (17, 'default', 'default', '违约，缺席；系统默认值', '四级', NULL, NULL);
INSERT INTO `words` VALUES (18, 'system', 'system', '系统', '四级', NULL, NULL);
INSERT INTO `words` VALUES (19, 'row', 'row', '行', '四级', NULL, NULL);
INSERT INTO `words` VALUES (20, 'Insert into', 'Insert into', '插入数据', '四级', NULL, NULL);
INSERT INTO `words` VALUES (21, 'create', 'create', '创建', '四级', NULL, NULL);
INSERT INTO `words` VALUES (22, 'groupby', 'groupby', '分组', '四级', NULL, NULL);
INSERT INTO `words` VALUES (23, 'orderby', 'orderby', '排序', '四级', NULL, NULL);
INSERT INTO `words` VALUES (24, 'desc', 'desc', '降序排列', '四级', NULL, NULL);
INSERT INTO `words` VALUES (25, 'asc', 'asc', '升序', '四级', NULL, NULL);
INSERT INTO `words` VALUES (26, 'varchar', 'varchar', '可变长字符串', '四级', NULL, NULL);
INSERT INTO `words` VALUES (27, 'date', 'date', '日期', '四级', NULL, NULL);
INSERT INTO `words` VALUES (28, 'data', 'data', '数据', '四级', NULL, NULL);
INSERT INTO `words` VALUES (29, 'unique', 'unique', '独特的，唯一的', '四级', NULL, NULL);
INSERT INTO `words` VALUES (30, 'values', 'values', '价值观', '四级', NULL, NULL);
INSERT INTO `words` VALUES (31, 'from', 'from', '从', '四级', NULL, NULL);
INSERT INTO `words` VALUES (32, 'where', 'where', '哪里，在哪', '四级', NULL, NULL);
INSERT INTO `words` VALUES (33, 'as', 'as', '当.....时；因为', '四级', NULL, NULL);
INSERT INTO `words` VALUES (34, 'distinct', 'distinct', '明显的，独特的', '四级', NULL, NULL);
INSERT INTO `words` VALUES (35, 'top', 'top', '顶部，上部', '四级', NULL, NULL);
INSERT INTO `words` VALUES (36, 'bottom', 'bottom', '底部', '四级', NULL, NULL);
INSERT INTO `words` VALUES (37, 'button', 'button', '按钮', '四级', NULL, NULL);
INSERT INTO `words` VALUES (38, 'percent', 'percent', '百分比', '四级', NULL, NULL);
INSERT INTO `words` VALUES (39, 'identity', 'identity', '一致的；恒等的', '四级', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

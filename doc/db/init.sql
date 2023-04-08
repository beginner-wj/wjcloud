SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE "category" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "parent" varchar(40) NOT NULL DEFAULT '' COMMENT '父ID',
  "name" varchar(50) NOT NULL COMMENT '名称',
  "sort" int DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000100', '00000000', '前端技术', 100);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000101', '00000100', 'htm/css', 101);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000102', '00000100', 'js', 102);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000103', '00000100', 'vue.js', 103);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000200', '00000000', '后端技术', 2002);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000201', '00000200', 'java', 201);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000202', '00000200', 'springboot', 202);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('00000203', '00000200', 'python', 203);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('bc8df469-1ab7-4470-a28e-1cf22a830696', '00000000', 'nginx', 1);
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES ('c9a3e6dd-e154-4af8-803b-8c3de4b019ce', 'bc8df469-1ab7-4470-a28e-1cf22a830696', 'nginx配置文件', 1);
COMMIT;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE "chapter" (
  "id" varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ID',
  "course_id" varchar(36) DEFAULT NULL COMMENT '课程ID',
  "name" varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='大章';

-- ----------------------------
-- Records of chapter
-- ----------------------------
BEGIN;
INSERT INTO `chapter` (`id`, `course_id`, `name`) VALUES ('76e33832-10f0-41fe-8735-f57814159c4c', '0d7ea6d1-c607-489d-9d7b-b1516826e060', 'vue第一章');
INSERT INTO `chapter` (`id`, `course_id`, `name`) VALUES ('9a7bd979-1cf3-4f91-9c0c-d4f5b74a31db', '0d7ea6d1-c607-489d-9d7b-b1516826e060', 'vue第二章');
COMMIT;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE "course" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "name" varchar(50) NOT NULL COMMENT '名称',
  "summary" varchar(2000) DEFAULT NULL COMMENT '概述',
  "time" int DEFAULT '0' COMMENT '时长|单位秒',
  "price" decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '价格(元)',
  "image" varchar(255) DEFAULT NULL COMMENT '封面',
  "level" char(1) NOT NULL COMMENT '级别|ONE("1","初级"),TWO("2","中级"),THREE("3","高级")',
  "charge" char(1) DEFAULT NULL COMMENT '收费|CHARGE("C","收费"),FREE("F","免费")',
  "status" char(1) DEFAULT NULL COMMENT '状态|PUBLISH("P","发布"),DRAFT("D","草稿")',
  "enroll" int DEFAULT '0' COMMENT '报名数',
  "sort" int DEFAULT NULL COMMENT '顺序',
  "create_at" datetime(3) DEFAULT NULL COMMENT '创建时间',
  "update_at" datetime(3) DEFAULT NULL COMMENT '修改时间',
  "teacher_id" varchar(40) DEFAULT NULL COMMENT '讲师|teacher.id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程表';

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
INSERT INTO `course` (`id`, `name`, `summary`, `time`, `price`, `image`, `level`, `charge`, `status`, `enroll`, `sort`, `create_at`, `update_at`, `teacher_id`) VALUES ('0d7ea6d1-c607-489d-9d7b-b1516826e060', 'vue课程', '学习vue技术', 1, 2.00, '', '2', 'F', 'P', NULL, 1, '2023-04-02 23:13:12.000', '2023-04-03 23:24:51.052', '5c316ddc-0147-4c90-ad85-711e5bfa0d8c');
INSERT INTO `course` (`id`, `name`, `summary`, `time`, `price`, `image`, `level`, `charge`, `status`, `enroll`, `sort`, `create_at`, `update_at`, `teacher_id`) VALUES ('2bf030ee-94e0-4fa7-80d7-ff5157bc794f', '前端技术课程(张三丰版本)', '概述', 1, 980.00, NULL, '2', 'F', 'P', NULL, 4, '2023-04-03 23:17:22.000', '2023-04-03 23:17:40.912', 'a8d5bbd4-16ee-4c1f-8435-cedef8555d51');
INSERT INTO `course` (`id`, `name`, `summary`, `time`, `price`, `image`, `level`, `charge`, `status`, `enroll`, `sort`, `create_at`, `update_at`, `teacher_id`) VALUES ('a68c4767-1a0b-40b5-b303-a10a6266acde', 'nginx课程', '学习nginx的具体部署', 1, 230.00, NULL, '2', 'C', 'P', NULL, 2, '2023-04-03 21:00:09.000', '2023-04-03 23:18:31.537', 'ad770064-e7f4-42bd-9a5b-8ba8998e46ba');
INSERT INTO `course` (`id`, `name`, `summary`, `time`, `price`, `image`, `level`, `charge`, `status`, `enroll`, `sort`, `create_at`, `update_at`, `teacher_id`) VALUES ('d2818706-7227-4fec-a219-90cea2718972', 'java课程', 'java学习', 20, 560.00, NULL, '2', 'F', 'P', NULL, 3, '2023-04-03 21:10:06.000', '2023-04-03 23:25:00.718', 'a8d5bbd4-16ee-4c1f-8435-cedef8555d51');
COMMIT;

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE "course_category" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "course_id" varchar(40) NOT NULL DEFAULT '' COMMENT '课程|course.id',
  "category_id" varchar(50) NOT NULL COMMENT '分类|category.id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

-- ----------------------------
-- Records of course_category
-- ----------------------------
BEGIN;
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('2d8c874f-d608-48f7-9931-830a8a53c327', 'd2818706-7227-4fec-a219-90cea2718972', '00000200');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('4eea38bb-d7c9-4211-88b0-1591ee9a01fe', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '00000103');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('4fffbcfe-0331-47aa-bb1c-eed5c4c20f39', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '00000102');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('5116adc2-8b47-4a6d-aff5-8a47774b79ad', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '00000100');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('6679542e-17cd-4479-a4a8-7e1f19893d6c', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '00000101');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('72cc2ab0-126f-40ab-94a6-a23475215389', '2bf030ee-94e0-4fa7-80d7-ff5157bc794f', '00000100');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('7943d25d-4044-4b92-a53e-b3dad06ee554', '2bf030ee-94e0-4fa7-80d7-ff5157bc794f', '00000103');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('ac86ec40-6ce8-4cf6-9fc3-d80518747fb4', 'a68c4767-1a0b-40b5-b303-a10a6266acde', 'bc8df469-1ab7-4470-a28e-1cf22a830696');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('ad731b95-182a-4eb9-aa31-063bccad5cb7', 'd2818706-7227-4fec-a219-90cea2718972', '00000201');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('c2e00c4d-80e3-4e71-ba12-a23462a97cdb', '2bf030ee-94e0-4fa7-80d7-ff5157bc794f', '00000101');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('d4480a21-8bb6-4d07-bca0-ae1cc6f946f1', 'a68c4767-1a0b-40b5-b303-a10a6266acde', 'c9a3e6dd-e154-4af8-803b-8c3de4b019ce');
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES ('e57a42de-2617-495c-a550-1cb3f010df36', '2bf030ee-94e0-4fa7-80d7-ff5157bc794f', '00000102');
COMMIT;

-- ----------------------------
-- Table structure for course_content
-- ----------------------------
DROP TABLE IF EXISTS `course_content`;
CREATE TABLE "course_content" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "content" mediumtext NOT NULL COMMENT '课程内容',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程内容';

-- ----------------------------
-- Records of course_content
-- ----------------------------
BEGIN;
INSERT INTO `course_content` (`id`, `content`) VALUES ('0d7ea6d1-c607-489d-9d7b-b1516826e060', '<p><br></p>');
COMMIT;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE "resource" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "name" varchar(100) NOT NULL COMMENT '名称|菜单或按钮',
  "page" varchar(50) DEFAULT NULL COMMENT '页面|路由',
  "request" varchar(200) DEFAULT NULL COMMENT '请求|接口',
  "parent" varchar(40) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------
BEGIN;
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('00', '欢迎', 'welcome', NULL, '');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('01', '系统管理', NULL, NULL, '');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('0101', '用户管理', 'system/user', NULL, '01');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('010101', '保存', NULL, '[\"/system/admin/user/list\",\"/system/admin/user/save\"]', '0101');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('010102', '删除', NULL, '[\"/system/admin/user/del\"]', '0101');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('010103', '重置密码', NULL, '[\"/system/admin/user/save-password\"]', '0101');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('0102', '资源管理', 'system/resource', NULL, '01');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('010201', '保存/显示', NULL, '[\"/system/admin/resource\"]', '0102');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('0103', '角色管理', 'system/role', NULL, '01');
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES ('010301', '角色/权限管理', NULL, '[\"/system/admin/role\"]', '0103');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE "role" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "name" varchar(100) NOT NULL COMMENT '角色',
  "roledesc" varchar(100) NOT NULL COMMENT '描述',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `name`, `roledesc`) VALUES ('00000000', '系统管理员', '管理用户、角色权限');
INSERT INTO `role` (`id`, `name`, `roledesc`) VALUES ('00000001', '开发管理员', '维护资源');
INSERT INTO `role` (`id`, `name`, `roledesc`) VALUES ('00000002', '业务管理员', '负责业务管理');
COMMIT;

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE "role_resource" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "role_id" varchar(40) NOT NULL COMMENT '角色|id',
  "resource_id" varchar(40) NOT NULL COMMENT '资源|id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色资源关联表';

-- ----------------------------
-- Records of role_resource
-- ----------------------------
BEGIN;
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('146343b7-ae0c-4ff6-8dbc-657fb5bc80b4', '00000001', '01');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('335c75b6-e929-406d-980e-112fe4c03c06', '00000000', '00');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('52b8f615-6eea-45f6-96d9-619ab2a56308', '00000000', '0103');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('5806eb56-7753-4029-b0c1-669fb157cb4b', '00000001', '0102');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('73f4d420-85e0-4ab1-a2c9-e770423a65b1', '00000000', '010101');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('8c049ca1-cea0-468b-9e54-1e42d89adafd', '00000000', '010103');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('8f3555c8-13a9-4421-86c7-73faaded67c8', '00000002', '00');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('9b525f14-38de-4e96-b1b2-f5710819e978', '00000000', '010201');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('afcfd63e-d5e1-4384-92a5-7c1e349e6a33', '00000000', '010301');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('be80d702-08b5-4064-b86b-84762e2b5131', '00000000', '0102');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('c852fc61-b1ad-4dd3-99bb-75c6b9c546b8', '00000001', '010201');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('d19940f2-e9ff-406b-ad21-d66f1e9939e4', '00000001', '00');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('e16eda3b-3098-44e7-b0ae-098b2c87c1b0', '00000000', '01');
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES ('e19d7b21-40f7-4f6b-b4c1-b371cd17712d', '00000000', '0101');
COMMIT;

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE "role_user" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "role_id" varchar(40) NOT NULL COMMENT '角色|id',
  "user_id" varchar(40) NOT NULL COMMENT '用户|id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色用户关联表';

-- ----------------------------
-- Records of role_user
-- ----------------------------
BEGIN;
INSERT INTO `role_user` (`id`, `role_id`, `user_id`) VALUES ('0693879c-a9b7-454e-aef6-55584ad56502', '00000001', 'd821be3f-f179-48c7-a305-9c38cd50120d');
INSERT INTO `role_user` (`id`, `role_id`, `user_id`) VALUES ('18f92b1c-9b94-469e-b9dd-0b493697e3de', '00000000', '10000000');
COMMIT;

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE "section" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "title" varchar(50) NOT NULL COMMENT '标题',
  "course_id" varchar(40) DEFAULT NULL COMMENT '课程|course.id',
  "chapter_id" varchar(40) DEFAULT NULL COMMENT '大章|chapter.id',
  "video" varchar(200) DEFAULT NULL COMMENT '视频',
  "time" int DEFAULT NULL COMMENT '时间',
  "charge" char(1) DEFAULT NULL COMMENT '时长|单位秒',
  "sort" int DEFAULT NULL COMMENT '顺序',
  "create_at" datetime(3) DEFAULT NULL COMMENT '创建时间',
  "update_at" datetime(3) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小节';

-- ----------------------------
-- Records of section
-- ----------------------------
BEGIN;
INSERT INTO `section` (`id`, `title`, `course_id`, `chapter_id`, `video`, `time`, `charge`, `sort`, `create_at`, `update_at`) VALUES ('447d1e49-25f3-4bfe-bea6-5b7dbfb8078f', 'vue第一章第二节', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '76e33832-10f0-41fe-8735-f57814159c4c', NULL, NULL, NULL, NULL, '2023-04-03 21:25:28.810', NULL);
INSERT INTO `section` (`id`, `title`, `course_id`, `chapter_id`, `video`, `time`, `charge`, `sort`, `create_at`, `update_at`) VALUES ('74a9b093-a3b4-4ee7-9b32-0e1d046f2293', 'vue第一章第一节', '0d7ea6d1-c607-489d-9d7b-b1516826e060', '76e33832-10f0-41fe-8735-f57814159c4c', NULL, 23, 'F', 1, '2023-04-03 21:19:24.766', NULL);
COMMIT;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE "teacher" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "name" varchar(50) NOT NULL COMMENT '姓名',
  "nickname" varchar(50) DEFAULT NULL COMMENT '昵称',
  "image" varchar(100) DEFAULT NULL COMMENT '头像',
  "position" varchar(50) DEFAULT NULL COMMENT '职位',
  "motto" varchar(50) DEFAULT NULL COMMENT '座右铭',
  "intro" varchar(500) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲师';

-- ----------------------------
-- Records of teacher
-- ----------------------------
BEGIN;
INSERT INTO `teacher` (`id`, `name`, `nickname`, `image`, `position`, `motto`, `intro`) VALUES ('5c316ddc-0147-4c90-ad85-711e5bfa0d8c', '李', '3', '', '后台开发', NULL, NULL);
INSERT INTO `teacher` (`id`, `name`, `nickname`, `image`, `position`, `motto`, `intro`) VALUES ('a8d5bbd4-16ee-4c1f-8435-cedef8555d51', '谢霆锋', 'xietingfeng', NULL, '高级开发', '座右铭1', '简介22');
INSERT INTO `teacher` (`id`, `name`, `nickname`, `image`, `position`, `motto`, `intro`) VALUES ('ad770064-e7f4-42bd-9a5b-8ba8998e46ba', '张三丰', 'zhangsanfeng', NULL, '高级项目经理', '做有用的人', '简介');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE "user" (
  "id" varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  "login_name" varchar(50) NOT NULL COMMENT '登录名',
  "name" varchar(50) DEFAULT NULL COMMENT '昵称',
  "password" varchar(32) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY ("id"),
  UNIQUE KEY "login_name_unique" ("login_name")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `login_name`, `name`, `password`) VALUES ('10000000', 'admin', '系统管理员', '343863e3cffbe2ee6c11913f7694788b');
INSERT INTO `user` (`id`, `login_name`, `name`, `password`) VALUES ('c39e9ac6-a07f-4ff7-afb8-dfdca2ae647f', 'lisi', '李四', '343863e3cffbe2ee6c11913f7694788b');
INSERT INTO `user` (`id`, `login_name`, `name`, `password`) VALUES ('d821be3f-f179-48c7-a305-9c38cd50120d', 'beginner', 'beginner', '343863e3cffbe2ee6c11913f7694788b');
INSERT INTO `user` (`id`, `login_name`, `name`, `password`) VALUES ('e356178b-1416-4b9a-82d4-9d671e679f88', 'zhangsan', 'zhangsan', '343863e3cffbe2ee6c11913f7694788b');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

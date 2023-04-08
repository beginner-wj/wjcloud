drop table if exists  `user`;
create table `user` (
      `id` varchar(40) not null default '' comment 'ID',
      `login_name` varchar(50) not null   comment '登录名',
       `name` varchar(50)   comment '昵称',
       `password` varchar(32)     comment '密码',
        primary key (`id`),
        unique key `login_name_unique` (`login_name`)
) engine=innodb default charset=utf8mb4 comment='用户';

insert into `user` (id,login_name,name,password) values
('10000000','test','测试','202cb962ac59075b964b07152d234b70');

drop table if exists  `resource`;
create table `resource` (
                        `id` varchar(40) not null default '' comment 'ID',
                        `name` varchar(100) not null   comment '名称|菜单或按钮',
                        `page` varchar(50)  null   comment '页面|路由',
                        `request` varchar(200)  null   comment '请求|接口',
                        `parent` varchar(40)  null   comment '父id',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='资源表';

insert into `resource` values ('01','系统管理',null,null,null);
insert into `resource` values ('0101','用户管理','/system/user',null,'01');
insert into `resource` values ('010101', '保存', null , '["/system/admin/user/list","/system/admin/user/save"]','0101');
insert into `resource` values ('010102', '删除', null , '["/system/admin/user/del"]','0101');
insert into `resource` values ('010103', '重置密码', null , '["/system/admin/user/save-password"]','0101');
insert into `resource` values ('0102', '资源管理', '/system/resource' , null, '01');
insert into `resource` values ('010201', '保存/显示', null ,  '["/system/admin/resource"]', '0102');
insert into `resource` values ('0103', '角色管理', '/system/role'  ,  null, '01');
insert into `resource` values ('010301', '角色/权限管理', null ,  '["/system/admin/role"]', '0103');

drop table if exists  `role`;
create table `role` (
                            `id` varchar(40) not null default '' comment 'ID',
                            `name` varchar(100) not null   comment '角色',
                            `roledesc` varchar(100) not null   comment '描述',
                            primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='角色表';
 insert into `role` values ('00000000','系统管理员','管理用户、角色权限');
insert into `role` values ('00000001','开发管理员','维护资源');
insert into `role` values ('00000002','业务管理员','负责业务管理');

drop table if exists  `role_resource`;
create table `role_resource` (
                        `id` varchar(40) not null default '' comment 'ID',
                        `role_id` varchar(40) not null   comment '角色|id',
                        `resource_id` varchar(40) not null   comment '资源|id',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='角色资源关联表';
insert into `role_resource` values ('00000000','00000000','01');
insert into `role_resource` values ('00000001','00000000','0101');
insert into `role_resource` values ('00000002','00000000','010101');
insert into `role_resource` values ('00000003','00000000','010102');
insert into `role_resource` values ('00000004','00000000','010103');
insert into `role_resource` values ('00000005','00000000','0102');
insert into `role_resource` values ('00000006','00000000','010201');
insert into `role_resource` values ('00000007','00000000','0103');
insert into `role_resource` values ('00000008','00000000','010301');


drop table if exists  `role_user`;
create table `role_user` (
                                 `id` varchar(40) not null default '' comment 'ID',
                                 `role_id` varchar(40) not null   comment '角色|id',
                                 `user_id` varchar(40) not null   comment '用户|id',
                                 primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='角色用户关联表';

insert into `role_user` values('00000000','00000000','10000000');
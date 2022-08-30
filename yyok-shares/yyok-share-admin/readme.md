alter table sys_role add  `pcode` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父编码';

alter table sys_role add  `status` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态';
alter table sys_role add  `coder` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码';
alter table sys_role add  `sorter` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序';
alter table sys_role add  `typer` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型';
alter table sys_role add  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述';
alter table sys_role add  `level` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '级别';

alter table sys_role add  `is_del` varchar(255) COLLATE utf8mb4_bin DEFAULT '2' COMMENT '是否删除：1删除、2不删除';
alter table sys_role add  `enabled` varchar(255) COLLATE utf8mb4_bin DEFAULT '1' COMMENT '状态：1启用、2禁用';
alter table sys_role add  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者';
alter table sys_role add  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者';
alter table sys_role add  `tenant_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
alter table sys_role add  `group_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属组',
alter table sys_role add  `system_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属系统',
alter table sys_role add  `instance_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属实例',
alter table sys_role add  `version_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '版本',
alter table sys_role add  `create_time` datetime COLLATE utf8mb4_bin NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期';
alter table sys_role add  `update_time` datetime COLLATE utf8mb4_bin NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP COMMENT '更新时间';


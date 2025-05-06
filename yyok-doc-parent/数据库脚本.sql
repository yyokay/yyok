-- ----------------------------
-- Table structure for exam_info
-- ----------------------------
DROP TABLE IF EXISTS `exam_info`;
CREATE TABLE `exam_info` (
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '考试名称',
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '考试描述',
  `open_type` int NOT NULL DEFAULT '1' COMMENT '1公开2部门3定员',
  `state` int NOT NULL DEFAULT '0' COMMENT '考试状态',
  `time_limit` tinyint NOT NULL DEFAULT '0' COMMENT '是否限时',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `total_score` int NOT NULL DEFAULT '0' COMMENT '总分数',
  `total_time` int NOT NULL DEFAULT '0' COMMENT '总时长（分钟）',
  `qualify_score` int NOT NULL DEFAULT '0' COMMENT '及格分数',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试主表';


-- ----------------------------
-- Table structure for exam_depart
-- ----------------------------
DROP TABLE IF EXISTS `exam_depart`;
CREATE TABLE `exam_depart` (
  `code_exam` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '考试code',
  `code_depart` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门code',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试部门';

-- ----------------------------
-- Records of exam_depart
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for exam_repo
-- ----------------------------
DROP TABLE IF EXISTS `exam_repo`;
CREATE TABLE `exam_repo` (
  `code_exam` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '考试code',
  `code_repo` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题库code',
  `radio_count` int NOT NULL DEFAULT '0' COMMENT '单选题数量',
  `radio_score` int NOT NULL DEFAULT '0' COMMENT '单选题分数',
  `multi_count` int NOT NULL DEFAULT '0' COMMENT '多选题数量',
  `multi_score` int NOT NULL DEFAULT '0' COMMENT '多选题分数',
  `judge_count` int NOT NULL DEFAULT '0' COMMENT '判断题数量',
  `judge_score` int NOT NULL DEFAULT '0' COMMENT '判断题分数',
  `saq_count` int NOT NULL DEFAULT '0' COMMENT '简答题数量',
  `saq_score` int NOT NULL DEFAULT '0' COMMENT '简答题分数',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试题库';


-- ----------------------------
-- Table structure for exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper` (
  `code_user` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户code',
  `code_depart` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门code',
  `code_exam` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '考试code',
  `title` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '考试标题',
  `total_time` int NOT NULL DEFAULT '0' COMMENT '考试时长',
  `user_time` int NOT NULL DEFAULT '0' COMMENT '用户时长',
  `total_score` int NOT NULL DEFAULT '0' COMMENT '试卷总分',
  `qualify_score` int NOT NULL DEFAULT '0' COMMENT '及格分',
  `obj_score` int NOT NULL DEFAULT '0' COMMENT '客观分',
  `subj_score` int NOT NULL DEFAULT '0' COMMENT '主观分',
  `user_score` int NOT NULL COMMENT '用户得分',
  `has_saq` tinyint NOT NULL DEFAULT '0' COMMENT '是否包含简答题',
  `limit_time` datetime DEFAULT NULL COMMENT '截止时间',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试记录';

-- ----------------------------
-- Records of exam_paper
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for exam_paper_qu
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_qu`;
CREATE TABLE `exam_paper_qu` (
  `code_paper` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷code',
  `code_qu` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目code',
  `qu_type` int NOT NULL COMMENT '题目类型',
  `answered` tinyint NOT NULL DEFAULT '0' COMMENT '是否已答',
  `answer` varchar(5000) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主观答案',
  `score` int NOT NULL DEFAULT '0' COMMENT '单题分分值',
  `actual_score` int NOT NULL DEFAULT '0' COMMENT '实际得分(主观题)',
  `is_right` tinyint NOT NULL DEFAULT '0' COMMENT '是否答对',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试记录考题';

-- ----------------------------
-- Records of exam_paper_qu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for exam_paper_qu_answer
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_qu_answer`;
CREATE TABLE `exam_paper_qu_answer` (
  `code_paper` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷code',
  `code_answer` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '回答项code',
  `code_qu` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目code',
  `is_right` tinyint NOT NULL DEFAULT '0' COMMENT '是否正确项',
  `checked` tinyint NOT NULL DEFAULT '0' COMMENT '是否选中',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `abc` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '选项标签',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试记录答案';

-- ----------------------------
-- Records of exam_paper_qu_answer
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for exam_qu
-- ----------------------------
DROP TABLE IF EXISTS `exam_qu`;
CREATE TABLE `exam_qu` (
  `qu_type` int NOT NULL COMMENT '题目类型',
  `level` int NOT NULL DEFAULT '1' COMMENT '1普通,2较难',
  `image` varchar(500) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '题目图片',
  `content` varchar(2000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '题目备注',
  `analysis` varchar(2000) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '整题解析',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='试题主表';


-- ----------------------------
-- Table structure for exam_qu_answer
-- ----------------------------
DROP TABLE IF EXISTS `exam_qu_answer`;
CREATE TABLE `exam_qu_answer` (
  `code_qu` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题code',
  `is_right` tinyint NOT NULL DEFAULT '0' COMMENT '是否正确',
  `image` varchar(500) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '选项图片',
  `content` varchar(5000) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '答案内容',
  `analysis` varchar(5000) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '答案分析',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='试题答案选项';


-- ----------------------------
-- Table structure for exam_qu_repo
-- ----------------------------
DROP TABLE IF EXISTS `exam_qu_repo`;
CREATE TABLE `exam_qu_repo` (
  `code_qu` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '试题code',
  `code_repo` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '归属题库',
  `qu_type` int NOT NULL DEFAULT '0' COMMENT '题目类型',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='试题题库关联';


-- ----------------------------
-- Table structure for exam_repo
-- ----------------------------
DROP TABLE IF EXISTS `exam_repo`;
CREATE TABLE `exam_repo` (
  `code` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '题库编号',
  `title` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '题库名称',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='题库信息';

-- ----------------------------
-- Table structure for exam_user_book
-- ----------------------------
DROP TABLE IF EXISTS `exam_user_book`;
CREATE TABLE `exam_user_book` (

  `code_exam` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '考试code',
  `code_user` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户code',
  `code_qu` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目code',
  `wrong_count` int NOT NULL COMMENT '错误时间',
  `title` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目标题',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='错题本';

-- ----------------------------
-- Records of exam_user_book
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for exam_user_exam
-- ----------------------------
DROP TABLE IF EXISTS `exam_user_exam`;
CREATE TABLE `exam_user_exam` (
  `code_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户code',
  `code_exam` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '考试code',
  `try_count` int NOT NULL DEFAULT '1' COMMENT '考试次数',
  `max_score` int NOT NULL DEFAULT '0' COMMENT '最高分数',
  `passed` tinyint NOT NULL DEFAULT '0' COMMENT '是否通过',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `pcode` varchar(64) DEFAULT NULL COMMENT '父编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `status` int(4) DEFAULT '1' COMMENT '状态',
  `enabled` int(4) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `is_del` int(4) DEFAULT '1' COMMENT '是否删除：0删除、1不删除',
  `sort` int(4) DEFAULT '1' COMMENT '排序',
  `type` int(4) DEFAULT '1' COMMENT '类型',
  `permission_data` varchar(64) DEFAULT NULL COMMENT '数据权限',
  `permission_scope` varchar(64) DEFAULT NULL COMMENT '权限范围',
  `permission_by` varchar(64) DEFAULT NULL COMMENT '功能权限',
  `auth_by` varchar(64) DEFAULT NULL COMMENT '授权权限',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `group_by` varchar(64) DEFAULT NULL COMMENT '更新组',
  `system_by` varchar(64) DEFAULT NULL COMMENT '所属系统',
  `tenant_by` varchar(64) DEFAULT NULL COMMENT '所属租户',
  `instance_by` varchar(255) DEFAULT NULL COMMENT '实例',
  `version_by` varchar(255) DEFAULT NULL COMMENT '版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='考试记录';

-- ----------------------------
-- Records of exam_user_exam
-- ----------------------------
BEGIN;
COMMIT;



SET FOREIGN_KEY_CHECKS = 1;

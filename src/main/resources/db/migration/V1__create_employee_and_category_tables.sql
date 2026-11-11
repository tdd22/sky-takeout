CREATE TABLE employee
(
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)  NOT NULL COMMENT '员工姓名',
    username    VARCHAR(32)  NOT NULL COMMENT '登录用户名',
    password    VARCHAR(100) NOT NULL COMMENT 'BCrypt密码哈希',
    phone       VARCHAR(11)  NOT NULL COMMENT '手机号',
    sex         TINYINT      NOT NULL COMMENT '性别：0未知，1男，2女',
    id_number   VARCHAR(18)  NOT NULL COMMENT '身份证号',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT       NULL COMMENT '创建人ID',
    update_user BIGINT       NULL COMMENT '修改人ID',

    PRIMARY KEY (id),
    CONSTRAINT uk_employee_username UNIQUE (username),
    CONSTRAINT chk_employee_sex CHECK (sex IN (0, 1, 2)),
    CONSTRAINT chk_employee_status CHECK (status IN (0, 1))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '员工信息';


CREATE TABLE category
(
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    type        TINYINT     NOT NULL COMMENT '类型：1菜品分类，2套餐分类',
    name        VARCHAR(32) NOT NULL COMMENT '分类名称',
    sort        INT         NOT NULL DEFAULT 0 COMMENT '排序值',
    status      TINYINT     NOT NULL DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT      NULL COMMENT '创建人ID',
    update_user BIGINT      NULL COMMENT '修改人ID',

    PRIMARY KEY (id),
    CONSTRAINT uk_category_type_name UNIQUE (type, name),
    CONSTRAINT chk_category_type CHECK (type IN (1, 2)),
    CONSTRAINT chk_category_sort CHECK (sort >= 0),
    CONSTRAINT chk_category_status CHECK (status IN (0, 1)),
    INDEX idx_category_type_status_sort (type, status, sort)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '菜品和套餐分类';
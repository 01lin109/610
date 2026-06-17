-- ====================================
-- 商城后台管理系统 初始化SQL
-- Database: market_db
-- ====================================

CREATE DATABASE IF NOT EXISTS market_db DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE market_db;

-- ----------------------------
-- 1. 系统用户表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码(BCrypt加密)',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    gender TINYINT DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    dept_id BIGINT COMMENT '部门ID',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
    remark VARCHAR(500) COMMENT '备注',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除 0未删 1已删'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- 2. 系统角色表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) NOT NULL UNIQUE COMMENT '角色标识',
    role_sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- 3. 系统菜单表
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    order_num INT DEFAULT 0 COMMENT '排序',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(200) COMMENT '组件路径',
    query VARCHAR(200) COMMENT '路由参数',
    menu_type CHAR(1) DEFAULT 'M' COMMENT 'M目录 C菜单 F按钮',
    visible TINYINT DEFAULT 1 COMMENT '是否可见 0隐藏 1显示',
    perms VARCHAR(100) COMMENT '权限标识',
    icon VARCHAR(100) COMMENT '图标',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- 4. 系统部门表
-- ----------------------------
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0 COMMENT '上级部门ID',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    order_num INT DEFAULT 0 COMMENT '排序',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统部门表';

-- ----------------------------
-- 5. 用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ----------------------------
-- 6. 角色菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- ----------------------------
-- 7. 商品分类表
-- ----------------------------
DROP TABLE IF EXISTS product_category;
CREATE TABLE product_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    level INT DEFAULT 1 COMMENT '层级',
    sort INT DEFAULT 0 COMMENT '排序',
    icon VARCHAR(255) COMMENT '图标',
    status TINYINT DEFAULT 1 COMMENT '状态',
    is_show TINYINT DEFAULT 1 COMMENT '是否展示',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ----------------------------
-- 8. 商品表
-- ----------------------------
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    product_code VARCHAR(50) COMMENT '商品编码',
    category_id BIGINT COMMENT '分类ID',
    category_name VARCHAR(50) COMMENT '分类名称',
    brand VARCHAR(50) COMMENT '品牌',
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '价格',
    cost_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '成本价',
    stock INT DEFAULT 0 COMMENT '库存',
    sales INT DEFAULT 0 COMMENT '销量',
    unit VARCHAR(20) COMMENT '单位',
    main_image VARCHAR(255) COMMENT '主图',
    images TEXT COMMENT '商品图片(JSON)',
    description TEXT COMMENT '商品描述',
    specs TEXT COMMENT '规格参数(JSON)',
    status TINYINT DEFAULT 1 COMMENT '状态 0下架 1上架',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热销',
    is_new TINYINT DEFAULT 0 COMMENT '是否新品',
    sort INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- 9. 订单表
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
    member_id BIGINT COMMENT '会员ID',
    member_name VARCHAR(50) COMMENT '会员名称',
    total_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品总金额',
    freight DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
    discount_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    pay_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '实付金额',
    pay_type TINYINT COMMENT '支付方式 1微信 2支付宝 3银行卡',
    pay_time DATETIME COMMENT '支付时间',
    delivery_no VARCHAR(50) COMMENT '物流单号',
    delivery_company VARCHAR(50) COMMENT '物流公司',
    delivery_time DATETIME COMMENT '发货时间',
    status TINYINT DEFAULT 0 COMMENT '0待付款 1已支付 2已发货 3已完成 4已评价 5已关闭',
    receiver_name VARCHAR(50) COMMENT '收货人',
    receiver_phone VARCHAR(20) COMMENT '收货电话',
    receiver_address VARCHAR(255) COMMENT '收货地址',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- 10. 订单项表
-- ----------------------------
DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    order_no VARCHAR(32) COMMENT '订单号',
    product_id BIGINT COMMENT '商品ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    product_image VARCHAR(255) COMMENT '商品图片',
    product_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品单价',
    quantity INT DEFAULT 1 COMMENT '数量',
    total_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '小计金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- ----------------------------
-- 11. 会员表
-- ----------------------------
DROP TABLE IF EXISTS member;
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    gender TINYINT DEFAULT 0 COMMENT '性别',
    birthday DATE COMMENT '生日',
    points INT DEFAULT 0 COMMENT '积分',
    balance DECIMAL(10,2) DEFAULT 0.00 COMMENT '余额',
    total_consume DECIMAL(12,2) DEFAULT 0.00 COMMENT '累计消费',
    level_id BIGINT COMMENT '会员等级ID',
    level_name VARCHAR(50) COMMENT '会员等级',
    status TINYINT DEFAULT 1 COMMENT '0禁用 1正常',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- ----------------------------
-- 12. 会员等级表
-- ----------------------------
DROP TABLE IF EXISTS member_level;
CREATE TABLE member_level (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level_name VARCHAR(50) NOT NULL COMMENT '等级名称',
    min_points INT DEFAULT 0 COMMENT '最低积分',
    max_points INT DEFAULT 0 COMMENT '最高积分',
    discount DECIMAL(3,2) DEFAULT 1.00 COMMENT '折扣率',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';

-- ----------------------------
-- 13. 优惠券表
-- ----------------------------
DROP TABLE IF EXISTS coupon;
CREATE TABLE coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_name VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    coupon_type TINYINT DEFAULT 1 COMMENT '1满减券 2折扣券 3代金券',
    discount_value DECIMAL(10,2) NOT NULL COMMENT '优惠值',
    min_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '使用门槛金额',
    total_count INT DEFAULT 0 COMMENT '发放总量',
    received_count INT DEFAULT 0 COMMENT '已领取数量',
    used_count INT DEFAULT 0 COMMENT '已使用数量',
    per_limit INT DEFAULT 1 COMMENT '每人限领',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    valid_days INT COMMENT '有效天数',
    status TINYINT DEFAULT 1 COMMENT '0禁用 1启用',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- ----------------------------
-- 14. 促销活动表
-- ----------------------------
DROP TABLE IF EXISTS promotion;
CREATE TABLE promotion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    promotion_name VARCHAR(100) NOT NULL COMMENT '活动名称',
    promotion_type TINYINT DEFAULT 1 COMMENT '1满减 2限时折扣 3秒杀 4拼团',
    rule VARCHAR(500) COMMENT '活动规则(JSON)',
    discount_value DECIMAL(10,2) COMMENT '折扣值',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status TINYINT DEFAULT 0 COMMENT '0未开始 1进行中 2已结束',
    product_ids TEXT COMMENT '参与商品ID(JSON)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='促销活动表';

-- ----------------------------
-- 15. 轮播图表
-- ----------------------------
DROP TABLE IF EXISTS banner;
CREATE TABLE banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    banner_name VARCHAR(50) NOT NULL COMMENT '轮播图名称',
    image_url VARCHAR(255) NOT NULL COMMENT '图片URL',
    link_url VARCHAR(255) COMMENT '链接地址',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '0禁用 1启用',
    remark VARCHAR(500) COMMENT '备注',
    start_time DATETIME COMMENT '展示开始时间',
    end_time DATETIME COMMENT '展示结束时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- ----------------------------
-- 16. 广告表
-- ----------------------------
DROP TABLE IF EXISTS advertisement;
CREATE TABLE advertisement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ad_name VARCHAR(100) NOT NULL COMMENT '广告名称',
    ad_type TINYINT DEFAULT 1 COMMENT '1图片 2链接 3视频',
    ad_position INT COMMENT '广告位 1首页 2商品页 3详情页',
    image_url VARCHAR(255) COMMENT '图片URL',
    link_url VARCHAR(255) COMMENT '链接地址',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '0禁用 1启用',
    click_count INT DEFAULT 0 COMMENT '点击量',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='广告表';

-- ----------------------------
-- 17. 文章表
-- ----------------------------
DROP TABLE IF EXISTS article;
CREATE TABLE article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    category_id BIGINT COMMENT '分类ID',
    category_name VARCHAR(50) COMMENT '分类名称',
    summary VARCHAR(500) COMMENT '摘要',
    content MEDIUMTEXT COMMENT '内容',
    cover_image VARCHAR(255) COMMENT '封面图',
    author VARCHAR(50) COMMENT '作者',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶',
    status TINYINT DEFAULT 1 COMMENT '0草稿 1已发布',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- 18. 公告表
-- ----------------------------
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content MEDIUMTEXT COMMENT '内容',
    notice_type TINYINT DEFAULT 1 COMMENT '1系统公告 2活动通知 3帮助中心',
    status TINYINT DEFAULT 1 COMMENT '0草稿 1已发布',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶',
    publisher_id BIGINT COMMENT '发布人ID',
    publisher_name VARCHAR(50) COMMENT '发布人',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ----------------------------
-- 19. 退款表
-- ----------------------------
DROP TABLE IF EXISTS refund;
CREATE TABLE refund (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refund_no VARCHAR(32) NOT NULL UNIQUE COMMENT '退款单号',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    order_no VARCHAR(32) COMMENT '订单号',
    member_id BIGINT COMMENT '会员ID',
    member_name VARCHAR(50) COMMENT '会员名称',
    refund_amount DECIMAL(10,2) NOT NULL COMMENT '退款金额',
    reason VARCHAR(500) COMMENT '退款原因',
    evidence VARCHAR(1000) COMMENT '凭证图片(JSON)',
    status TINYINT DEFAULT 0 COMMENT '0待处理 1已通过 2已驳回 3已完成',
    handle_note VARCHAR(500) COMMENT '处理备注',
    handler_id BIGINT COMMENT '处理人ID',
    handle_time DATETIME COMMENT '处理时间',
    apply_time DATETIME COMMENT '申请时间',
    finish_time DATETIME COMMENT '完成时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款表';

-- ----------------------------
-- 20. 交易流水表
-- ----------------------------
DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_no VARCHAR(32) NOT NULL UNIQUE COMMENT '交易流水号',
    order_id BIGINT COMMENT '订单ID',
    order_no VARCHAR(32) COMMENT '订单号',
    member_id BIGINT COMMENT '会员ID',
    member_name VARCHAR(50) COMMENT '会员名称',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    transaction_type TINYINT DEFAULT 1 COMMENT '1支付 2退款 3充值',
    pay_type TINYINT COMMENT '支付方式',
    pay_no VARCHAR(100) COMMENT '第三方支付流水号',
    status TINYINT DEFAULT 1 COMMENT '1成功 0失败',
    remark VARCHAR(500) COMMENT '备注',
    transaction_time DATETIME COMMENT '交易时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易流水表';

-- ----------------------------
-- 21. 系统配置表
-- ----------------------------
DROP TABLE IF EXISTS system_config;
CREATE TABLE system_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    config_type VARCHAR(50) DEFAULT 'basic' COMMENT '配置类型 basic/logistics/pay/sms',
    config_name VARCHAR(100) COMMENT '配置名称',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ====================================
-- 初始数据
-- ====================================

-- 系统用户 admin/123456 (BCrypt)
INSERT INTO sys_user (username, password, nickname, email, phone, gender, dept_id, status, remark) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 'admin@market.com', '13800000000', 1, 1, 1, '系统内置管理员');

-- 系统角色
INSERT INTO sys_role (role_name, role_key, role_sort, status, remark) VALUES
('超级管理员', 'admin', 1, 1, '拥有所有权限'),
('运营人员', 'operator', 2, 1, '商品、订单、会员管理'),
('财务人员', 'finance', 3, 1, '财务管理权限'),
('内容编辑', 'editor', 4, 1, '内容管理权限');

-- 系统菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, perms, icon, status) VALUES
('系统管理', 0, 1, '/system', '', 'M', 1, '', 'Setting', 1),
('用户管理', 1, 1, '/system/user', 'system/user/index', 'C', 1, 'system:user:list', 'User', 1),
('角色管理', 1, 2, '/system/role', 'system/role/index', 'C', 1, 'system:role:list', 'UserFilled', 1),
('菜单管理', 1, 3, '/system/menu', 'system/menu/index', 'C', 1, 'system:menu:list', 'Menu', 1),
('部门管理', 1, 4, '/system/dept', 'system/dept/index', 'C', 1, 'system:dept:list', 'OfficeBuilding', 1),
('商品管理', 0, 2, '/product', '', 'M', 1, '', 'Goods', 1),
('商品列表', 6, 1, '/product/list', 'product/index', 'C', 1, 'product:list', 'GoodsFilled', 1),
('分类管理', 6, 2, '/product/category', 'product/category', 'C', 1, 'product:category:list', 'Menu', 1),
('订单管理', 0, 3, '/order', 'order/index', 'C', 1, 'order:list', 'Document', 1),
('会员管理', 0, 4, '/member', 'member/index', 'C', 1, 'member:list', 'User', 1),
('促销管理', 0, 5, '/promotion', '', 'M', 1, '', 'Discount', 1),
('优惠券', 11, 1, '/promotion/coupon', 'promotion/coupon', 'C', 1, 'promotion:coupon:list', 'Ticket', 1),
('活动管理', 11, 2, '/promotion/activity', 'promotion/activity', 'C', 1, 'promotion:activity:list', 'Gift', 1),
('运营管理', 0, 6, '/operation', '', 'M', 1, '', 'Monitor', 1),
('轮播图', 14, 1, '/operation/banner', 'operation/banner', 'C', 1, 'operation:banner:list', 'Picture', 1),
('广告管理', 14, 2, '/operation/ad', 'operation/ad', 'C', 1, 'operation:ad:list', 'Postcard', 1),
('内容管理', 0, 7, '/content', '', 'M', 1, '', 'Notebook', 1),
('文章管理', 17, 1, '/content/article', 'content/article', 'C', 1, 'content:article:list', 'Edit', 1),
('公告管理', 17, 2, '/content/notice', 'content/notice', 'C', 1, 'content:notice:list', 'Bell', 1),
('统计报表', 0, 8, '/statistics', 'statistics/index', 'C', 1, 'statistics:list', 'TrendCharts', 1),
('财务管理', 0, 9, '/finance', '', 'M', 1, '', 'Money', 1),
('交易流水', 21, 1, '/finance/transaction', 'finance/transaction', 'C', 1, 'finance:transaction:list', 'CreditCard', 1),
('退款管理', 21, 2, '/finance/refund', 'finance/refund', 'C', 1, 'finance:refund:list', 'Wallet', 1),
('系统设置', 0, 10, '/setting', 'setting/index', 'C', 1, 'setting:list', 'Tools', 1);

-- 部门
INSERT INTO sys_dept (parent_id, dept_name, order_num, leader, phone, status) VALUES
(0, '总部', 1, '管理员', '13800000000', 1),
(0, '技术部', 2, '张三', '13800000001', 1),
(0, '运营部', 3, '李四', '13800000002', 1),
(0, '财务部', 4, '王五', '13800000003', 1);

-- 用户角色关联 (admin -> 超级管理员)
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 系统配置默认值
INSERT INTO system_config (config_key, config_value, config_type, config_name, remark) VALUES
('site_name', 'Market商城后台管理系统', 'basic', '站点名称', ''),
('site_logo', '', 'basic', '站点LOGO', ''),
('copyright', 'Copyright 2026 Market', 'basic', '版权信息', ''),
('order_timeout', '30', 'basic', '订单超时(分钟)', '未支付订单超时自动取消'),
('default_freight', '10.00', 'logistics', '默认运费', ''),
('free_freight_amount', '99.00', 'logistics', '包邮门槛', '订单满该金额免运费'),
('wechat_pay', '{"enabled":true}', 'pay', '微信支付配置', 'JSON格式'),
('ali_pay', '{"enabled":true}', 'pay', '支付宝配置', 'JSON格式'),
('sms_provider', 'aliyun', 'sms', '短信服务商', 'aliyun/tencent'),
('sms_sign', 'Market商城', 'sms', '短信签名', '');

-- 商品分类
INSERT INTO product_category (category_name, parent_id, level, sort, status, is_show) VALUES
('电子产品', 0, 1, 1, 1, 1),
('服装鞋帽', 0, 1, 2, 1, 1),
('食品饮料', 0, 1, 3, 1, 1),
('手机', 1, 2, 1, 1, 1),
('电脑', 1, 2, 2, 1, 1),
('男装', 2, 2, 1, 1, 1),
('女装', 2, 2, 2, 1, 1);

-- 会员等级
INSERT INTO member_level (level_name, min_points, max_points, discount, remark) VALUES
('普通会员', 0, 999, 1.00, '新注册用户'),
('银卡会员', 1000, 4999, 0.95, '消费满1000元'),
('金卡会员', 5000, 19999, 0.90, '消费满5000元'),
('钻石会员', 20000, -1, 0.85, '消费满20000元');

-- 示例商品
INSERT INTO product (product_name, product_code, category_id, category_name, brand, price, cost_price, stock, sales, unit, status, is_recommend, is_hot, is_new) VALUES
('iPhone 15 Pro Max', 'PHONE-001', 4, '手机', 'Apple', 9999.00, 8500.00, 100, 256, '台', 1, 1, 1, 1),
('MacBook Pro 14', 'PC-001', 5, '电脑', 'Apple', 14999.00, 12500.00, 50, 128, '台', 1, 1, 0, 1),
('商务休闲男装', 'CLO-001', 6, '男装', '定制', 599.00, 300.00, 200, 89, '件', 1, 0, 1, 0),
('时尚连衣裙', 'CLO-002', 7, '女装', '设计款', 899.00, 450.00, 150, 320, '件', 1, 1, 1, 1);

-- 示例会员
INSERT INTO member (username, nickname, phone, email, gender, points, balance, total_consume, level_id, level_name, status) VALUES
('zhangsan', '张三', '13900000001', 'zhangsan@test.com', 1, 2500, 500.00, 15000.00, 3, '金卡会员', 1),
('lisi', '李四', '13900000002', 'lisi@test.com', 2, 800, 100.00, 5000.00, 1, '普通会员', 1),
('wangwu', '王五', '13900000003', 'wangwu@test.com', 1, 15000, 2000.00, 80000.00, 4, '钻石会员', 1);

-- 示例订单
INSERT INTO `order` (order_no, member_id, member_name, total_amount, freight, discount_amount, pay_amount, pay_type, pay_time, delivery_no, delivery_company, delivery_time, status, receiver_name, receiver_phone, receiver_address) VALUES
('20260610000001', 1, '张三', 9999.00, 0.00, 500.00, 9499.00, 1, '2026-06-10 10:30:00', 'SF123456789', '顺丰速运', '2026-06-10 14:00:00', 3, '张三', '13900000001', '广东省深圳市南山区科技园'),
('20260609000001', 2, '李四', 599.00, 10.00, 0.00, 609.00, 2, '2026-06-09 15:20:00', NULL, NULL, NULL, 1, '李四', '13900000002', '北京市朝阳区望京街道'),
('20260608000001', 3, '王五', 14999.00, 0.00, 1000.00, 13999.00, 1, '2026-06-08 09:00:00', 'YT987654321', '圆通速递', '2026-06-08 18:00:00', 2, '王五', '13900000003', '上海市浦东新区陆家嘴');

-- 订单项
INSERT INTO order_item (order_id, order_no, product_id, product_name, product_image, product_price, quantity, total_amount) VALUES
(1, '20260610000001', 1, 'iPhone 15 Pro Max', '', 9999.00, 1, 9999.00),
(2, '20260609000001', 3, '商务休闲男装', '', 599.00, 1, 599.00),
(3, '20260608000001', 2, 'MacBook Pro 14', '', 14999.00, 1, 14999.00);
# 商城后台管理系统 - API接口文档

## 基础信息

- Base URL: `http://localhost:8080/api`
- 认证方式: Bearer Token (JWT)
- 请求头: `Authorization: Bearer <token>`
- 响应格式: JSON

## 1. 认证模块 `/api/auth`

### 1.1 登录
- **POST** `/api/auth/login`
- 请求体: `{ "username": "admin", "password": "123456" }`
- 响应: `{ "code": 200, "data": { "token": "xxx", "userInfo": {...} } }`

### 1.2 获取当前用户信息
- **GET** `/api/auth/info`
- 响应: `{ "code": 200, "data": { "username": "admin", "nickname": "管理员", ... } }`

### 1.3 退出登录
- **POST** `/api/auth/logout`

## 2. 仪表盘模块 `/api/dashboard`

### 2.1 获取仪表盘数据
- **GET** `/api/dashboard`
- 响应: `{ "totalProducts": 100, "totalMembers": 500, "pendingOrders": 15, "deliveredOrders": 200, "totalOrders": 320, "todaySales": 12800.00 }`

## 3. 商品模块 `/api/products`

### 3.1 商品列表
- **GET** `/api/products?page=1&pageSize=10&keyword=&categoryId=&status=`
- 响应: 分页结果

### 3.2 商品详情
- **GET** `/api/products/{id}`

### 3.3 新增商品
- **POST** `/api/products`
- 请求体: `{ "productName": "", "productCode": "", "categoryId": 1, "price": 99.00, ... }`

### 3.4 更新商品
- **PUT** `/api/products`

### 3.5 删除商品
- **DELETE** `/api/products/{id}`

### 3.6 上下架
- **PUT** `/api/products/{id}/status/{status}`

### 3.7 分类列表
- **GET** `/api/products/categories`

### 3.8 分类CRUD
- **GET** `/api/products/categories/{id}`
- **POST** `/api/products/categories`
- **PUT** `/api/products/categories`
- **DELETE** `/api/products/categories/{id}`

## 4. 订单模块 `/api/orders`

### 4.1 订单列表
- **GET** `/api/orders?page=1&pageSize=10&orderNo=&status=`

### 4.2 订单详情
- **GET** `/api/orders/{id}`

### 4.3 更新订单状态
- **PUT** `/api/orders/{id}/status/{status}`

## 5. 会员模块 `/api/members`

### 5.1 会员列表
- **GET** `/api/members?page=1&pageSize=10&keyword=&status=`

### 5.2 会员详情
- **GET** `/api/members/{id}`

### 5.3 更新会员
- **PUT** `/api/members`

### 5.4 更新会员状态
- **PUT** `/api/members/{id}/status/{status}`

### 5.5 会员等级列表
- **GET** `/api/members/levels`
- CRUD 类似标准接口

## 6. 促销模块 `/api/promotions`

### 6.1 优惠券
- **GET** `/api/promotions/coupons`
- **GET/POST/PUT/DELETE** `/api/promotions/coupons[/{id}]`

### 6.2 活动
- **GET** `/api/promotions/activities`
- **GET/POST/PUT/DELETE** `/api/promotions/activities[/{id}]`

## 7. 运营模块 `/api/operation`

### 7.1 轮播图
- **GET/POST/PUT/DELETE** `/api/operation/banners[/{id}]`

### 7.2 广告
- **GET/POST/PUT/DELETE** `/api/operation/ads[/{id}]`

## 8. 内容模块 `/api/content`

### 8.1 文章
- **GET/POST/PUT/DELETE** `/api/content/articles[/{id}]`

### 8.2 公告
- **GET/POST/PUT/DELETE** `/api/content/notices[/{id}]`

## 9. 统计模块 `/api/statistics`

### 9.1 销售概览
- **GET** `/api/statistics/sales/overview`

### 9.2 订单统计
- **GET** `/api/statistics/orders`

### 9.3 商品排行
- **GET** `/api/statistics/products/rank?limit=10`

### 9.4 每日销售趋势
- **GET** `/api/statistics/sales/daily?days=7`

## 10. 财务模块 `/api/finance`

### 10.1 交易流水
- **GET** `/api/finance/transactions?transactionNo=&transactionType=`

### 10.2 交易详情
- **GET** `/api/finance/transactions/{id}`

### 10.3 退款列表
- **GET** `/api/finance/refunds?refundNo=&status=`

### 10.4 退款详情
- **GET** `/api/finance/refunds/{id}`

### 10.5 审核通过
- **POST** `/api/finance/refunds/{id}/approve`
- 请求体: `{ "note": "审核通过" }`

### 10.6 审核驳回
- **POST** `/api/finance/refunds/{id}/reject`
- 请求体: `{ "note": "审核驳回" }`

## 11. 权限模块 `/api/permission`

### 11.1 用户管理
- **GET/POST/PUT/DELETE** `/api/permission/users[/{id}]`
- **PUT** `/api/permission/users/{id}/status/{status}`

### 11.2 角色管理
- **GET/POST/PUT/DELETE** `/api/permission/roles[/{id}]`

### 11.3 菜单管理
- **GET/POST/PUT/DELETE** `/api/permission/menus[/{id}]`

### 11.4 部门管理
- **GET/POST/PUT/DELETE** `/api/permission/depts[/{id}]`

## 12. 系统设置 `/api/settings`

### 12.1 按类型查询
- **GET** `/api/settings?configType=basic`

### 12.2 批量更新
- **POST** `/api/settings/batch`
- 请求体: `[{ "configKey": "site_name", "configValue": "XX商城", "configType": "basic" }]`

## 13. 通用错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 业务异常 / 参数错误 |
| 401 | 未登录 / Token过期 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 系统错误 |

---

*文档版本: V1.0*
*编写日期: 2026-06-10*

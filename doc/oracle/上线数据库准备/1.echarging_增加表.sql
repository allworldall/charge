
create table ECHARGING.LOG_CP_ORDER_PUSH
(
  charge_detail_id NUMBER(15) not null,
  union_order_id   VARCHAR2(50) not null,
  game_id          NUMBER(8) not null,
  gateway_id       NUMBER(10) not null,
  charge_money     NUMBER(10,2) not null,
  charge_amount    NUMBER(10) not null,
  attach_code      VARCHAR2(1000) not null,
  expand_info      VARCHAR2(4000) not null,
  push_time        DATE not null,
  response_time    DATE,
  result           NUMBER(5),
  push_count       NUMBER(5),
  user_name        VARCHAR2(50)
)
tablespace ECHARGING_USER01
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table ECHARGING.LOG_CP_ORDER_PUSH
  is 'CP 充值订单推送记录表';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_detail_id
  is '蓝港充值系统订单号';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.union_order_id
  is '联运方订单号';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.game_id
  is '游戏ID';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.gateway_id
  is '网关ID';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_money
  is '充值金额';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_amount
  is '充值元宝数';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.attach_code
  is '附加字段';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.expand_info
  is '拓展信息';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.push_time
  is '推送时间';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.response_time
  is '推送成功相应时间';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.result
  is '推送返回结果';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.push_count
  is '推送次数';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.user_name
  is '推送游戏账号';
alter table ECHARGING.LOG_CP_ORDER_PUSH
  add constraint PK_LOG_CP_ORDER_PUSH primary key (UNION_ORDER_ID, CHARGE_DETAIL_ID, GAME_ID)
  using index 
  tablespace ECHARGING_USER01
  pctfree 10
  initrans 2
  maxtrans 255;


create table ECHARGING.LOG_CP_PAYMENT_ORDER
(
  union_order_id   VARCHAR2(50) not null,
  charge_detail_id NUMBER(15),
  payment_id       VARCHAR2(50),
  user_name        VARCHAR2(50) not null,
  cp_id            NUMBER(5) not null,
  game_id          NUMBER(8) not null,
  gateway_id       NUMBER(10) not null,
  charge_money     NUMBER(10,2),
  charge_amount    NUMBER(10) not null,
  cp_callback_time DATE not null,
  platform_name    VARCHAR2(50),
  attach_code      VARCHAR2(1000),
  expand_info      VARCHAR2(4000),
  success_time     DATE,
  state            NUMBER(2) not null,
  server_ip        NUMBER(10),
  request_ip       NUMBER(10)
)
tablespace ECHARGING_USER01
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ECHARGING.LOG_CP_PAYMENT_ORDER
  is 'CP 支付成功回调记录信息表';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.union_order_id
  is '联运订单号ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_detail_id
  is '蓝港充值订单号';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.payment_id
  is '预支付id';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.user_name
  is '充值玩家账号';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.cp_id
  is '联运商ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.game_id
  is '游戏ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.gateway_id
  is '区服ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_money
  is '充值金额';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_amount
  is '充值获取的元宝数';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.cp_callback_time
  is 'CP通知成功时间';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.platform_name
  is 'CP 平台名称';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.attach_code
  is '游戏透传字段信息';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.expand_info
  is '拓展字段';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.success_time
  is '支付成功时间';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.state
  is '是否推送成功0，未推送成功，1 推送成功';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.server_ip
  is '服务器IP';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.request_ip
  is '用户请求IP';
alter table ECHARGING.LOG_CP_PAYMENT_ORDER
  add constraint PK_LOG_CP_PAYMENT_ORDER primary key (UNION_ORDER_ID, GAME_ID)
  using index 
  tablespace ECHARGING_USER01
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ECHARGING.LOG_CP_PAYMENT_ORDER
  add constraint IDX_LOG_CP_PAYMENT_ORDER unique (CHARGE_DETAIL_ID)
  using index 
  tablespace ECHARGING_USER01
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


create table ECHARGING.LOG_PRE_PAYMENT
(
  payment_id     VARCHAR2(50) not null,
  user_name      VARCHAR2(50) not null,
  cp_id          NUMBER(5) not null,
  game_id        NUMBER(8) not null,
  gateway_id     NUMBER(10) not null,
  charge_money   NUMBER(10,2) not null,
  charge_amount  NUMBER(10),
  payment_time   DATE not null,
  platform_name  VARCHAR2(100),
  attach_code    VARCHAR2(1000),
  expand_info    VARCHAR2(4000),
  state          NUMBER(2) not null,
  server_ip      NUMBER(10),
  request_ip     NUMBER(10),
  union_order_id VARCHAR2(200),
  role_id        NUMBER(10)
)
tablespace ECHARGING_USER01
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ECHARGING.LOG_PRE_PAYMENT
  is '预支付信息记录表';
comment on column ECHARGING.LOG_PRE_PAYMENT.payment_id
  is '预支付ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.user_name
  is '预支付玩家账号';
comment on column ECHARGING.LOG_PRE_PAYMENT.cp_id
  is '联运伙伴ID是由我们在后台进行分配';
comment on column ECHARGING.LOG_PRE_PAYMENT.game_id
  is '游戏ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.gateway_id
  is '区服ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.charge_money
  is '充值金额';
comment on column ECHARGING.LOG_PRE_PAYMENT.charge_amount
  is '充值金额对应获取的元宝数';
comment on column ECHARGING.LOG_PRE_PAYMENT.payment_time
  is '预支付时间';
comment on column ECHARGING.LOG_PRE_PAYMENT.platform_name
  is '预支付合作商名称';
comment on column ECHARGING.LOG_PRE_PAYMENT.attach_code
  is '游戏透传字段信息';
comment on column ECHARGING.LOG_PRE_PAYMENT.expand_info
  is '拓展信息';
comment on column ECHARGING.LOG_PRE_PAYMENT.state
  is '是否支付成功0，未支付成功，1 支付成功';
comment on column ECHARGING.LOG_PRE_PAYMENT.server_ip
  is '服务器IP';
comment on column ECHARGING.LOG_PRE_PAYMENT.request_ip
  is '请求IP';
comment on column ECHARGING.LOG_PRE_PAYMENT.union_order_id
  is '合作伙伴订单ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.role_id
  is '角色ID';
alter table ECHARGING.LOG_PRE_PAYMENT
  add constraint PK_LOG_PRE_PAYMENT primary key (PAYMENT_ID, USER_NAME, GAME_ID)
  using index 
  tablespace ECHARGING_USER01
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


spool off

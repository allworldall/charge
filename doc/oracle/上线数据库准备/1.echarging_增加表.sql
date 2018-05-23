
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
  is 'CP ��ֵ�������ͼ�¼��';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_detail_id
  is '���۳�ֵϵͳ������';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.union_order_id
  is '���˷�������';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.game_id
  is '��ϷID';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.gateway_id
  is '����ID';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_money
  is '��ֵ���';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.charge_amount
  is '��ֵԪ����';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.attach_code
  is '�����ֶ�';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.expand_info
  is '��չ��Ϣ';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.push_time
  is '����ʱ��';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.response_time
  is '���ͳɹ���Ӧʱ��';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.result
  is '���ͷ��ؽ��';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.push_count
  is '���ʹ���';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.user_name
  is '������Ϸ�˺�';
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
  is 'CP ֧���ɹ��ص���¼��Ϣ��';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.union_order_id
  is '���˶�����ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_detail_id
  is '���۳�ֵ������';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.payment_id
  is 'Ԥ֧��id';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.user_name
  is '��ֵ����˺�';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.cp_id
  is '������ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.game_id
  is '��ϷID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.gateway_id
  is '����ID';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_money
  is '��ֵ���';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.charge_amount
  is '��ֵ��ȡ��Ԫ����';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.cp_callback_time
  is 'CP֪ͨ�ɹ�ʱ��';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.platform_name
  is 'CP ƽ̨����';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.attach_code
  is '��Ϸ͸���ֶ���Ϣ';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.expand_info
  is '��չ�ֶ�';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.success_time
  is '֧���ɹ�ʱ��';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.state
  is '�Ƿ����ͳɹ�0��δ���ͳɹ���1 ���ͳɹ�';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.server_ip
  is '������IP';
comment on column ECHARGING.LOG_CP_PAYMENT_ORDER.request_ip
  is '�û�����IP';
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
  is 'Ԥ֧����Ϣ��¼��';
comment on column ECHARGING.LOG_PRE_PAYMENT.payment_id
  is 'Ԥ֧��ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.user_name
  is 'Ԥ֧������˺�';
comment on column ECHARGING.LOG_PRE_PAYMENT.cp_id
  is '���˻��ID���������ں�̨���з���';
comment on column ECHARGING.LOG_PRE_PAYMENT.game_id
  is '��ϷID';
comment on column ECHARGING.LOG_PRE_PAYMENT.gateway_id
  is '����ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.charge_money
  is '��ֵ���';
comment on column ECHARGING.LOG_PRE_PAYMENT.charge_amount
  is '��ֵ����Ӧ��ȡ��Ԫ����';
comment on column ECHARGING.LOG_PRE_PAYMENT.payment_time
  is 'Ԥ֧��ʱ��';
comment on column ECHARGING.LOG_PRE_PAYMENT.platform_name
  is 'Ԥ֧������������';
comment on column ECHARGING.LOG_PRE_PAYMENT.attach_code
  is '��Ϸ͸���ֶ���Ϣ';
comment on column ECHARGING.LOG_PRE_PAYMENT.expand_info
  is '��չ��Ϣ';
comment on column ECHARGING.LOG_PRE_PAYMENT.state
  is '�Ƿ�֧���ɹ�0��δ֧���ɹ���1 ֧���ɹ�';
comment on column ECHARGING.LOG_PRE_PAYMENT.server_ip
  is '������IP';
comment on column ECHARGING.LOG_PRE_PAYMENT.request_ip
  is '����IP';
comment on column ECHARGING.LOG_PRE_PAYMENT.union_order_id
  is '������鶩��ID';
comment on column ECHARGING.LOG_PRE_PAYMENT.role_id
  is '��ɫID';
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

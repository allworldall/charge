alter table ECHARGING.log_cp_order_push drop COLUMN attach_code ;
alter table ECHARGING.log_cp_order_push drop COLUMN expand_info ;
alter table ECHARGING.log_cp_order_push add attach_code VARCHAR2(1000) null;
alter table ECHARGING.log_cp_order_push add expand_info VARCHAR2(4000) null;
comment on column ECHARGING.LOG_CP_ORDER_PUSH.attach_code
is '�����ֶ�';
comment on column ECHARGING.LOG_CP_ORDER_PUSH.expand_info
is '��չ��Ϣ';
alter table ECHARGING.log_pre_payment modify payment_id varchar2(100);        --���ϲ�ͨʹ�� ������ʱ�Ļ�varchar2(50)

alter table ECHARGING.log_cp_payment_order modify payment_id varchar2(100);	--���ϲ�ͨʹ�ã�����ʱ�Ļ�varchar2(50)
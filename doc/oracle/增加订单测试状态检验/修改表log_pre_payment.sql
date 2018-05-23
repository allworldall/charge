alter table log_pre_payment add test_state NUMBER(2);

comment on column LOG_PRE_PAYMENT.test_state
  is '测试状态；0-正常数据  1-测试数据';
  
update log_pre_payment set test_state = 0; commit;

alter table log_pre_payment modify test_state not null;

create or replace package PKG_ECHARGING_CP_CHARGE is
S_ERATING_PKG_NAME    constant varchar2(50) := 'pkg_game_';
E_CHARGE_ORDER_EXIST  constant integer := -1490;
E_CHARGE_DUPLICATE    constant integer := -1472; --�������ظ�
E_ERROR               constant integer := 0;     --�����ʶ
E_PUSH_ORDER_EXIST    constant integer := -21007;--���Ͷ���������
E_PUSH_ORDER_COUNT    constant integer := -21008;--���������Ѿ��ﵽ����������
E_PUSH_COUNT          constant integer := 3;     --���󶩵����ʹ���
/*
  Ԥ֧���¶����ӿ�
*/
function preCharge(v_payMentId            in varchar2,   --Ԥ֧��ID
                   v_userName             in varchar2,   --Ԥ֧���û��˺�
                   n_cpId                 in number,     --������ID��ʶ
                   n_gameId               in number,     --��ϷID
                   n_gatewayId            in number,     --����ID
                   n_chargeMoney          in number,     --��ֵ���
                   n_chargeAmount         in number,     --��ֵԪ���� 
                   v_platformName         in varchar2,   --����������
                   v_attachCode           in varchar2,   --Ԥ֧��������
                   v_expandInfo           in varchar2,   --��չ��Ϣ       
                   n_roleId               in number,      --��ɫID
                   n_testState            in number,     --����״̬
                   n_productId            in varchar2    --��Ʒ��ʶ
                  ) return integer;
/*
  ͨ��Ԥ֧��ID��ȡԤ֧����Ϣ
*/
function getPreInfo(v_payMentId           in varchar2,   --Ԥ֧��ID
                    cur_result            out sys_refcursor
                   ) return integer;
/**
   ͨ����ϷID�Ͳ�ƷID��ȡProduct������Ϣ
 */
 function getAppleProductInfo(n_gameId     in number,
                              v_productId  in varchar2,
                              cur_result   out sys_refcursor
                             ) return integer;
 /*
   ��¼APPSTORE ��ֵ��־��
 */
function cpChargeCallBackForApple
                          (v_unionOrderId                 in varchar2,   --���˶�����
                           n_chargeDetailId               in number,     --���۳�ֵ������
                           v_payMentId                    in varchar2,   --Ԥ֧��ID
                           v_userName                     in varchar2,   --Ԥ֧���û��˺�
                           n_cpId                         in number,     --�������ID
                           n_gameId                       in number,     --��ϷID
                           n_gatewayId                    in number,     --��ֵ����ID
                           n_chargeMoney                  in number,     --��ֵ���
                           n_chargeAmount                 in number,     --��ֵԪ����
                           v_platformName                 in varchar2,   --cp����
                           v_attachCode                   in varchar2,   --������
                           v_expandInfo                   in varchar2,   --��չ��Ϣ
                           v_charge_detail_id             in varchar2,
                           v_user_name                    in varchar2,
                           n_game_id                      in number,
                           n_gateway_id                   in number,
                           v_product_id                   in varchar2,
                           n_quantity                     in number,
                           v_app_item_id                  in varchar2,
                           v_transaction_id               in varchar2,
                           v_purchase_date                in varchar2,
                           v_purchase_date_ms             in varchar2,
                           v_purchase_date_pst            in varchar2,
                           v_original_transaction_id      in varchar2,
                           v_original_purchase_date       in varchar2,
                           v_original_purchase_date_ms    in varchar2,
                           v_original_purchase_date_pst   in varchar2,
                           v_unique_identifier            in varchar2,
                           v_bid                          in varchar2,
                           v_bvrs                         in varchar2,
                           n_charge_amount                in number,
                           n_product_price                in number,
                           n_all_charge_amount            in number,
                           n_all_product_price            in number
                           )return integer ;
/*
  ��¼CP֪ͨ��ֵ�ɹ���Ϣ
*/ 
function cpChargeCallBack(v_unionOrderId     in varchar2,   --���˶�����
                          n_chargeDetailId   in number,     --���۳�ֵ������
                          v_payMentId        in varchar2,   --Ԥ֧��ID
                          v_userName         in varchar2,   --Ԥ֧���û��˺�
                          n_cpId             in number,     --�������ID
                          n_gameId           in number,     --��ϷID
                          n_gatewayId        in number,     --��ֵ����ID
                          n_chargeMoney      in number,     --��ֵ���
                          n_chargeAmount     in number,     --��ֵԪ����
                          v_platformName     in varchar2,   --cp����
                          v_attachCode       in varchar2,   --������
                          v_expandInfo       in varchar2    --��չ��Ϣ
                         ) return integer;
/*
  ���͵���Ϸ������
*/
function pushChargeInfo(v_unionOrderId       in varchar2,   -- ���˶�����
                        n_chargeDetailId     in number,     -- ���۳�ֵ������
                        n_gameId             in number,      -- ��ϷID
                        v_serverIp           in varchar2,
                        v_clientIp           in varchar2
                       ) return integer;
/*
  ��ʼ��¼��ʼ������Ϣ
*/
procedure beginRecordPushInfo(v_unionOrderId   in varchar2,   --���˶�����
                             n_chargeDetailId in number,      --���۶�����
                             v_userName       in varchar2,    --�����û��˺�
                             n_gameId         in number,      --���͵���ϷID
                             n_gatewayId      in number,      --���͵�����ID
                             n_chargeMoney    in number,      --���ͽ��
                             n_chargeAmount   in number,      --����Ԫ����
                             v_attachCode     in varchar2,    --���͸����ֶ�
                             v_expandInfo     in varchar2     --������չ��Ϣ                  
                            );
/*
  ������Ϸ������������¼��Ϣ����
*/
procedure endRecordPushInfo(v_unionOrderId     in varchar2,    --���˶�����
                           n_chargeDetailId   in number,       --���۶�����
                           n_result           in number        --���ͷ��ؽ��
                           );
/*
  ɾ���������ظ���������Ϣ
*/
procedure delPushDupOrder(v_unionOrderId      in varchar2,   --���˶�����
                          n_chargeDetailId    in number      --���۶�����
                         );
end PKG_ECHARGING_CP_CHARGE;
/
create or replace package body PKG_ECHARGING_CP_CHARGE is
/*
  Ԥ֧���¶����ӿ�
*/
function preCharge(v_payMentId            in varchar2,   --Ԥ֧��ID
                   v_userName             in varchar2,   --Ԥ֧���û��˺�
                   n_cpId                 in number,     --������ID��ʶ
                   n_gameId               in number,     --��ϷID
                   n_gatewayId            in number,     --����ID
                   n_chargeMoney          in number,     --��ֵ���
                   n_chargeAmount         in number,     --��ֵԪ���� 
                   v_platformName         in varchar2,   --����������
                   v_attachCode           in varchar2,   --Ԥ֧��������
                   v_expandInfo           in varchar2,   --��չ��Ϣ 
                   n_roleId               in number,     --roleId
                   n_testState            in number,      --����״̬
                   n_productId            in varchar2     --��ƷID
                  ) return integer is
                  
     n_result integer := pkg_linekong_util.S_SUCCESS;
  begin
    begin
      insert into log_pre_payment
                  (payment_id,
                   user_name,
                   cp_id,
                   game_id,
                   gateway_id,
                   charge_money,
                   charge_amount,
                   payment_time,
                   platform_name,
                   attach_code,
                   expand_info,
                   state,
                   role_id,
                   test_state,
                   product_id)
                values
                  (v_payMentId,
                   v_userName,
                   n_cpId,
                   n_gameId,
                   n_gatewayId,
                   n_chargeMoney,
                   n_chargeAmount,
                   sysdate,
                   v_platformName,
                   v_attachCode,
                   v_expandInfo,
                   0,
                   n_roleId,
                   n_testState,
                   n_productId);
       exception 
         when DUP_VAL_ON_INDEX then
         n_result := E_CHARGE_DUPLICATE;
    end;
    return n_result;
  end;
/*
  ͨ��Ԥ֧��ID��ȡԤ֧����Ϣ
*/
function getPreInfo(v_payMentId           in varchar2,   --Ԥ֧��ID
                    cur_result            out sys_refcursor
                   ) return integer is
    n_result       integer := pkg_linekong_util.S_SUCCESS;
    n_val          integer := pkg_linekong_util.S_SUCCESS;
 begin
   --�жϸ�Ԥ֧����Ϣ�Ƿ����
   select count(1) into n_val from log_pre_payment where payment_id = v_payMentId and state = 0;
   if(n_val = 0)then
      n_result := E_CHARGE_ORDER_EXIST;
      goto ExitError;
   else 
     open cur_result for
       select payment_id,user_name,cp_id,game_id,gateway_id,charge_money,charge_amount,product_id from log_pre_payment where payment_id = v_payMentId;
   end if;
   <<ExitError>>
   return n_result;
 end;
 
 /**
   ͨ����ϷID�Ͳ�ƷID��ȡProduct������Ϣ
 */
 function getAppleProductInfo(n_gameId     in number,
                              v_productId  in varchar2,
                              cur_result            out sys_refcursor
                             ) return integer is
    n_result       integer := pkg_linekong_util.S_SUCCESS;
 begin
  
     open cur_result for
     select sap.charge_amount,sap.product_price
		   from sys_appstore_product sap
		  where sap.game_id = n_gameId and sap.product_id= v_productId;
   
   <<ExitError>>
   return n_result;
 end;
 /*
   ��¼APPSTORE ��ֵ��־��
 */
function cpChargeCallBackForApple
                          (v_unionOrderId                 in varchar2,   --���˶�����
                           n_chargeDetailId               in number,     --���۳�ֵ������
                           v_payMentId                    in varchar2,   --Ԥ֧��ID
                           v_userName                     in varchar2,   --Ԥ֧���û��˺�
                           n_cpId                         in number,     --�������ID
                           n_gameId                       in number,     --��ϷID
                           n_gatewayId                    in number,     --��ֵ����ID
                           n_chargeMoney                  in number,     --��ֵ���
                           n_chargeAmount                 in number,     --��ֵԪ����
                           v_platformName                 in varchar2,   --cp����
                           v_attachCode                   in varchar2,   --������
                           v_expandInfo                   in varchar2,   --��չ��Ϣ
                           v_charge_detail_id             in varchar2,
                           v_user_name                    in varchar2,
                           n_game_id                      in number,
                           n_gateway_id                   in number,
                           v_product_id                   in varchar2,
                           n_quantity                     in number,
                           v_app_item_id                  in varchar2,
                           v_transaction_id               in varchar2,
                           v_purchase_date                in varchar2,
                           v_purchase_date_ms             in varchar2,
                           v_purchase_date_pst            in varchar2,
                           v_original_transaction_id      in varchar2,
                           v_original_purchase_date       in varchar2,
                           v_original_purchase_date_ms    in varchar2,
                           v_original_purchase_date_pst   in varchar2,
                           v_unique_identifier            in varchar2,
                           v_bid                          in varchar2,
                           v_bvrs                         in varchar2,
                           n_charge_amount                in number,
                           n_product_price                in number,
                           n_all_charge_amount            in number,
                           n_all_product_price            in number
                          )return integer is           
  n_result         integer := pkg_linekong_util.S_SUCCESS;
  n_val            integer := pkg_linekong_util.S_SUCCESS;
begin
  begin
      --�жϸö����Ƿ��Ѿ�֧��
      select count(1) 
        into n_val 
        from log_cp_payment_order where union_order_id = v_unionOrderId and game_id = n_gameId;
        
      if(n_val >0)then
         return n_result;
      else  
         --�жϸö����Ƿ��Ѿ���ƻ��֧����
         select count(1) into n_val
           from log_appstore_charge
          where transaction_id = v_transaction_id;
          if(n_val >0)then
               n_result := E_CHARGE_DUPLICATE;--�������ظ�
               return n_result;
          else
              begin
       
                  insert into log_appstore_charge
                     (   charge_detail_id              ,           
                         user_name                     ,                  
                         game_id                       ,
                         gateway_id                    ,
                         product_id                    ,
                         quantity                      ,
                         app_item_id                   ,
                         transaction_id                ,
                         purchase_date                 ,
                         purchase_date_ms              ,
                         purchase_date_pst             ,
                         original_transaction_id       ,
                         original_purchase_date        ,
                         original_purchase_date_ms     ,
                         original_purchase_date_pst    ,
                         unique_identifier             ,
                         bid                           ,
                         bvrs                          ,
                         operate_time                  ,
                         charge_amount                 ,
                         product_price                 ,
                         all_charge_amount             ,
                         all_product_price             
                      )                                
                  values(
                         v_charge_detail_id            , 
                         v_user_name                   ,
                         n_game_id                     ,
                         n_gateway_id                  ,
                         v_product_id                  ,
                         n_quantity                    ,
                         v_app_item_id                 ,
                         v_transaction_id              ,
                         v_purchase_date               ,
                         v_purchase_date_ms            ,
                         v_purchase_date_pst           ,
                         v_original_transaction_id     ,
                         v_original_purchase_date      ,
                         v_original_purchase_date_ms   ,
                         v_original_purchase_date_pst  ,
                         v_unique_identifier           ,
                         v_bid                         ,
                         v_bvrs                        ,
                         sysdate                       ,
                         n_charge_amount               ,
                         n_product_price               ,
                         n_all_charge_amount           ,
                         n_all_product_price       
                  );
      
                 insert into log_cp_payment_order
                                  (union_order_id,
                                   charge_detail_id,
                                   payment_id,
                                   user_name,
                                   cp_id,
                                   game_id,
                                   gateway_id,
                                   charge_money,
                                   charge_amount,
                                   cp_callback_time,
                                   platform_name,
                                   attach_code,
                                   expand_info,
                                   success_time,
                                   state)
                                values
                                  (v_unionOrderId,
                                   n_chargeDetailId,
                                   v_payMentId,
                                   v_userName,
                                   n_cpId,
                                   n_gameId,
                                   n_gatewayId,
                                   n_chargeMoney,
                                   n_chargeAmount,
                                   sysdate,
                                   v_platformName,
                                   v_attachCode,
                                   v_expandInfo,
                                   sysdate,
                                   0);
              exception 
                   when DUP_VAL_ON_INDEX then
                   n_result := E_CHARGE_DUPLICATE;
              end;
          end if;
          --����Ԥ֧��������Ϣ�е�֧��״̬�Լ����˶�����Ϣ
          update log_pre_payment
             set state = 1,
                 union_order_id = v_unionOrderId
           where payment_id = v_payMentId;
         
      end if;
   end;
   return n_result;
end;
/*
  ��¼CP֪ͨ��ֵ�ɹ���Ϣ
*/ 
function cpChargeCallBack(v_unionOrderId     in varchar2,   --���˶�����
                          n_chargeDetailId   in number,     --���۳�ֵ������
                          v_payMentId        in varchar2,   --Ԥ֧��ID
                          v_userName         in varchar2,   --Ԥ֧���û��˺�
                          n_cpId             in number,     --�������ID
                          n_gameId           in number,     --��ϷID
                          n_gatewayId        in number,     --��ֵ����ID
                          n_chargeMoney      in number,     --��ֵ���
                          n_chargeAmount     in number,     --��ֵԪ����
                          v_platformName     in varchar2,   --cp����
                          v_attachCode       in varchar2,   --������
                          v_expandInfo       in varchar2    --��չ��Ϣ
                         ) return integer is
    n_result         integer := pkg_linekong_util.S_SUCCESS;
    n_val            integer := pkg_linekong_util.S_SUCCESS;
begin
   begin
      --�жϸö����Ƿ��Ѿ�֧��
      select count(1) 
        into n_val 
        from log_cp_payment_order where union_order_id = v_unionOrderId and game_id = n_gameId;
        
      if(n_val >0)then
         return n_result;
      else
        begin
           insert into log_cp_payment_order
                            (union_order_id,
                             charge_detail_id,
                             payment_id,
                             user_name,
                             cp_id,
                             game_id,
                             gateway_id,
                             charge_money,
                             charge_amount,
                             cp_callback_time,
                             platform_name,
                             attach_code,
                             expand_info,
                             success_time,
                             state)
                          values
                            (v_unionOrderId,
                             n_chargeDetailId,
                             v_payMentId,
                             v_userName,
                             n_cpId,
                             n_gameId,
                             n_gatewayId,
                             n_chargeMoney,
                             n_chargeAmount,
                             sysdate,
                             v_platformName,
                             v_attachCode,
                             v_expandInfo,
                             sysdate,
                             0);
        exception 
             when DUP_VAL_ON_INDEX then
             n_result := E_CHARGE_DUPLICATE;
        end;
        --����Ԥ֧��������Ϣ�е�֧��״̬�Լ����˶�����Ϣ
        update log_pre_payment
           set state = 1,
               union_order_id = v_unionOrderId
         where payment_id = v_payMentId;
         
      end if;
   end;
   return n_result;
end;
/*
  ���͵���Ϸ������
*/
function pushChargeInfo(v_unionOrderId       in varchar2,   -- ���˶�����
                        n_chargeDetailId     in number,     -- ���۳�ֵ������
                        n_gameId             in number,     -- ��ϷID
                        v_serverIp           in varchar2,
                        v_clientIp           in varchar2
                       ) return integer is
    n_result          integer := pkg_linekong_util.S_SUCCESS;
    n_val             integer := pkg_linekong_util.S_SUCCESS;
    v_userName        log_cp_payment_order.user_name%type;
    n_gatewayId       log_cp_payment_order.gateway_id%type;
    n_chargeMoney     log_cp_payment_order.charge_money%type;
    n_chargeAmount    log_cp_payment_order.charge_amount%type;
    v_attachCode      log_cp_payment_order.attach_code%type;
    v_expandInfo      log_cp_payment_order.expand_info%type;
    v_paymentId       log_cp_payment_order.payment_id%type;
begin
  begin
   --��ѯ���˳�ֵ������
   select user_name,gateway_id,charge_money,charge_amount,attach_code,expand_info,payment_id into
         v_userName,n_gatewayId,n_chargeMoney,n_chargeAmount,v_attachCode,v_expandInfo,v_paymentId
    from log_cp_payment_order
   where union_order_id = v_unionOrderId and game_id = n_gameId;
  exception when NO_DATA_FOUND then
    n_result := E_PUSH_ORDER_EXIST;
    goto ExitError;
  end;
  --�ж϶����Ƿ��Ѿ��ﵽ�˴������ʹ���
  select count(1) into n_val
    from log_cp_order_push
   where charge_detail_id = n_chargeDetailId
     and union_order_id = v_unionOrderId
     and push_count = E_PUSH_COUNT;
  if(n_val > 0)then
     n_result := E_PUSH_ORDER_COUNT;
     goto ExitError;
  end if;
  --��¼������Ϣ
  beginRecordPushInfo(v_unionOrderId,n_chargeDetailId,v_userName,n_gameId,n_gatewayId,n_chargeMoney,n_chargeAmount,v_attachCode,v_expandInfo);
  --ִ����Ϣ
  begin
     execute immediate 'begin 
                          :tmp := ' ||
                          S_ERATING_PKG_NAME || n_gameId ||
                          '.PfCharge
                            (:gameId,
                             :userId,
                             :gateway_id,
                             :charge_money,
                             :charge_amount,
                             :charge_time,
                             :server_ip,
                             :client_ip,
                             :charge_detail_id,
                             :orderId,
                             :v_code);
                        end;'
          using out n_result, n_gameId, v_userName, n_gatewayId, n_chargeMoney, n_chargeAmount, sysdate, v_serverIp, v_clientIp, to_number(n_chargeDetailId), v_unionOrderId, v_attachCode;
  end;
  --
  if(n_result =  E_CHARGE_DUPLICATE)then -- �������ظ�
     delPushDupOrder(v_unionOrderId,n_chargeDetailId);
  else
     if(n_result = pkg_linekong_util.S_SUCCESS)then
        delPushDupOrder(v_unionOrderId,n_chargeDetailId);
        update log_cp_payment_order
           set state = 1,
               success_time = sysdate,
               charge_detail_id = n_chargeDetailId
         where union_order_id = v_unionOrderId;
         
         update log_pre_payment
            set state = 1
         where payment_id = v_paymentId;
     else
       endRecordPushInfo(v_unionOrderId,n_chargeDetailId,n_result);
     end if;
  end if;
  <<ExitError>>
  return n_result;
end;
/*
  ��ʼ��¼��ʼ������Ϣ
*/
procedure beginRecordPushInfo(v_unionOrderId   in varchar2,    --���˶�����
                             n_chargeDetailId in number,      --���۶�����
                             v_userName       in varchar2,    --�����û��˺�
                             n_gameId         in number,      --���͵���ϷID
                             n_gatewayId      in number,      --���͵�����ID
                             n_chargeMoney    in number,      --���ͽ��
                             n_chargeAmount   in number,      --����Ԫ����
                             v_attachCode     in varchar2,    --���͸����ֶ�
                             v_expandInfo     in varchar2     --������չ��Ϣ                  
                            ) is
   PRAGMA AUTONOMOUS_TRANSACTION;
begin
  begin
   MERGE INTO log_cp_order_push t 
    using (select v_unionOrderId as unionOrderId,n_chargeDetailId as chargeDetailId from dual) t1
    on(t.union_order_id = t1.unionOrderId and t.charge_detail_id = t1.chargeDetailId) 
    when matched then
      update set t.push_count = t.push_count+1
    when not matched then
      insert (charge_detail_id,
               union_order_id,
               game_id,
               gateway_id,
               charge_money,
               charge_amount,
               attach_code,
               expand_info,
               push_time,
               response_time,
               result,
               push_count,
               user_name)
            values
              (n_chargeDetailId,
               v_unionOrderId,
               n_gameId,
               n_gatewayId,
               n_chargeMoney,
               n_chargeAmount,
               v_attachCode,
               v_expandInfo,
               sysdate,
               sysdate,
               0,
               1,v_userName);
    end;
    commit;
end;
/*
  ������Ϸ������������¼��Ϣ����
*/
procedure endRecordPushInfo(v_unionOrderId     in varchar2,     --���˶�����
                           n_chargeDetailId   in number,       --���۶�����
                           n_result           in number        --���ͷ��ؽ��
                           )  is
   PRAGMA AUTONOMOUS_TRANSACTION;
begin
   update log_cp_order_push
      set response_time = sysdate, result = n_result
    where charge_detail_id = n_chargeDetailId
      and union_order_id = v_unionOrderId;
   commit;
end;
/*
  ɾ���������ظ���������Ϣ
*/
procedure delPushDupOrder(v_unionOrderId      in varchar2,   --���˶�����
                          n_chargeDetailId    in number      --���۶�����
                         ) is
   PRAGMA AUTONOMOUS_TRANSACTION;
begin
   delete from log_cp_order_push where charge_detail_id = n_chargeDetailId and union_order_id = v_unionOrderId;
   commit;
end;
end PKG_ECHARGING_CP_CHARGE;
/

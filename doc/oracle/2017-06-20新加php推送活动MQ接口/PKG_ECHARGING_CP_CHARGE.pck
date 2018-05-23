create or replace package PKG_ECHARGING_CP_CHARGE is
S_ERATING_PKG_NAME    constant varchar2(50) := 'pkg_game_';
E_CHARGE_ORDER_EXIST  constant integer := -1490;
E_CHARGE_DUPLICATE    constant integer := -1472; --�������ظ�
E_ERROR               constant integer := 0;     --�����ʶ
E_PUSH_ORDER_EXIST    constant integer := -21007;--���Ͷ���������
E_PUSH_ORDER_COUNT    constant integer := -21008;--���������Ѿ��ﵽ����������
E_PUSH_COUNT          constant integer := 3;     --���󶩵����ʹ���
/*E_CHARGE_DUPLICATE
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
                   n_productId            in varchar2,   --��Ʒ��ʶ
                   n_var                  in varchar2,   --�汾�ţ���SDK ���������������ֲ�ͬ�汾��
                   n_cpSignType           in varchar2,   --����Ԥ֧��ǩ������
                   n_productDesc          in varchar2,   --��Ʒ����
                   n_productName          in varchar2    --��ƷName     
                  ) return integer;
/*
  ͨ��Ԥ֧��ID��ȡԤ֧����Ϣ
*/
function getPreInfo(v_payMentId           in varchar2,   --Ԥ֧��ID
                    cur_result            out sys_refcursor
                   ) return integer;
/*
   ��ȡ�����ٴ����͵ģ����ͼ�¼
   ���ʹ���С��3�������벻Ϊ0��������ʱ���Ѿ�����2Сʱ
 */
 function getPushInfo(cur_result    out sys_refcursor)return integer ;                 
 /*
  ͨ�����۶����Ż�ȡԤ֧����Ϣ
*/
function getPreInfoByChargeDetailId(   v_unionOrderId      in varchar2,   --���۶���ID
                                       cur_result            out sys_refcursor
                                     ) return integer;                
/*
  ��ȡ��ֵ������״̬
*/
function getChargeOrderStatus(v_payMentId           in varchar2   --Ԥ֧��ID
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
 * ����ǰ��Ϣ���
*/
function pushMQChargeInfoCheck(v_unionOrderId       in varchar2,   -- ���˶�����
                               n_chargeDetailId     in number,     -- ���۳�ֵ������
                               n_gameId             in number     -- ��ϷID
                              ) return integer;
/*
 * MQ���ͺ���Ϣ��¼
 */
function pushMQChargeInfoRecord (v_unionOrderId       in varchar2,   -- ���˶�����
                                 n_chargeDetailId     in number,     -- ���۳�ֵ������
                                 n_serverIp           in varchar2,   -- ������IP
                                 n_clientIp           in varchar2,   -- ����IP
                                 n_userId             in number,     --userId
                                 n_result             in number      -- MQ���ͽ��
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
/**
   ����������־
*/
function recordActMQInfo(n_gameId      in number,  --��ϷID
                          v_userId      in varchar2,--ͨ��֤ID
                          v_userName      in varchar2,--ͨ��֤����
                          n_gatewayId      in number,  --����ID
                          n_chargeChannelId  in number,  --��ֵ����ID
                          n_discount      in number,  --��ֵ�����ۿ�
                          n_chargeSubjectId  in number,  --��ֵ��Ϸ�Ļ�������=
                          n_chargeMoney    in number,  --��ֵ������=
                          n_chargeAmount    in number,  --��ֵԪ����
                          v_chargeOrderCode  in varchar2,--���˶�����
                          n_chargeDetailId  in number,  --��ֵ������
                          n_chargeType    in number,  --��ֵ����1ֱ��
                          n_moneyType      in number,  --�������� 1 �����
                          v_attachCode    in varchar2,--͸���ֶ���Ϣ
                          n_roleId      in number,  --��ɫID
                          v_chargeTime    in varchar2,--�µ�ʱ�� (ʱ������뼶)
                          v_serverIp      in varchar2,--������IP
                          v_clientIp      in varchar2,--����IP
                          v_pushResult    in varchar2 --���ͽ��
                         )return integer; 
/**
   ��ѯ���ͻMQ�Ƿ��ظ�
*/
function checkActMQInfo(n_gameId      in number,  --��ϷID
                          v_chargeOrderCode  in varchar2,--���˶�����
                          n_chargeDetailId  in number  --��ֵ������
                         )return integer;                                                
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
                   n_productId            in varchar2,     --��ƷID
                   n_var                  in varchar2,   --�汾�ţ���SDK ���������������ֲ�ͬ�汾��
                   n_cpSignType           in varchar2,   --����Ԥ֧��ǩ������
                   n_productDesc          in varchar2,   --��Ʒ����
                   n_productName          in varchar2    --��ƷName   
                  ) return integer is
                  
     n_result integer := pkg_linekong_util.S_SUCCESS;
     n_count  integer := 0;
  begin
    begin
      --����Ƿ����ظ��Ķ�����
      select count(1) into n_count from log_pre_payment where payment_id = v_payMentId;
      if( n_count > 0 ) then
           return E_CHARGE_DUPLICATE;
      end if;
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
                   product_id,
                   cp_var,      
                   cp_sign_type,
                   product_desc,
                   product_name
                   )
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
                   n_productId,
                   n_var,         
                   n_cpSignType,
                   n_productDesc, 
                   n_productName
                   );
       exception 
         when DUP_VAL_ON_INDEX then
         n_result := E_ERROR;
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
       select payment_id,user_name,cp_id,game_id,gateway_id,charge_money,charge_amount,product_id,attach_code,expand_info,payment_time,server_ip,request_ip from log_pre_payment where payment_id = v_payMentId;
   end if;
   <<ExitError>>
   return n_result;
 end;
 /*
   ��ȡ�����ٴ����͵ģ����ͼ�¼
   ���ʹ���С��3�������벻Ϊ0��������ʱ���Ѿ�����2Сʱ
 */
 function getPushInfo(cur_result    out sys_refcursor) return integer is
   n_result       integer := pkg_linekong_util.S_SUCCESS;
 begin
   open cur_result for
   select lcop.charge_detail_id,lcop.union_order_id,lcop.game_id,lcop.gateway_id,lcop.charge_money,lcop.charge_amount,lcop.push_time,lcop.response_time,lcop.result,lcop.push_count,lcop.user_name,lcop.attach_code,lcop.expand_info 
     from log_cp_order_push lcop
    where lcop.push_count < E_PUSH_COUNT and( lcop.result <>0 or lcop.response_time < sysdate-2/24);
   return n_result;
 end;
 
 /*
  ͨ�����۶����Ż�ȡԤ֧����Ϣ
*/
function getPreInfoByChargeDetailId(   v_unionOrderId      in varchar2,   --���۶���ID
                                       cur_result            out sys_refcursor
                                     ) return integer is
    n_result       integer := pkg_linekong_util.S_SUCCESS;
    n_val          integer := pkg_linekong_util.S_SUCCESS;
 begin
   --�жϸ�Ԥ֧����Ϣ�Ƿ����
   select count(1) into n_val from log_pre_payment where union_order_id = v_unionOrderId;
   if(n_val = 0)then
      n_result := E_CHARGE_ORDER_EXIST;
      goto ExitError;
   else 
     open cur_result for
       select payment_id,user_name,cp_id,game_id,gateway_id,charge_money,charge_amount,product_id,attach_code,expand_info,payment_time from log_pre_payment where union_order_id = v_unionOrderId;
   end if;
   <<ExitError>>
   return n_result;
 end;
 /*
  ��ȡ��ֵ������״̬
*/
function getChargeOrderStatus(v_payMentId           in varchar2   --Ԥ֧��ID
                   ) return integer is
     n_val          integer := pkg_linekong_util.S_SUCCESS;
begin
   select count(1) into n_val from log_pre_payment where payment_id = v_payMentId and state = 1;
   return n_val;
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
         return E_CHARGE_DUPLICATE;--�������ظ�
      else  
         --�жϸö����Ƿ��Ѿ���ƻ��֧����
         select count(1) into n_val
           from log_appstore_charge
          where transaction_id = v_transaction_id;
          if(n_val >0)then
               n_result := E_CHARGE_DUPLICATE;--�������ظ�
               return n_result;
          else
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
             
              --����Ԥ֧��������Ϣ�е�֧��״̬�Լ����˶�����Ϣ
              update log_pre_payment
                 set state = 1,
                     union_order_id = v_unionOrderId
               where payment_id = v_payMentId;
          end if;
         
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
         return  E_CHARGE_DUPLICATE;--�������ظ�;
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
 * MQ����ǰ��Ϣ���
*/
function pushMQChargeInfoCheck(v_unionOrderId       in varchar2,   -- ���˶�����
                               n_chargeDetailId     in number,     -- ���۳�ֵ������
                               n_gameId             in number     -- ��ϷID
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
   
   <<ExitError>>
  return n_result;
end;

/*
 * MQ���ͺ���Ϣ��¼
 */
function pushMQChargeInfoRecord (v_unionOrderId       in varchar2,   -- ���˶�����
                                 n_chargeDetailId     in number,     -- ���۳�ֵ������
                                 n_serverIp           in varchar2,   -- ������IP
                                 n_clientIp           in varchar2,   -- ����IP
                                 n_userId             in number,     --userId
                                 n_result             in number      -- MQ���ͽ��
                       ) return integer is
    v_paymentId       log_cp_payment_order.payment_id%type;
    v_result          integer := pkg_linekong_util.S_SUCCESS;
    v_chackResult     integer := 0;
    n_gatewayId       log_cp_payment_order.gateway_id%type;
    n_chargeMoney     log_cp_payment_order.charge_money%type;
    n_chargeAmount    log_cp_payment_order.charge_amount%type;
    v_gameId          log_cp_payment_order.game_id%type;
begin 
  begin 
    --��ѯ���˳�ֵ������
    select gateway_id,charge_money,charge_amount,payment_id,game_id into
           n_gatewayId,n_chargeMoney,n_chargeAmount,v_paymentId,v_gameId
      from log_cp_payment_order
     where union_order_id = v_unionOrderId and charge_detail_id = n_chargeDetailId;
    exception when NO_DATA_FOUND then
      v_result := E_PUSH_ORDER_EXIST;
      goto ExitError;
    end;
  
  if(n_result =  E_CHARGE_DUPLICATE)then -- �������ظ�
     delPushDupOrder(v_unionOrderId,n_chargeDetailId);
     --����Ƿ���log_charge_common�������n_chargeDetailId
     select count(1) into v_chackResult from log_charge_common where charge_detail_id = n_chargeDetailId;
     if(v_chackResult <= 0) then
         --���ô洢���̣�����log_charge_common������
          v_result :=pkg_echarging_rating.UnionCharging(n_chargeDetailId,
                     n_userId,
                     v_gameId,
                     n_gatewayId,
                     3,
                     n_chargeAmount,
                     n_chargeMoney,
                     n_clientIp,
                     n_serverIp,
                     sysdate);              
     end if;
  else
     if(n_result = pkg_linekong_util.S_SUCCESS)then
        delPushDupOrder(v_unionOrderId,n_chargeDetailId);
        update log_cp_payment_order
           set state = 1,
               success_time = sysdate,
               charge_detail_id = n_chargeDetailId,
               server_ip = pkg_linekong_util.IP2Num(n_serverIp),
               request_ip = pkg_linekong_util.IP2Num(n_clientIp)
         where union_order_id = v_unionOrderId;
         
         update log_pre_payment
            set state = 1,
                server_ip = pkg_linekong_util.IP2Num(n_serverIp),
                request_ip = pkg_linekong_util.IP2Num(n_clientIp)
         where payment_id = v_paymentId;
         --���ô洢���̣�����log_charge_common������
          v_result :=pkg_echarging_rating.UnionCharging(n_chargeDetailId,
                     n_userId,
                     v_gameId,
                     n_gatewayId,
                     3,
                     n_chargeAmount,
                     n_chargeMoney,
                     n_clientIp,
                     n_serverIp,
                     sysdate);
           
         
     else
       endRecordPushInfo(v_unionOrderId,n_chargeDetailId,n_result);
     end if;
  end if;
  --
                     
                        
  <<ExitError>>
  return v_result;
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
               charge_detail_id = n_chargeDetailId,
               server_ip = pkg_linekong_util.IP2Num(v_serverIp),
               request_ip = pkg_linekong_util.IP2Num(v_clientIp)
         where union_order_id = v_unionOrderId;
         
         update log_pre_payment
            set state = 1,
                server_ip = pkg_linekong_util.IP2Num(v_serverIp),
                request_ip = pkg_linekong_util.IP2Num(v_clientIp)
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
/**
   ����������־
*/
function recordActMQInfo(n_gameId      in number,  --��ϷID
                          v_userId      in varchar2,--ͨ��֤ID
                          v_userName      in varchar2,--ͨ��֤����
                          n_gatewayId      in number,  --����ID
                          n_chargeChannelId  in number,  --��ֵ����ID
                          n_discount      in number,  --��ֵ�����ۿ�
                          n_chargeSubjectId  in number,  --��ֵ��Ϸ�Ļ�������=
                          n_chargeMoney    in number,  --��ֵ������=
                          n_chargeAmount    in number,  --��ֵԪ����
                          v_chargeOrderCode  in varchar2,--���˶�����
                          n_chargeDetailId  in number,  --��ֵ������
                          n_chargeType    in number,  --��ֵ����1ֱ��
                          n_moneyType      in number,  --�������� 1 �����
                          v_attachCode    in varchar2,--͸���ֶ���Ϣ
                          n_roleId      in number,  --��ɫID
                          v_chargeTime    in varchar2,--�µ�ʱ�� (ʱ������뼶)
                          v_serverIp      in varchar2,--������IP
                          v_clientIp      in varchar2,--����IP
                          v_pushResult    in varchar2 --���ͽ��
                         )return integer is
  PRAGMA AUTONOMOUS_TRANSACTION;
begin
  
   MERGE INTO LOG_ACTIVITY_MQ_PUSH t 
    using (select n_gameId as gameId,v_chargeOrderCode as chargeOrderCode,n_chargeDetailId as chargeDetailId from dual) t1
    on(t.game_id = t1.gameId and t.charge_ordercode = t1.chargeOrderCode and t.charge_detailid = t1.chargeDetailId) 
    when matched then
      update set t.push_result = v_pushResult
    when not matched then

         insert  
               (game_id,         
                user_id,         
                user_name,       
                gateway_id,      
                chargechannel_id,
                discount,        
                charge_subjectid,
                charge_money,   
                charge_amount,   
                charge_ordercode,
                charge_detailid, 
                charge_type,     
                money_type,      
                attach_code,    
                role_id,         
                charge_time,     
                server_ip,       
                client_ip,       
                push_result     
               )     
         VALUES(n_gameId,      
                v_userId,      
                v_userName,      
                n_gatewayId,    
                n_chargeChannelId,  
                n_discount,      
                n_chargeSubjectId,  
                n_chargeMoney,    
                n_chargeAmount,    
                v_chargeOrderCode,  
                n_chargeDetailId,  
                n_chargeType,    
                n_moneyType,    
                v_attachCode,    
                n_roleId,      
                v_chargeTime,    
                v_serverIp,      
                v_clientIp,      
                v_pushResult
                );
   commit;      
   return 1;
end;
/**
   ��ѯ���ͻMQ�Ƿ��ظ�
*/
function checkActMQInfo(n_gameId      in number,  --��ϷID
                          v_chargeOrderCode  in varchar2,--���˶�����
                          n_chargeDetailId  in number  --��ֵ������
                         )return integer is
    ordercodeCount          integer := 0;
    detailidCount           integer := 0;
begin
   select count(1) into ordercodeCount from LOG_ACTIVITY_MQ_PUSH
   where game_id = n_gameId and charge_ordercode = v_chargeOrderCode and push_result = 1;
   
   select count(1) into detailidCount from LOG_ACTIVITY_MQ_PUSH
   where game_id = n_gameId and  charge_detailid = n_chargeDetailId and push_result = 1;
    
   if(ordercodeCount > 0) then
        return -1472;
   end if;
   if(detailidCount > 0) then
        return -1472;
   end if;
      
   return 1;      
end;


end PKG_ECHARGING_CP_CHARGE;
/

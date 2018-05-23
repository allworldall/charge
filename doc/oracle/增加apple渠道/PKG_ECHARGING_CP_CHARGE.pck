create or replace package PKG_ECHARGING_CP_CHARGE is
S_ERATING_PKG_NAME    constant varchar2(50) := 'pkg_game_';
E_CHARGE_ORDER_EXIST  constant integer := -1490;
E_CHARGE_DUPLICATE    constant integer := -1472; --订单号重复
E_ERROR               constant integer := 0;     --错误标识
E_PUSH_ORDER_EXIST    constant integer := -21007;--推送订单不存在
E_PUSH_ORDER_COUNT    constant integer := -21008;--订单推送已经达到了推送上限
E_PUSH_COUNT          constant integer := 3;     --错误订单推送次数
/*
  预支付下订单接口
*/
function preCharge(v_payMentId            in varchar2,   --预支付ID
                   v_userName             in varchar2,   --预支付用户账号
                   n_cpId                 in number,     --合作商ID标识
                   n_gameId               in number,     --游戏ID
                   n_gatewayId            in number,     --区服ID
                   n_chargeMoney          in number,     --充值金额
                   n_chargeAmount         in number,     --充值元宝数 
                   v_platformName         in varchar2,   --合作商名称
                   v_attachCode           in varchar2,   --预支付附加码
                   v_expandInfo           in varchar2,   --拓展信息       
                   n_roleId               in number,      --角色ID
                   n_testState            in number,     --测试状态
                   n_productId            in varchar2    --产品标识
                  ) return integer;
/*
  通过预支付ID获取预支付信息
*/
function getPreInfo(v_payMentId           in varchar2,   --预支付ID
                    cur_result            out sys_refcursor
                   ) return integer;
/**
   通过游戏ID和产品ID获取Product配置信息
 */
 function getAppleProductInfo(n_gameId     in number,
                              v_productId  in varchar2,
                              cur_result   out sys_refcursor
                             ) return integer;
 /*
   记录APPSTORE 充值日志表
 */
function cpChargeCallBackForApple
                          (v_unionOrderId                 in varchar2,   --联运订单号
                           n_chargeDetailId               in number,     --蓝港充值订单号
                           v_payMentId                    in varchar2,   --预支付ID
                           v_userName                     in varchar2,   --预支付用户账号
                           n_cpId                         in number,     --合作伙伴ID
                           n_gameId                       in number,     --游戏ID
                           n_gatewayId                    in number,     --充值区服ID
                           n_chargeMoney                  in number,     --充值金额
                           n_chargeAmount                 in number,     --充值元宝数
                           v_platformName                 in varchar2,   --cp名称
                           v_attachCode                   in varchar2,   --附加码
                           v_expandInfo                   in varchar2,   --拓展信息
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
  记录CP通知充值成功信息
*/ 
function cpChargeCallBack(v_unionOrderId     in varchar2,   --联运订单号
                          n_chargeDetailId   in number,     --蓝港充值订单号
                          v_payMentId        in varchar2,   --预支付ID
                          v_userName         in varchar2,   --预支付用户账号
                          n_cpId             in number,     --合作伙伴ID
                          n_gameId           in number,     --游戏ID
                          n_gatewayId        in number,     --充值区服ID
                          n_chargeMoney      in number,     --充值金额
                          n_chargeAmount     in number,     --充值元宝数
                          v_platformName     in varchar2,   --cp名称
                          v_attachCode       in varchar2,   --附加码
                          v_expandInfo       in varchar2    --拓展信息
                         ) return integer;
/*
  推送到游戏服务器
*/
function pushChargeInfo(v_unionOrderId       in varchar2,   -- 联运订单号
                        n_chargeDetailId     in number,     -- 蓝港充值订单号
                        n_gameId             in number,      -- 游戏ID
                        v_serverIp           in varchar2,
                        v_clientIp           in varchar2
                       ) return integer;
/*
  开始记录开始推送信息
*/
procedure beginRecordPushInfo(v_unionOrderId   in varchar2,   --联运订单号
                             n_chargeDetailId in number,      --蓝港订单号
                             v_userName       in varchar2,    --推送用户账号
                             n_gameId         in number,      --推送的游戏ID
                             n_gatewayId      in number,      --推送的网关ID
                             n_chargeMoney    in number,      --推送金额
                             n_chargeAmount   in number,      --推送元宝数
                             v_attachCode     in varchar2,    --推送附加字段
                             v_expandInfo     in varchar2     --推送拓展信息                  
                            );
/*
  推送游戏服务器结束记录信息操作
*/
procedure endRecordPushInfo(v_unionOrderId     in varchar2,    --联运订单号
                           n_chargeDetailId   in number,       --蓝港订单号
                           n_result           in number        --推送返回结果
                           );
/*
  删除订单号重复的推送信息
*/
procedure delPushDupOrder(v_unionOrderId      in varchar2,   --联运订单号
                          n_chargeDetailId    in number      --蓝港订单号
                         );
end PKG_ECHARGING_CP_CHARGE;
/
create or replace package body PKG_ECHARGING_CP_CHARGE is
/*
  预支付下订单接口
*/
function preCharge(v_payMentId            in varchar2,   --预支付ID
                   v_userName             in varchar2,   --预支付用户账号
                   n_cpId                 in number,     --合作商ID标识
                   n_gameId               in number,     --游戏ID
                   n_gatewayId            in number,     --区服ID
                   n_chargeMoney          in number,     --充值金额
                   n_chargeAmount         in number,     --充值元宝数 
                   v_platformName         in varchar2,   --合作商名称
                   v_attachCode           in varchar2,   --预支付附加码
                   v_expandInfo           in varchar2,   --拓展信息 
                   n_roleId               in number,     --roleId
                   n_testState            in number,      --测试状态
                   n_productId            in varchar2     --产品ID
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
  通过预支付ID获取预支付信息
*/
function getPreInfo(v_payMentId           in varchar2,   --预支付ID
                    cur_result            out sys_refcursor
                   ) return integer is
    n_result       integer := pkg_linekong_util.S_SUCCESS;
    n_val          integer := pkg_linekong_util.S_SUCCESS;
 begin
   --判断该预支付信息是否存在
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
   通过游戏ID和产品ID获取Product配置信息
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
   记录APPSTORE 充值日志表
 */
function cpChargeCallBackForApple
                          (v_unionOrderId                 in varchar2,   --联运订单号
                           n_chargeDetailId               in number,     --蓝港充值订单号
                           v_payMentId                    in varchar2,   --预支付ID
                           v_userName                     in varchar2,   --预支付用户账号
                           n_cpId                         in number,     --合作伙伴ID
                           n_gameId                       in number,     --游戏ID
                           n_gatewayId                    in number,     --充值区服ID
                           n_chargeMoney                  in number,     --充值金额
                           n_chargeAmount                 in number,     --充值元宝数
                           v_platformName                 in varchar2,   --cp名称
                           v_attachCode                   in varchar2,   --附加码
                           v_expandInfo                   in varchar2,   --拓展信息
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
      --判断该订单是否已经支付
      select count(1) 
        into n_val 
        from log_cp_payment_order where union_order_id = v_unionOrderId and game_id = n_gameId;
        
      if(n_val >0)then
         return n_result;
      else  
         --判断该订单是否已经在苹果支付过
         select count(1) into n_val
           from log_appstore_charge
          where transaction_id = v_transaction_id;
          if(n_val >0)then
               n_result := E_CHARGE_DUPLICATE;--订单号重复
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
          --更新预支付订单信息中的支付状态以及联运订单信息
          update log_pre_payment
             set state = 1,
                 union_order_id = v_unionOrderId
           where payment_id = v_payMentId;
         
      end if;
   end;
   return n_result;
end;
/*
  记录CP通知充值成功信息
*/ 
function cpChargeCallBack(v_unionOrderId     in varchar2,   --联运订单号
                          n_chargeDetailId   in number,     --蓝港充值订单号
                          v_payMentId        in varchar2,   --预支付ID
                          v_userName         in varchar2,   --预支付用户账号
                          n_cpId             in number,     --合作伙伴ID
                          n_gameId           in number,     --游戏ID
                          n_gatewayId        in number,     --充值区服ID
                          n_chargeMoney      in number,     --充值金额
                          n_chargeAmount     in number,     --充值元宝数
                          v_platformName     in varchar2,   --cp名称
                          v_attachCode       in varchar2,   --附加码
                          v_expandInfo       in varchar2    --拓展信息
                         ) return integer is
    n_result         integer := pkg_linekong_util.S_SUCCESS;
    n_val            integer := pkg_linekong_util.S_SUCCESS;
begin
   begin
      --判断该订单是否已经支付
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
        --更新预支付订单信息中的支付状态以及联运订单信息
        update log_pre_payment
           set state = 1,
               union_order_id = v_unionOrderId
         where payment_id = v_payMentId;
         
      end if;
   end;
   return n_result;
end;
/*
  推送到游戏服务器
*/
function pushChargeInfo(v_unionOrderId       in varchar2,   -- 联运订单号
                        n_chargeDetailId     in number,     -- 蓝港充值订单号
                        n_gameId             in number,     -- 游戏ID
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
   --查询联运充值订单号
   select user_name,gateway_id,charge_money,charge_amount,attach_code,expand_info,payment_id into
         v_userName,n_gatewayId,n_chargeMoney,n_chargeAmount,v_attachCode,v_expandInfo,v_paymentId
    from log_cp_payment_order
   where union_order_id = v_unionOrderId and game_id = n_gameId;
  exception when NO_DATA_FOUND then
    n_result := E_PUSH_ORDER_EXIST;
    goto ExitError;
  end;
  --判断订单是否已经达到了错误推送次数
  select count(1) into n_val
    from log_cp_order_push
   where charge_detail_id = n_chargeDetailId
     and union_order_id = v_unionOrderId
     and push_count = E_PUSH_COUNT;
  if(n_val > 0)then
     n_result := E_PUSH_ORDER_COUNT;
     goto ExitError;
  end if;
  --记录推送信息
  beginRecordPushInfo(v_unionOrderId,n_chargeDetailId,v_userName,n_gameId,n_gatewayId,n_chargeMoney,n_chargeAmount,v_attachCode,v_expandInfo);
  --执行信息
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
  if(n_result =  E_CHARGE_DUPLICATE)then -- 订单号重复
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
  开始记录开始推送信息
*/
procedure beginRecordPushInfo(v_unionOrderId   in varchar2,    --联运订单号
                             n_chargeDetailId in number,      --蓝港订单号
                             v_userName       in varchar2,    --推送用户账号
                             n_gameId         in number,      --推送的游戏ID
                             n_gatewayId      in number,      --推送的网关ID
                             n_chargeMoney    in number,      --推送金额
                             n_chargeAmount   in number,      --推送元宝数
                             v_attachCode     in varchar2,    --推送附加字段
                             v_expandInfo     in varchar2     --推送拓展信息                  
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
  推送游戏服务器结束记录信息操作
*/
procedure endRecordPushInfo(v_unionOrderId     in varchar2,     --联运订单号
                           n_chargeDetailId   in number,       --蓝港订单号
                           n_result           in number        --推送返回结果
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
  删除订单号重复的推送信息
*/
procedure delPushDupOrder(v_unionOrderId      in varchar2,   --联运订单号
                          n_chargeDetailId    in number      --蓝港订单号
                         ) is
   PRAGMA AUTONOMOUS_TRANSACTION;
begin
   delete from log_cp_order_push where charge_detail_id = n_chargeDetailId and union_order_id = v_unionOrderId;
   commit;
end;
end PKG_ECHARGING_CP_CHARGE;
/

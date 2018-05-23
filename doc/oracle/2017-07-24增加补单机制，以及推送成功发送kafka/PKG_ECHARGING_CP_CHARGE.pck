create or replace package PKG_ECHARGING_CP_CHARGE is
S_ERATING_PKG_NAME    constant varchar2(50) := 'pkg_game_';
E_CHARGE_ORDER_EXIST  constant integer := -1490;
E_CHARGE_DUPLICATE    constant integer := -1472; --订单号重复
E_ERROR               constant integer := 0;     --错误标识
E_PUSH_ORDER_EXIST    constant integer := -21007;--推送订单不存在
E_PUSH_ORDER_COUNT    constant integer := -21008;--订单推送已经达到了推送上限
E_PUSH_COUNT          constant integer := 3;     --错误订单推送次数
/*E_CHARGE_DUPLICATE
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
                   n_productId            in varchar2,   --产品标识
                   n_var                  in varchar2,   --版本号（与SDK 商议后决定，以区分不同版本）
                   n_cpSignType           in varchar2,   --渠道预支付签名类型
                   n_productDesc          in varchar2,   --产品描述
                   n_productName          in varchar2    --产品Name     
                  ) return integer;
/*
  通过预支付ID获取预支付信息
*/
function getPreInfo(v_payMentId           in varchar2,   --预支付ID
                    cur_result            out sys_refcursor
                   ) return integer;
/*
   获取符合再次推送的，推送记录
   推送次数小于3，错误码不为0或者推送时间已经超过2小时
 */
 function getPushInfo(cur_result    out sys_refcursor)return integer ;
 /**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差超过两小时，push表中无数据
	 * @return
	 */
 function getOrderInfo(cur_result   out sys_refcursor)return integer; 
 /**
 获取log_charge_common表中数据
*/
 function getLogChargeCommon(v_chargeDetailId    in number,
                             cur_result    out sys_refcursor) return integer;               
 /*
  通过蓝港订单号获取预支付信息
*/
function getPreInfoByChargeDetailId(   v_unionOrderId      in varchar2,   --蓝港订单ID
                                       cur_result            out sys_refcursor
                                     ) return integer;                
/*
  获取充值订单号状态
*/
function getChargeOrderStatus(v_payMentId           in varchar2   --预支付ID
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
 * 推送前信息检查
*/
function pushMQChargeInfoCheck(v_unionOrderId       in varchar2,   -- 联运订单号
                               n_chargeDetailId     in number,     -- 蓝港充值订单号
                               n_gameId             in number     -- 游戏ID
                              ) return integer;
/*
 * MQ推送后信息记录
 */
function pushMQChargeInfoRecord (v_unionOrderId       in varchar2,   -- 联运订单号
                                 n_chargeDetailId     in number,     -- 蓝港充值订单号
                                 n_serverIp           in varchar2,   -- 服务器IP
                                 n_clientIp           in varchar2,   -- 请求IP
                                 n_userId             in number,     --userId
                                 n_result             in number      -- MQ推送结果
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
/**
   插入活动推送日志
*/
function recordActMQInfo(n_gameId      in number,  --游戏ID
                          v_userId      in varchar2,--通行证ID
                          v_userName      in varchar2,--通行证名称
                          n_gatewayId      in number,  --网关ID
                          n_chargeChannelId  in number,  --充值渠道ID
                          n_discount      in number,  --充值渠道折扣
                          n_chargeSubjectId  in number,  --充值游戏的货币类型=
                          n_chargeMoney    in number,  --充值货币数=
                          n_chargeAmount    in number,  --充值元宝数
                          v_chargeOrderCode  in varchar2,--联运订单号
                          n_chargeDetailId  in number,  --充值订单号
                          n_chargeType    in number,  --充值类型1直充
                          n_moneyType      in number,  --货币类型 1 人民币
                          v_attachCode    in varchar2,--透传字段信息
                          n_roleId      in number,  --角色ID
                          v_chargeTime    in varchar2,--下单时间 (时间戳，秒级)
                          v_serverIp      in varchar2,--服务器IP
                          v_clientIp      in varchar2,--请求IP
                          v_pushResult    in varchar2 --推送结果
                         )return integer; 
/**
   查询推送活动MQ是否重复
*/
function checkActMQInfo(n_gameId      in number,  --游戏ID
                          v_chargeOrderCode  in varchar2,--联运订单号
                          n_chargeDetailId  in number  --充值订单号
                         )return integer;                                                
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
                   n_productId            in varchar2,     --产品ID
                   n_var                  in varchar2,   --版本号（与SDK 商议后决定，以区分不同版本）
                   n_cpSignType           in varchar2,   --渠道预支付签名类型
                   n_productDesc          in varchar2,   --产品描述
                   n_productName          in varchar2    --产品Name   
                  ) return integer is
                  
     n_result integer := pkg_linekong_util.S_SUCCESS;
     n_count  integer := 0;
  begin
    begin
      --检查是否有重复的订单号
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
       select payment_id,user_name,cp_id,game_id,gateway_id,charge_money,charge_amount,product_id,attach_code,expand_info,payment_time,server_ip,request_ip from log_pre_payment where payment_id = v_payMentId;
   end if;
   <<ExitError>>
   return n_result;
 end;
 /*
   获取符合再次推送的，推送记录
   推送次数小于3，错误码不为0或者推送时间已经超过2小时
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
 
 function getOrderInfo(cur_result    out sys_refcursor)return integer is
   n_result       integer := pkg_linekong_util.S_SUCCESS;
 begin
   open cur_result for
   select t1.charge_detail_id,t1.union_order_id,t1.game_id,t1.gateway_id,t1.payment_id,t1.cp_id,t1.user_name,t1.charge_money,t1.charge_amount,t1.platform_name,t1.attach_code,t1.expand_info,t1.state
     from log_cp_payment_order t1
     left join log_cp_order_push t2 on t1.charge_detail_id = t2.charge_detail_id
     where t1.state=0 and t1.cp_callback_time < sysdate-2/24 and t2.union_order_id is null;
   return n_result;
  end;
/**
 获取log_charge_common表中数据
*/
 function getLogChargeCommon(v_chargeDetailId    in number,
                             cur_result    out sys_refcursor) return integer is
   n_result       integer := pkg_linekong_util.S_SUCCESS;
 begin
   open cur_result for
   select lcc.charge_detail_id ,lcc.passport_id,lcc.game_id,lcc.charge_gateway_id,lcc.charge_subject_id,
          lcc.charge_channel_id,lcc.charge_amount,lcc.charge_time,lcc.charge_money,lcc.charge_realmoney,
          lcc.client_ip,lcc.server_ip,lcc.discount,lcc.deal_state,lcc.card_num,lcc.uncharge_time,lcc.province,lcc.city 
   from log_charge_common lcc
   where lcc.charge_detail_id= v_chargeDetailId;		
   return n_result;
 end; 
 /*
  通过蓝港订单号获取预支付信息
*/
function getPreInfoByChargeDetailId(   v_unionOrderId      in varchar2,   --蓝港订单ID
                                       cur_result            out sys_refcursor
                                     ) return integer is
    n_result       integer := pkg_linekong_util.S_SUCCESS;
    n_val          integer := pkg_linekong_util.S_SUCCESS;
 begin
   --判断该预支付信息是否存在
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
  获取充值订单号状态
*/
function getChargeOrderStatus(v_payMentId           in varchar2   --预支付ID
                   ) return integer is
     n_val          integer := pkg_linekong_util.S_SUCCESS;
begin
   select count(1) into n_val from log_pre_payment where payment_id = v_payMentId and state = 1;
   return n_val;
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
         return E_CHARGE_DUPLICATE;--订单号重复
      else  
         --判断该订单是否已经在苹果支付过
         select count(1) into n_val
           from log_appstore_charge
          where transaction_id = v_transaction_id;
          if(n_val >0)then
               n_result := E_CHARGE_DUPLICATE;--订单号重复
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
             
              --更新预支付订单信息中的支付状态以及联运订单信息
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
         return  E_CHARGE_DUPLICATE;--订单号重复;
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
 * MQ推送前信息检查
*/
function pushMQChargeInfoCheck(v_unionOrderId       in varchar2,   -- 联运订单号
                               n_chargeDetailId     in number,     -- 蓝港充值订单号
                               n_gameId             in number     -- 游戏ID
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
   
   <<ExitError>>
  return n_result;
end;

/*
 * MQ推送后信息记录
 */
function pushMQChargeInfoRecord (v_unionOrderId       in varchar2,   -- 联运订单号
                                 n_chargeDetailId     in number,     -- 蓝港充值订单号
                                 n_serverIp           in varchar2,   -- 服务器IP
                                 n_clientIp           in varchar2,   -- 请求IP
                                 n_userId             in number,     --userId
                                 n_result             in number      -- MQ推送结果
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
    --查询联运充值订单号
    select gateway_id,charge_money,charge_amount,payment_id,game_id into
           n_gatewayId,n_chargeMoney,n_chargeAmount,v_paymentId,v_gameId
      from log_cp_payment_order
     where union_order_id = v_unionOrderId and charge_detail_id = n_chargeDetailId;
    exception when NO_DATA_FOUND then
      v_result := E_PUSH_ORDER_EXIST;
      goto ExitError;
    end;
  
  if(n_result =  E_CHARGE_DUPLICATE)then -- 订单号重复
     delPushDupOrder(v_unionOrderId,n_chargeDetailId);
     --检查是否在log_charge_common表里存在n_chargeDetailId
     select count(1) into v_chackResult from log_charge_common where charge_detail_id = n_chargeDetailId;
     if(v_chackResult <= 0) then
         --调用存储过程，加入log_charge_common表数据
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
         --调用存储过程，加入log_charge_common表数据
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
/**
   插入活动推送日志
*/
function recordActMQInfo(n_gameId      in number,  --游戏ID
                          v_userId      in varchar2,--通行证ID
                          v_userName      in varchar2,--通行证名称
                          n_gatewayId      in number,  --网关ID
                          n_chargeChannelId  in number,  --充值渠道ID
                          n_discount      in number,  --充值渠道折扣
                          n_chargeSubjectId  in number,  --充值游戏的货币类型=
                          n_chargeMoney    in number,  --充值货币数=
                          n_chargeAmount    in number,  --充值元宝数
                          v_chargeOrderCode  in varchar2,--联运订单号
                          n_chargeDetailId  in number,  --充值订单号
                          n_chargeType    in number,  --充值类型1直充
                          n_moneyType      in number,  --货币类型 1 人民币
                          v_attachCode    in varchar2,--透传字段信息
                          n_roleId      in number,  --角色ID
                          v_chargeTime    in varchar2,--下单时间 (时间戳，秒级)
                          v_serverIp      in varchar2,--服务器IP
                          v_clientIp      in varchar2,--请求IP
                          v_pushResult    in varchar2 --推送结果
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
   查询推送活动MQ是否重复
*/
function checkActMQInfo(n_gameId      in number,  --游戏ID
                          v_chargeOrderCode  in varchar2,--联运订单号
                          n_chargeDetailId  in number  --充值订单号
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

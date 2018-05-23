package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class CaohuaResBean {
    private int code;   //200:成功（重复回调也返回这个）   201：参数错误   202：签名校验失败   203：其它错误
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CaohuaResBean() { super(); }


    public CaohuaResBean(int resCode) {
        switch (resCode){
            case Constant.CHARGE_SUCCESS :
            case Constant.ERROR_ORDER_DUPLICATE : this.code = 200; this.msg = "成功" ;
                break;
            case Constant.ERROR_PARAM_VALIDATE : this.code = 201 ; this.msg = "参数错误";
                break;
            case Constant.ERROR_SIGN : this.code = 202 ; this.msg = "签名校验失败" ;
                break;
            case Constant.ERROR_CHARGE_MONEY : this.code = 203 ; this.msg = "订单金额不符";
                break;
            case Constant.ERROR_SYSTEM : this.code = 203 ; this.msg = "程序出错"; this.data="";
                break;
            case Constant.ERROR_SERVER_IP : this.code = 203 ; this.msg = "此IP未加入到IP白名单中";
                break;
            default:  this.code = 203 ; this.msg = "其它错误";
                break;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("code=").append(code);
        sb.append(",msg=").append(msg);
        sb.append(",data=").append(data);
        return sb.toString();
    }
}

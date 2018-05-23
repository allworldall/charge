package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

//9377回调响应
public class NTSSResBean {

    private int state;

    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("state=").append(state);
        sb.append(",msg=").append(msg);
        return sb.toString();
    }

    public NTSSResBean createRespJson(int result) {
        switch (result) {
            case Constant.SUCCESS:
            case Constant.ERROR_ORDER_DUPLICATE: this.state = 1;this.msg = "success"; break;
            case Constant.ERROR_SIGN : this.state = Constant.ERROR_SIGN; this.msg = "error sign"; break;
            case Constant.ERROR_SERVER_IP : this.state = Constant.ERROR_SERVER_IP; this.msg = "error white ip"; break;
            default: this.state = 0; this.msg = "other error"; break;
        }
        return this;
    }
}

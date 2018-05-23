package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class SevenKResBean {
    private int Result;

    public SevenKResBean(int result) {
        Result = result;
    }

    public SevenKResBean() {
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Result=").append(Result);
        return sb.toString();
    }

    public SevenKResBean buildBean(int resCode) {
        switch(resCode){
            case Constant.SUCCESS: this.setResult(1); break;
            case Constant.ERROR_ORDER_DUPLICATE : this.setResult(4); break;
            case Constant.ERROR_SERVER_IP : this.setResult(5); break;
            case Constant.ERROR_SIGN: this.setResult(6); break;
            case Constant.ERROR_PARAM_VALIDATE : this.setResult(7); break;
            default: this.setResult(8); break;
        }
        return this;
    }


}

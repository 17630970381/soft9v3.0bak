package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
//public class Result {
//
//    public static final String CODE_SUCCESS = "200";
//    public static final String CODE_AUTH_ERROR = "401";
//    public static final String CODE_SYS_ERROR = "500";
//
//    private String code; // 状态码vate Integer code; // 状态码
//    private String msg; // 返回信息
//    private Object data; // 返回数据
//
//    public static Result success() {
//        return new Result(CODE_SUCCESS, "请求成功", null);
//    }
//    public static Result success(Object data) {
//        return new Result(CODE_SUCCESS, "请求成功", data);
//    }
//
//    public static Result error(String msg) {
//        return new Result(CODE_SYS_ERROR, msg, null);
//    }
//
//    public static Result error(String code, String msg) {
//        return new Result(code, msg, null);
//    }
//
//    public static Result error() {
//        return new Result(CODE_SYS_ERROR, "系统错误", null);
//    }
//}

public class Result<T> {
    private Object data;
    private String msg;
    private int code;

    public Result() {
    }

    public Result(Object data, int code) {
        this.data = data;
        this.code=code;
    }

    // getter setter 省略，构造方法省略
    // 操作成功返回数据
    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result success(int code , String msg) {
        return success(code, msg, null);
    }

    public static Result success(String msg, Object data) {
        return success(200,msg,data);
    }

    public static Result success(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }



    // 操作异常返回
    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400,msg,null);
    }

    public static Result fail(int code, String msg) {
        return fail(code,msg,"null");
    }

    public static Result fail(String msg, Object data) {
        return fail(400,msg,data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

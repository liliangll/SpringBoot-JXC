package com.bdqn.util;


/**
 * Created by Eric on 2017-02-17.
 */
public class JsonResponse<T>  {

    public static final Integer ERR = 5000;
    public static final JsonResponse PARAM_MISSING = JsonResponse.notOk(4000, "参数缺失");
    public static final JsonResponse AUTH_FAIL = JsonResponse.notOk(4001, "权限验证失败");
    public static final JsonResponse NO_PERMISSION = JsonResponse.notOk(4002, "无访问权限");
    public static final JsonResponse TOKEN_TIMEOUT = JsonResponse.notOk(4003, "Token 失效");
    public static final JsonResponse SERVER_ERR = JsonResponse.notOk(ERR, "服务器异常");
    private static final long serialVersionUID = 3580043264590808190L;
    private static final Integer OK = 200;
    private static final Integer REDIRECT = 302;
    private Integer result_status;

    private Long total;

    private T err;

    private T result_data;

    public static JsonResponse ok() {
        JsonResponse r = new JsonResponse();
        r.result_status = OK;
        return r;
    }

    public static JsonResponse ok(Object result_data) {
        JsonResponse r = new JsonResponse();
        r.result_status = OK;
        r.result_data = result_data;
        return r;
    }

    public static JsonResponse ok(Long total, Object result_data) {
        JsonResponse r = new JsonResponse();
        r.result_status = OK;
        r.total = total;
        r.result_data = result_data;
        return r;
    }

    public static JsonResponse notOk(Object err) {
        JsonResponse r = new JsonResponse();
        r.result_status = ERR;
        r.err = err;
        return r;
    }

    public static JsonResponse notOk(Integer result_status, Object err) {
        JsonResponse r = new JsonResponse();
        r.result_status = result_status;
        r.err = err;
        return r;
    }

    public static JsonResponse redirect(String url) {
        JsonResponse r = new JsonResponse();
        r.result_status = REDIRECT;
        r.result_data = url;
        return r;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getResult_status() {
        return result_status;
    }

    public void setResult_status(Integer result_status) {
        this.result_status = result_status;
    }

    public Object getErr() {
        return err;
    }

    public void setErr(T err) {
        this.err = err;
    }

    public T getResult_data() {
        return result_data;
    }

    public void setResult_data(T result_data) {
        this.result_data = result_data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "result_status=" + result_status +
                ", err=" + err +
                ", result_data=" + result_data +
                '}';
    }

}

package run.autoium.common.DataCode.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 前后端交互的json对象，用来存储前后端交互的数据。对象R中至少包含success
 * 标志位、code状态码、message说明信息、data数据。
 */
@Data
public class R {

    /**
     * 数据传输是否成功
     */
    private Boolean success;

    /**
     * 返回的状态码
     */
    private Integer code;

    /**
     * 返回的数据的说明
     */
    private String message;

    /**
     * 返回的数据用map存放
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 私有的构造方法，可以防止外部new R()
     */
    private R() {

    }

    /**
     * 响应成功，但不包含数据
     *
     * @return r
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    /**
     * 响应失败，但不包含数据
     *
     * @return r
     */
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**
     * 设置标志位
     *
     * @param success 标志位
     * @return r 链式编程
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置状态码
     *
     * @param code 状态码
     * @return r 链式编程
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置响应的说明信息
     *
     * @param message 说明信息
     * @return r 链式编程
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置响应携带的数据，以map存放
     *
     * @param data 响应数据
     * @return r 链式编程
     */
    public R data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    /**
     * 设置响应携带的数据，以键值对存放
     *
     * @param key   键
     * @param value 值
     * @return r 链式编程
     */
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}

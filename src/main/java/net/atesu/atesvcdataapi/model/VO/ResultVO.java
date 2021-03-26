package net.atesu.atesvcdataapi.model.VO;

public class ResultVO<T> {
    private String code;
    private String msg;
    private T data;

    public static String CODE_SUCCESS = "1";
    public static String CODE_SUCCESS_MSG = "success";
    public static String CODE_FAILED = "0";
    public static String CODE_FAILED_MSG = "failed";

    /**
     * 构造成功的返回对象
     * @param data 返回的数据
     * @param <DataType> 返回的数据类型
     * @return
     */
    public static<DataType> ResultVO<DataType> success(DataType data) {
        return new ResultVO(CODE_SUCCESS,CODE_SUCCESS_MSG, data);
    }

    /**
     * 构造失败的返回对象
     * @param data
     * @param <DataType>
     * @return
     */
    public static<DataType> ResultVO<DataType> failed(DataType data) {
        return new ResultVO(CODE_FAILED,CODE_FAILED_MSG, data);
    }

    /**
     * 构造失败的返回对象
     * @param failedMsg
     * @param data
     * @param <DataType>
     * @return
     */
    public static<DataType> ResultVO<DataType> failed(String failedMsg, DataType data) {
        return new ResultVO(CODE_FAILED,failedMsg, data);
    }

    public static<DataType> ResultVO<DataType> build(String code, String msg, DataType data) {
        return new ResultVO(code,msg, data);
    }


    private ResultVO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

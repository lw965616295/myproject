package com.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private T datas;
    private Integer resp_code;
    private String resp_msg;

    public static <T> Result<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> Result<T> succeedWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith(null, CodeEnum.FAIL.getCode(), msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.FAIL.getCode(), msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> error(String msg) {
        return errorWith(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(T model, String msg) {
        return errorWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> errorWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> warn(String msg) {
        return warnWith(null, CodeEnum.WARN.getCode(), msg);
    }

    public static <T> Result<T> warn(T model, String msg) {
        return warnWith(model, CodeEnum.WARN.getCode(), msg);
    }

    public static <T> Result<T> warnWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }


    public enum CodeEnum {
        SUCCESS(0),
        ERROR(-9),
        FAIL(1),
        WARN(-1);

        private Integer code;

        CodeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public Boolean isSuccess() {
        return CodeEnum.SUCCESS.getCode().equals(this.resp_code);
    }

    public Boolean isFailed() {
        return CodeEnum.FAIL.getCode().equals(this.resp_code);
    }

    public Boolean isError() {
        return CodeEnum.ERROR.getCode().equals(this.resp_code);
    }

    public Boolean isWarn() {
        return CodeEnum.WARN.getCode().equals(this.resp_code);
    }


}
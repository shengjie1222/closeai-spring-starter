package com.ethereal.confidant.ai.pojo;

import lombok.Data;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Data
public class ResponseVo<T> {

    private int code = 200;

    private String message;

    private T data;


    public static <T> ResponseVo<T> ok(T data){
        ResponseVo<T> response = new ResponseVo<>();
        response.setData(data);
        return response;
    }
}

package com.example.administratorsidesoftware.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A unified, encapsulated back-end interface,
 * it can return operation result, some message, and some
 * data to front-end
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private State state;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(State.SUCCESS,"",null);
    }
    public static Result success(Object data){
        return new Result(State.SUCCESS,"",data);
    }
    public static Result error(String msg){
        return new Result(State.USER_ERROR,msg,null);
    }
    public static Result error(){
        return new Result(State.BUSINESS_ERROR,"",null);
    }
}

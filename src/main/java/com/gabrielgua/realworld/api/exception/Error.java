package com.gabrielgua.realworld.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonTypeName("error")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Error {

    private int status;
    private String message;
//    private List<Error.Field> body;
    private Map<String, Object> errors;
//
//    @Getter
//    @Setter
//    @Builder
//    public static class Field {
//        public String message;
//    }
}

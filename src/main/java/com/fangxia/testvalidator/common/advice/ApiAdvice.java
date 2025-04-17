package com.fangxia.testvalidator.common.advice;

import com.fangxia.testvalidator.common.model.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice implements ResponseBodyAdvice<Object> {

    private final HttpServletResponse response;

    @Override
    public boolean supports(MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull org.springframework.http.server.ServerHttpRequest request,
                                  @NonNull org.springframework.http.server.ServerHttpResponse serverResponse) {

        if (body instanceof ApiResponse<?> fxResponse) {
            response.setStatus(fxResponse.toHttpStatus());
            if(fxResponse.isSuccess()) {
                return fxResponse.getData();
            }
            return fxResponse.getMsg();
        }

        return body;
    }

}

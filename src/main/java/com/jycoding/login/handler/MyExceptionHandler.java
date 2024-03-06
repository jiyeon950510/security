package com.jycoding.login.handler;

import com.jycoding.login.handler.ex.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomException.class)
 public String BasicException(Exception e){
     StringBuilder sb = new StringBuilder();
     sb.append("<script>");
     sb.append("alert('" + e.getMessage() + "');");
     sb.append("history.back();");
     sb.append("</script>");

     return sb.toString();
 }

}

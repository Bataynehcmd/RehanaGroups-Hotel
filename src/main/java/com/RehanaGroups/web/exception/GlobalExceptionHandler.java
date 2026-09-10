package com.RehanaGroups.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String runTimeException(RuntimeException runtimeException, Model model) {
        model.addAttribute("message", runtimeException.getMessage());
        return "error";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationException(
            MethodArgumentNotValidException exception,
            Model model) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        model.addAttribute("message", message);

        return "error";
    }

}

package codesquad.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebUtil {
    @ExceptionHandler(NullPointerException.class)
    public String nullPoint() {
        return "/user/login";
    }
}

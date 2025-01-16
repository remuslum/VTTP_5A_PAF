package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception.InvalidDateException;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception.InvalidValueException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({InvalidDateException.class, InvalidValueException.class})
    public ModelAndView handleInvalidValue(HttpServletRequest request, RuntimeException ex, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", ex.getMessage());
        mav.setStatus(HttpStatusCode.valueOf(response.getStatus()));
        return mav;
    }
}

package com.deador.mvcapp.exception.handler;

import com.deador.mvcapp.exception.AlreadyExistException;
import com.deador.mvcapp.exception.NotExistException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(NotExistException.class)
    public ModelAndView handleNotExistException(HttpServletRequest request, NotExistException exception, Model model) {
        return getModelAndView(request, exception, model);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ModelAndView handleAlreadyExistException(HttpServletRequest request, NotExistException exception, Model model) {
        return getModelAndView(request, exception, model);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, NotExistException exception, Model model) {
        String requestURL = request.getRequestURL().toString();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());
        model.addAttribute("errorMessage", exception.getMessage());

        if (requestURL.contains("/categories")) {
            modelAndView.setViewName("categories");
        } else if (requestURL.contains("/products")) {
            modelAndView.setViewName("products");
        } else if (requestURL.contains("/orders")) {
            modelAndView.setViewName("orders");
        } else {
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}

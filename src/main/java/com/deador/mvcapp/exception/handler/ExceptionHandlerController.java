package com.deador.mvcapp.exception.handler;

import com.deador.mvcapp.exception.AlreadyExistException;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.exception.UserNotActivatedException;
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
    public ModelAndView handleAlreadyExistException(HttpServletRequest request, AlreadyExistException exception, Model model) {
        return getModelAndView(request, exception, model);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ModelAndView handleUserAuthenticationException(HttpServletRequest request, UserAuthenticationException exception, Model model) {
        return getModelAndView(request, exception, model);
    }

    // FIXME: 21.03.2023 don't work handle
    @ExceptionHandler(UserNotActivatedException.class)
    public ModelAndView handleUserNotActivatedException(HttpServletRequest request, UserNotActivatedException exception, Model model) {
        return getModelAndView(request, exception, model);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, Exception exception, Model model) {
        String requestURL = request.getRequestURL().toString();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());
        model.addAttribute("errorMessage", exception.getMessage());

        if (requestURL.contains("/register")) {
            modelAndView.setViewName("register");
        } else if (requestURL.contains("/login")) {
            modelAndView.setViewName("login");
        } else if (requestURL.contains("/addToCart") || requestURL.contains("buyNow")) {
            modelAndView.setViewName("login");
        } else if (requestURL.contains("/personalCabinet")) {
            modelAndView.setViewName("login");
        } else if (requestURL.contains("/cart")) {
            modelAndView.setViewName("login");
        } else if (requestURL.contains("/categories")) {
            modelAndView.setViewName("categories");
        } else if (requestURL.contains("/products")) {
            modelAndView.setViewName("products");
        } else if (requestURL.contains("/orders") || requestURL.contains("/order")) {
            modelAndView.setViewName("orders");
        } else {
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}

package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldServlet {
  private static final long serialVersionUID = 1L;

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public ModelAndView hello() {
    var modelAndView = new ModelAndView();
    modelAndView.setViewName("views/403");
    return modelAndView;
  }
}

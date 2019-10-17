package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldServlet {
  private static final long serialVersionUID = 1L;

  @GetMapping("hello")
  public ModelAndView hello() {
    var modelAndView = new ModelAndView();
    return null;
  }


}

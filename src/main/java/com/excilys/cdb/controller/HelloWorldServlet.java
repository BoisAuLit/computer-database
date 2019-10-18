package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldServlet {
  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public ModelAndView hello() {
    var modelAndView = new ModelAndView();
    modelAndView.setViewName("HelloWorld");
    return modelAndView;
  }

  @GetMapping("/sample")
  public String showForm() {
    return "sample";
  }
}

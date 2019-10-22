package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.services.ComputerService;

@Controller
public class ComputerController {

  @Autowired
  private ComputerService computerService;

  @CrossOrigin(origins = "http://localhost:4201")
  @GetMapping("yassine")
  public @ResponseBody List<ComputerDto> getCompters() {
    return computerService.getComputerDtos();
  }
}

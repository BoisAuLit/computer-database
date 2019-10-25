package com.excilys.cdb.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping("add-computer")
public class AddComputerController {
  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyService companyService;

  @GetMapping
  public ModelAndView getAddComputerPage() {
    return new ModelAndView("addComputer", "companyDtos", companyService.getCompanyDtos());
  }

  @PostMapping
  public void addComputer(@RequestBody ComputerDto computerDto,
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    int rowsAffected = computerService.saveComputer(computerDto);
    if (rowsAffected == 1) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }
}

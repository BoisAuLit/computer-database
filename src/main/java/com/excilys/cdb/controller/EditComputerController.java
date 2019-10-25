package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping("edit-computer")
public class EditComputerController {

  @Autowired
  private ComputerService computerService;

  @GetMapping
  public ModelAndView getEditComputerPage(@RequestParam long id) {
    ModelAndView modelAndView = new ModelAndView();

    Optional<ComputerDto> computerDtoOpt = computerService.getComputerDtoById(id);

    modelAndView.addObject("computerDto", computerDtoOpt.get());

    modelAndView.setViewName("editComputer");
    return modelAndView;
  }

  @PostMapping
  public void editComputer(@RequestBody ComputerDto computerDto,
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    int rowsAffected = computerService.updateComputer(computerDto);
    if (rowsAffected == 1) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }


}

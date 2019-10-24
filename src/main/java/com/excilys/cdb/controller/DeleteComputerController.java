package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping("delete-computer")
public class DeleteComputerController {

  @Autowired
  private ComputerService computerService;

  @PostMapping
  public void deleteComputer(@RequestBody List<String> ids,
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    boolean success = computerService.batchDeleteComputer(ids);

    if (success) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }

}

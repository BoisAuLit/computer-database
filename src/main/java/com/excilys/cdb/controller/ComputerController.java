package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.model.ComputerModel;
import com.excilys.cdb.services.ComputerServiceTest;

@RestController
@RequestMapping("api/v1/computer")
public class ComputerController {

  @Autowired
  ComputerServiceTest computerServiceTest;

  @GetMapping("get-all")
  public List<ComputerModel> getAllComputers() {
    return computerServiceTest.getAllComputers();
  }

  @GetMapping("get-by-id/{id}")
  public ComputerModel getById(@PathVariable Long id) {
    return computerServiceTest.getComputerById(id).get();
  }
}

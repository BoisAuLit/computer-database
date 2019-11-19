package com.excilys.cdb.webapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.persistence.model.ComputerModel;
import com.excilys.cdb.service.ComputerServiceTest;

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

  @PostMapping("create")
  public ComputerModel createComputer(@RequestBody ComputerModel computerModel) {
    return computerServiceTest.createComputer(computerModel);
  }

  @GetMapping("find-by-name/{name}")
  public List<ComputerModel> findComputersByName(@PathVariable String name) {
    return computerServiceTest.findComputersByName(name);
  }

  @PostMapping("update")
  public ComputerModel updateComputer(@RequestBody ComputerModel computerModel) {
    return computerServiceTest.updateComputer(computerModel);
  }
}

package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("api/v1/computers")
public class ComputerController {

  @Autowired
  ComputerService computerService;

  @GetMapping("get-all")
  public List<Computer> getAllComputers() {
    return computerService.getAllComputers();
  }

  @GetMapping("get-by-id/{id}")
  public Computer getById(@PathVariable Long id) {
    return computerService.getComputerById(id).get();
  }

  @PostMapping("create")
  public Computer createComputer(@RequestBody Computer computer) {
    return computerService.createComputer(computer);
  }

  @GetMapping("find-by-name/{name}")
  public List<Computer> findComputersByName(@PathVariable String name) {
    return computerService.findComputersByName(name);
  }

  @PostMapping("update")
  public Computer updateComputer(@RequestBody Computer computer) {
    return computerService.updateComputer(computer);
  }
}

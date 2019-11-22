package com.excilys.cdb.webapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.persistence.model.Computer;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("api/v1/computers")
public class ComputerController {

  @Autowired
  ComputerService computerService;

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("get-all")
  public List<Computer> getAllComputers() {
    return computerService.getAllComputers();
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("get-by-id/{id}")
  public Computer getById(@PathVariable Long id) {
    return computerService.getComputerById(id).get();
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("create")
  public Computer createComputer(@RequestBody Computer computer) {
    return computerService.createComputer(computer);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("find-by-name/{name}")
  public List<Computer> findComputersByName(@PathVariable String name) {
    return computerService.findComputersByName(name);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("update")
  public Computer updateComputer(@RequestBody Computer computer) {
    return computerService.updateComputer(computer);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @DeleteMapping("delete/{id}")
  public void deleteComputerById(@PathVariable Long id) {
    computerService.deleteComputerById(id);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("delete-all")
  public void deleteComputerByIds(@RequestBody List<Long> ids) {
    computerService.deleteComputerByIds(ids);
  }
}

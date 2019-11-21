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
import com.excilys.cdb.persistence.model.ComputerModel;
import com.excilys.cdb.service.ComputerServiceTest;

@RestController
@RequestMapping("api/v1/computer")
public class ComputerController {

  @Autowired
  ComputerServiceTest computerServiceTest;

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("get-all")
  public List<ComputerModel> getAllComputers() {
    return computerServiceTest.getAllComputers();
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("get-by-id/{id}")
  public ComputerModel getById(@PathVariable Long id) {
    return computerServiceTest.getComputerById(id).get();
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("create")
  public ComputerModel createComputer(@RequestBody ComputerModel computerModel) {
    return computerServiceTest.createComputer(computerModel);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("find-by-name/{name}")
  public List<ComputerModel> findComputersByName(@PathVariable String name) {
    return computerServiceTest.findComputersByName(name);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("update")
  public ComputerModel updateComputer(@RequestBody ComputerModel computerModel) {
    return computerServiceTest.updateComputer(computerModel);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @DeleteMapping("delete/{id}")
  public void deleteComputer(@PathVariable Long id) {
    computerServiceTest.deleteComputerById(id);
  }
}

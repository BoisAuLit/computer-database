package com.excilys.cdb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.excilys.cdb.services.ComputerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@Component
@WebServlet("/delete-computer")
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerService computerService;

  public DeleteComputerServlet(ComputerService computerService) {
    this.computerService = computerService;
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Gson gson = new Gson();

    // Get data from POST body
    StringBuilder sb = new StringBuilder();
    String line = null;

    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }

    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
    List<String> ids = gson.fromJson(sb.toString(), listType);

    boolean success = computerService.batchDeleteComputer(ids);

    if (success) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }

}

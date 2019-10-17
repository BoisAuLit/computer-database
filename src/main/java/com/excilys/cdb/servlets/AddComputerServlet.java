package com.excilys.cdb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.google.gson.Gson;

/**
 * Servlet implementation class AddComputerServlet
 */
@Component
@WebServlet("/add-computer")
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;


  private ComputerService computerService;
  private CompanyService companyService;

  public AddComputerServlet(ComputerService computerService, CompanyService companyService) {
    this.computerService = computerService;
    this.companyService = companyService;
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<CompanyDto> companyDtos = companyService.getCompanyDtos();
    request.setAttribute("companyDtos", companyDtos);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(
        request,
        response);
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

    ComputerDto computerDto = gson.fromJson(sb.toString(), ComputerDto.class);
    int rowsAffected = computerService.saveComputer(computerDto);
    if (rowsAffected == 1) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }
}

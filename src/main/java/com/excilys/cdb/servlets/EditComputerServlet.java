package com.excilys.cdb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.google.gson.Gson;

/**
 * Servlet implementation class EditComputer
 */
@Component
@Deprecated
// @WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyService companyService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    long id = Long.parseLong(request.getParameter("id"));
    Optional<ComputerDto> computerDtoOpt = computerService.getComputerDtoById(id);
    request.setAttribute("computerDto", computerDtoOpt.get());

    List<CompanyDto> companyDtos = companyService.getCompanyDtos();
    request.setAttribute("companyDtos", companyDtos);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
        .forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
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
    int rowsAffected = computerService.updateComputer(computerDto);
    if (rowsAffected == 1) {
      response.setStatus(200);
    } else {
      response.sendError(500);
    }
  }

}

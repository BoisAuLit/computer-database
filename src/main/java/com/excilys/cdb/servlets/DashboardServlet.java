package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.services.ComputerService;

@Component
@Deprecated
// @WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ComputerService computerService;

  @Override
  public void init(
      ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ComputerPage computerPage;
    String limitStr = request.getParameter("limit");

    // First call of this method
    if (StringUtils.isEmpty(limitStr)) {
      computerPage = computerService.getDefaultComputerPage();
    } else {
      String nameToFind = request.getParameter("nameToFind");
      nameToFind = Objects.nonNull(nameToFind) ? nameToFind : "";
      request.setAttribute("nameToFind", nameToFind);
      String offsetStr = request.getParameter("offset");

      int limit = Integer.parseInt(limitStr);
      int offset = Integer.parseInt(offsetStr);
      computerPage = computerService.getComputerPage(nameToFind, limit, offset);
    }

    request.setAttribute("page", computerPage);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(
        request,
        response);
  }
}

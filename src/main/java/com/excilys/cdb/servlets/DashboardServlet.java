package com.excilys.cdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.services.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ComputerPage computerPage = ComputerService.getInstance().getDefaultComputerPage();
    request.setAttribute("page", computerPage);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(
        request,
        response);
  }

  // Todo, add doPost() method -> Act as « backend api »
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // String limitStr = request.getParameter("limit");
    // String offsetStr = request.getParameter("offset");
    //
    // int limit = Integer.parseInt(limitStr);
    // int offset = Integer.parseInt(offsetStr);



    int limit = 10;
    int offset = 10;

    ComputerPage computerPage = ComputerService.getInstance().getComputerPage(limit, offset);
    request.setAttribute("page", computerPage);

    System.out.println(computerPage.getComputerDtos());

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(
        request,
        response);
  }
}

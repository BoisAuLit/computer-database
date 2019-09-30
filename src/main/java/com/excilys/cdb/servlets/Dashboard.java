package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Computer;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Computer> computers = ComputerDao.getInstance().getAll();

    request.setAttribute("computers", computers);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(
        request,
        response);
  }
}

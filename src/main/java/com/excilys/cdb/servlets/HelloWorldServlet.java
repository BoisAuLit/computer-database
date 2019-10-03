package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.domain.Company;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Company> companies = CompanyDao.getInstance().getAll();

    request.setAttribute("companies", companies);

    this.getServletContext().getRequestDispatcher("/WEB-INF/HelloWorld.jsp").forward(
        request,
        response);
  }
}

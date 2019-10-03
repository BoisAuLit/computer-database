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
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.DtoBuilder;
import com.excilys.cdb.services.CompanyService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Long> companyIds = CompanyService.getInstance().getCompnayIds();

    request.setAttribute("companyIds", companyIds);

    List<Company> companies = CompanyDao.getInstance().getAll();
    List<CompanyDto> companyDtos = DtoBuilder.getCompnayDtoList(companies);

    request.setAttribute("companyDtos", companyDtos);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(
        request,
        response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // super.doPost(request, response);
  }

}

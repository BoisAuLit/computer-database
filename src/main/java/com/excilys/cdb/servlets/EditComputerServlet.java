package com.excilys.cdb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.DtoBuilder;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    long id = Long.parseLong(request.getParameter("id"));
    Optional<ComputerDto> computerDtoOpt = ComputerService.getInstance().getComputerDtoById(id);
    request.setAttribute("computerDto", computerDtoOpt.get());

    List<CompanyDto> companyDtos = DtoBuilder.getCompanyDtos();
    request.setAttribute("companyDtos", companyDtos);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(
        request,
        response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    StringBuffer jb = new StringBuffer();
    String line = null;
    try {
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null) {
        jb.append(line);
      }
    } catch (Exception e) {
      /* report an error */ }

    // try {
    //
    //
    // Json jsonObject = HTTP.toJSONObject(jb.toString());
    // } catch (JSONException e) {
    // // crash and burn
    // throw new IOException("Error parsing JSON request string");
    // }
  }

}

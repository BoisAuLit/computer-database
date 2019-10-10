<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">


<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
  rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
  rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
  rel="stylesheet" media="screen">

</head>

<body>
  <header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <a class="navbar-brand" href="dashboard"> Application -
        Computer Database </a>
    </div>
  </header>

  <section id="main">
    <div class="container">
      <h1 id="homeTitle">${ page.nbAllComputers }&nbsp;Computers&nbsp;found</h1>
      <div id="actions" class="form-horizontal">
        <div class="pull-left">

          <!--  -->
          <form id="searchForm" action="search()" method="GET"
            class="form-inline">

            <input type="search" id="searchbox" name="search"
              class="form-control" placeholder="Search name"
              value="${ nameToFind }" />
            <input type="submit" id="searchsubmit"
              value="Filter by name" class="btn btn-primary" />
          </form>

        </div>
        <div class="pull-right">
          <a class="btn btn-success" id="addComputer"
            href="add-computer">Add Computer</a>
          <a class="btn btn-default" id="editComputer" href="#"
            onclick="$.fn.toggleEditMode();">Edit</a>
        </div>
      </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
      <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">


      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Computer name</th>
            <th>Introduced date</th>
            <th>Discontinued date</th>
            <th>Company</th>
          </tr>
        </thead>
        <!-- Browse attribute computers -->

        <tbody id="results">

          <%--           <c:forEach var="computer" items="${ computers }"> --%>
          <c:forEach var="computer" items="${ page.computerDtos }">
            <tr>
              <td>
                <a href="edit-computer?id=${computer.id}">${ computer.name }</a>
              </td>
              <td>${ computer.introduced }</td>
              <td>${ computer.discontinued }</td>
              <td>${ computer.companyName }</td>
            </tr>
          </c:forEach>

        </tbody>

      </table>
    </div>
  </section>

  <footer class="navbar-fixed-bottom">
    <div class="container text-center">
      <ul class="pagination">

        <c:if test="${ page.beginPage ne 1 }">
          <li>
            <a
              href="dashboard?limit=${ page.currentMaxElementsPerPage }&offset=${(page.beginPage - 2) * page.currentMaxElementsPerPage}">
              <span>&laquo;</span>
            </a>
          </li>
        </c:if>



        <c:forEach begin="${ page.beginPage }" end="${ page.endPage }"
          varStatus="loop">

          <c:if test="${ loop.index eq page.currentPage}">
            <li class="active">
              <a href="#">${loop.index}</a>
            </li>
          </c:if>

          <c:if test="${ loop.index ne page.currentPage}">

            <li>
              <a href="#"
                onclick="changePage('${ page.currentMaxElementsPerPage }', 
                '${ (loop.index - 1) * page.currentMaxElementsPerPage  }')">
                ${loop.index} </a>

              <!--               <a -->
              <%--                 href="dashboard?limit=${ page.currentMaxElementsPerPage }&offset=${ (loop.index - 1) * page.currentMaxElementsPerPage  }"> --%>
              <%--                 ${loop.index} </a> --%>
            </li>
          </c:if>

        </c:forEach>

        <c:if test="${ page.totalPages gt page.endPage }">
          <li>
            <a href="#"
              onclick="changePage('${ page.currentMaxElementsPerPage }', 
                '${page.endPage * page.currentMaxElementsPerPage}')">
              <span>&raquo;</span>
            </a>

            <!--             <a -->
            <%--               href="dashboard?limit=${ page.currentMaxElementsPerPage }&offset=${page.endPage * page.currentMaxElementsPerPage}"> --%>
            <!--               <span>&raquo;</span> -->
            <!--             </a> -->

          </li>
        </c:if>

      </ul>


      <div class="btn-group btn-group-sm pull-right" role="group">
        <c:forEach var="nbElements"
          items="${ page.maxElementsPerPages }">

          <c:if test="${ page.currentMaxElementsPerPage eq  nbElements}">
            <button type="button" class="btn btn-default active">${ nbElements }</button>
          </c:if>

          <c:if test="${ page.currentMaxElementsPerPage ne  nbElements}">
            <button type="button" class="btn btn-default"
              onclick="changeSize('${nbElements}')">${ nbElements }
            </button>
          </c:if>

        </c:forEach>
      </div>

    </div>
  </footer>

  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
  <script
    src="${pageContext.request.contextPath}/js/validate_computer.js"></script>
  <script
    src="${pageContext.request.contextPath}/js/dashboard_change_page.js"></script>

</body>
</html>
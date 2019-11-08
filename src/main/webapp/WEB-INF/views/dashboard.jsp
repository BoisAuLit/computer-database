<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<%-- <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" media="screen"> --%>
<%-- <link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" media="screen"> --%>
<%-- <link href="<c:url value='/resources/css/font-awesome.css' />" rel="stylesheet" media="screen"> --%>
<%-- <script src="<c:url value='/resources/js/sort-table.js' />" type="text/javascript"></script> --%>


<%-- <spring:url value="/css/bootstrap.min.css" var="cousin" /> --%>
<%-- <spring:url value="/css/main.css" var="cousine" /> --%>
<%-- <spring:url value="/css/font-awesome.css" var="coupon" /> --%>
<%-- <spring:url value="/js/sort-table.js" var="coupure" /> --%>

<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
<script src="${pageContext.request.contextPath}/resources/js/sort-table.js" type="text/javascript"></script>



<%-- <link href="<c:url value="/src/main/webapp/css/bootstrap.min.css" />" rel="stylesheet" media="screen"> --%>
<%-- <link href="<c:url value="/css/main.css" />" rel="stylesheet" media="screen"> --%>
<%-- <link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet" media="screen"> --%>
<%-- <script src="<c:url value="/js/sort-table.js" />" type="text/javascript"></script> --%>


</head>

<body>
  <header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
    </div>
  </header>

  <section id="main">
    <div class="container">
      <h1 id="homeTitle">${ page.nbAllComputers }&nbsp;Computers&nbsp;found</h1>
      <div id="actions" class="form-horizontal">
        <div class="pull-left">

          <!--  -->
          <form id="searchForm" action="search()" method="GET" class="form-inline">

            <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${ nameToFind }" />
            <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
          </form>

        </div>
        <div class="pull-right">
          <a class="btn btn-success" id="addComputer" href="add-computer">Add Computer</a>
          <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
        </div>
      </div>
    </div>

    <!--     <form id="deleteForm" action="#" method="POST"> -->
    <!--       <input type="hidden" name="selection" value=""> -->
    <!--     </form> -->

    <div class="container" style="margin-top: 10px;">

      <table class="table table-striped table-bordered sortable">
        <thead>
          <tr>

            <th class="editMode" style="width: 60px; height: 22px;">
              <input type="checkbox" id="selectall" />
              <span style="vertical-align: top;">
                -
                <!--                 <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> -->
                <!--                   <i class="fa fa-trash-o fa-lg"></i> -->
                <!--                 </a> -->

                <a href="#" id="deleteSelected" onclick="deleteComputer()">
                  <i class="fa fa-trash-o fa-lg"></i>
                </a>

              </span>
            </th>


            <th>Computer name</th>
            <th>Introduced date</th>
            <th>Discontinued date</th>
            <th>Company</th>
          </tr>
        </thead>

        <tbody id="results">
          <c:forEach var="computer" items="${ page.computerDtos }">
            <tr>

              <!-- Delete computer checkbox -->
              <td class="editMode">
                <input type="checkbox" name="cb" class="cb" value="${ computer.id }">
              </td>

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
            <a href="dashboard?limit=${ page.currentMaxElementsPerPage }&offset=${(page.beginPage - 2) * page.currentMaxElementsPerPage}">
              <span>&laquo;</span>
            </a>
          </li>
        </c:if>

        <c:forEach begin="${ page.beginPage }" end="${ page.endPage }" varStatus="loop">

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

          </li>
        </c:if>

      </ul>


      <div class="btn-group btn-group-sm pull-right" role="group">
        <c:forEach var="nbElements" items="${ page.maxElementsPerPages }">

          <c:if test="${ page.currentMaxElementsPerPage eq  nbElements}">
            <button type="button" class="btn btn-default active">${ nbElements }</button>
          </c:if>

          <c:if test="${ page.currentMaxElementsPerPage ne  nbElements}">
            <button type="button" class="btn btn-default" onclick="changeSize('${nbElements}')">${ nbElements }</button>
          </c:if>

        </c:forEach>
      </div>

    </div>
  </footer>

  <%--   <spring:url value="/js/jquery.min.js" /> --%>
  <%--   <spring:url value="/js/bootstrap.min.js" /> --%>
  <%--   <spring:url value="/js/dashboard.js" /> --%>
  <%--   <spring:url value="/js/validate_computer.js" /> --%>
  <%--   <spring:url value="/js/dashboard_change_page.js" /> --%>
  <%--   <spring:url value="/js/delete-computer.js" /> --%>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validate_computer.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dashboard_change_page.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/delete-computer.js"></script>

  <%--   <spring:url value="/js/jquery.min.js" var="val1" /> --%>
  <%--   <spring:url value="/js/bootstrap.min.js" var="val2" /> --%>
  <%--   <spring:url value="/js/dashboard.js" var="val3" /> --%>
  <%--   <spring:url value="/js/validate_computer.js" var="val4" /> --%>
  <%--   <spring:url value="/js/dashboard_change_page.js" var="val5" /> --%>
  <%--   <spring:url value="/js/delete-computer.js" var="val6" /> --%>

<%--   <script src="<c:url value='/resources/js/jquery.min.js' />"></script> --%>
<%--   <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script> --%>
<%--   <script src="<c:url value='/resources/js/dashboard.js' />"></script> --%>
<%--   <script src="<c:url value='/resources/js/validate_computer.js' />"></script> --%>
<%--   <script src="<c:url value='/resources/js/dashboard_change_page.js' />"></script> --%>
<%--   <script src="<c:url value='/resources/js/delete-computer.js' />"></script> --%>

</body>
</html>
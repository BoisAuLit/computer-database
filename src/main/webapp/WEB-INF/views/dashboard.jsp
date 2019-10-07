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
      <h1 id="homeTitle">${ computers.size() }&nbsp;Computers&nbsp;found</h1>
      <div id="actions" class="form-horizontal">
        <div class="pull-left">
          <form id="searchForm" action="#" method="GET"
            class="form-inline">

            <input type="search" id="searchbox" name="search"
              class="form-control" placeholder="Search name" />
            <input type="submit" id="searchsubmit"
              value="Filter by name" class="btn btn-primary" />
          </form>
        </div>
        <div class="pull-right">
          <a class="btn btn-success" id="addComputer" href="addComputer">Add
            Computer</a> <a class="btn btn-default" id="editComputer"
            href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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

          <c:forEach var="computer" items="${ computers }">
            <tr>
              <td><a href="edit-computer?id=${computer.id}">${ computer.name }</a></td>

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
        <li><a href="#" aria-label="Previous"> <span
            aria-hidden="true">&laquo;</span>
        </a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#" aria-label="Next"> <span
            aria-hidden="true">&raquo;</span>
        </a></li>
      </ul>
    </div>

    <div class="btn-group btn-group-sm pull-right" role="group">
      <button type="button" class="btn btn-default">10</button>
      <button type="button" class="btn btn-default">50</button>
      <button type="button" class="btn btn-default">100</button>
    </div>

  </footer>

  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>
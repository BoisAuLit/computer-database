<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
  <header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
    </div>
  </header>


  <section id="main">
    <div class="container">
      <div class="row">
        <div class="col-xs-8 col-xs-offset-2 box">
          <div class="label label-default pull-right">id: ${ computerDto.id }</div>
          <h1>Edit Computer</h1>

          <form action="edit-computer" method="POST" id="edit-computer-form">
            <input type="hidden" value="${ computerDto.id }" id="id" name="id" />
            Computer id: ${ computerDto.id }
            <fieldset>

              <!-- Computer name field -->
              <div class="form-group">
                <label for="computerName">Computer name</label>
                <input type="text" class="form-control" id="computerName" name="computerName" value="${ computerDto.name }" placeholder=""
                  required>
              </div>

              <!-- Introduced date field -->
              <div class="form-group">
                <label for="introduced">Introduced date</label>
                <input type="date" value="${ computerDto.introduced }" class="form-control" id="introduced" name="introduced"
                  placeholder="Introduced date">
              </div>

              <!-- Discontinued date field -->
              <div class="form-group">
                <label for="discontinued">Discontinued date</label>
                <input type="date" value="${ computerDto.discontinued }" class="form-control" id="discontinued" name="discontinued"
                  placeholder="Discontinued date">
              </div>

              <!-- Company information field -->
              <div class="form-group">
                <label for="companyId">Company</label>

                <select class="form-control" id="companyId" name="companyId">

                  <option value="-1">No company is specified for this computer yet.</option>

                  <c:forEach var="companyDto" items="${ companyDtos }">

                    <option value="${companyDto.id}" ${ companyDto.id eq computerDto.companyId  ? 'selected' : 'cousin' }>
                      <fmt:formatNumber type="number" pattern="00" value="${companyDto.id}" /> &nbsp;&nbsp;&nbsp;&nbsp;${ companyDto.name }
                    </option>

                  </c:forEach>
                </select>

              </div>
            </fieldset>
            <div class="actions pull-right">
              <input type="submit" value="Confirm" class="btn btn-primary">
              or
              <a href="dashboard" class="btn btn-default">Cancel</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>

  <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/jquery.redirect.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/validate_computer.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/edit_computer.js"></script>

</body>
</html>
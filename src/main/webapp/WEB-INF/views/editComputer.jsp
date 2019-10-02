<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
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
      <div class="row">
        <div class="col-xs-8 col-xs-offset-2 box">
          <div class="label label-default pull-right">id: ${ param["id"] }</div>
          <h1>Edit Computer</h1>

          <form action="editComputer" method="POST">
            <input type="hidden" value="0" id="id" /> Computer id: ${ param["id"] }
            <fieldset>
              <div class="form-group">
                <label for="computerName">Computer name</label> <input
                  type="text" class="form-control" id="computerName"
                  placeholder="${param['name']}">
              </div>
              <div class="form-group">
                <label for="introduced">Introduced date (old
                  date - ${ param["introduced"] })</label> <input type="date"
                  value="" class="form-control" id="introduced"
                  placeholder="Introduced date">
              </div>
              <div class="form-group">
                <label for="discontinued">Discontinued date (old
                  date - ${ param["discontinued"] })</label> <input type="date"
                  value="" class="form-control" id="discontinued"
                  placeholder="Discontinued date">
              </div>
              <div class="form-group">
                <label for="companyId">Company</label> <select
                  class="form-control" id="companyId">

                  <c:forEach var="companyId" items="${ companyIds }">

                    <option value="${companyId}"
                      ${ companyId.toString().trim() eq param['companyId'].trim()  ? 'selected' : 'cousin' }>
                      ${ companyId }</option>
                  </c:forEach>

                </select>
              </div>
            </fieldset>
            <div class="actions pull-right">
              <input type="submit" value="Edit" class="btn btn-primary">
              or <a href="dashboard" class="btn btn-default">Cancel</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>

  <h1>id: ${ param["id"] }</h1>
  <h1>name: ${ param["name"] }</h1>
  <h1>introduced: ${ param["introduced"] }</h1>
  <h1>discontinued: ${ param["discontinued"] }</h1>
  <h1>company id: ${ param["companyId"] }</h1>
  <h1>company name: ${ param["companyName"] }</h1>


  <br />



</body>
</html>
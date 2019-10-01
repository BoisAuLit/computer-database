<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Test</title>
</head>

<body>
  <strong>All the computer comapanies</strong>
  <table border="1">
    <tr>
      <th>id</th>
      <th>name</th>
    </tr>
    <c:forEach var="company" items="${companies}">
      <tr>
        <td>${company.id}</td>
        <td>${company.name }</td>
      </tr>
    </c:forEach>
  </table>



  <!--   <div id="wrapper">
    <h1>Sortable Table of Search Queries</h1>

    <table id="keywords" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th><span>Keywords</span></th>
          <th><span>Impressions</span></th>
          <th><span>Clicks</span></th>
          <th><span>CTR</span></th>
          <th><span>Rank</span></th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="lalign">silly tshirts</td>
          <td>6,000</td>
          <td>110</td>
          <td>1.8%</td>
          <td>22.2</td>
        </tr>
        <tr>
          <td class="lalign">desktop workspace photos</td>
          <td>2,200</td>
          <td>500</td>
          <td>22%</td>
          <td>8.9</td>
        </tr>
        <tr>
          <td class="lalign">arrested development quotes</td>
          <td>13,500</td>
          <td>900</td>
          <td>6.7%</td>
          <td>12.0</td>
        </tr>
        <tr>
          <td class="lalign">popular web series</td>
          <td>8,700</td>
          <td>350</td>
          <td>4%</td>
          <td>7.0</td>
        </tr>
        <tr>
          <td class="lalign">2013 webapps</td>
          <td>9,900</td>
          <td>460</td>
          <td>4.6%</td>
          <td>11.5</td>
        </tr>
        <tr>
          <td class="lalign">ring bananaphone</td>
          <td>10,500</td>
          <td>748</td>
          <td>7.1%</td>
          <td>17.3</td>
        </tr>
      </tbody>
    </table>
  </div> -->




  <h1>&lt;c:param&gt; Demo</h1>
  <c:url var="myURL" value="/tag-types/core/param.jsp">
    <c:param name="param1" value="value1" />
    <c:param name="param2" value="value2" />
  </c:url>
  <a href="/<c:out value="${myURL}"/>"> Click to send two test
    parametets and values using &lt;c:param&gt;</a>
  <br />
  <br />
  <c:if test="${not empty param.param1 && not empty param.param2}">
      Param1
      <c:out value="${param.param1}" />
    <br />
      Param2
      <c:out value="${param.param2}" />
  </c:if>


</body>
</html>
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
<link href="${pageContext.request.contextPath}/css/dataTables.bootstrap.min.css"
  rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
  rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet"
  media="screen">

<link
  href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
  rel="stylesheet">

</head>

<body>
  <header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <a class="navbar-brand" href="dashboard"> Application - Computer
        Database </a>
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
            <input type="submit" id="searchsubmit" value="Filter by name"
              class="btn btn-primary" />
          </form>

        </div>
        <div class="pull-right">
          <a class="btn btn-success" id="addComputer" href="add-computer">Add
            Computer</a>
          <a class="btn btn-default" id="editComputer" href="#"
            onclick="$.fn.toggleEditMode();">Edit</a>
        </div>
      </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
      <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">


      <!--       <table class="table table-striped table-bordered"> -->
      <!--         <thead> -->
      <!--           <tr> -->

      <!--             <th> -->
      <!--               <b>Computer name</b> -->
      <!--               <i class="fa fa-fw fa-sort"></i> -->
      <!--             </th> -->

      <!--                         <th>Computer name</th> -->
      <!--             <th>Introduced date</th> -->
      <!--             <th>Discontinued date</th> -->
      <!--             <th>Company</th> -->
      <!--           </tr> -->
      <!--         </thead> -->
      <!--         Browse attribute computers -->

      <!--         <tbody id="results"> -->

      <%--                     <c:forEach var="computer" items="${ computers }"> --%>
      <%--           <c:forEach var="computer" items="${ page.computerDtos }"> --%>
      <!--             <tr> -->
      <!--               <td> -->
      <%--                 <a href="edit-computer?id=${computer.id}">${ computer.name }</a> --%>
      <!--               </td> -->
      <%--               <td>${ computer.introduced }</td> --%>
      <%--               <td>${ computer.discontinued }</td> --%>
      <%--               <td>${ computer.companyName }</td> --%>
      <!--             </tr> -->
      <%--           </c:forEach> --%>

      <!--         </tbody> -->

      <!--       </table> -->

      <table id="example" class="table table-striped table-bordered"
        style="width: 100%">
        <thead>
          <tr>
            <th>Name</th>
            <th>Position</th>
            <th>Office</th>
            <th>Age</th>
            <th>Start date</th>
            <th>Salary</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Tiger Nixon</td>
            <td>System Architect</td>
            <td>Edinburgh</td>
            <td>61</td>
            <td>2011/04/25</td>
            <td>$320,800</td>
          </tr>
          <tr>
            <td>Garrett Winters</td>
            <td>Accountant</td>
            <td>Tokyo</td>
            <td>63</td>
            <td>2011/07/25</td>
            <td>$170,750</td>
          </tr>
          <tr>
            <td>Ashton Cox</td>
            <td>Junior Technical Author</td>
            <td>San Francisco</td>
            <td>66</td>
            <td>2009/01/12</td>
            <td>$86,000</td>
          </tr>
          <tr>
            <td>Cedric Kelly</td>
            <td>Senior Javascript Developer</td>
            <td>Edinburgh</td>
            <td>22</td>
            <td>2012/03/29</td>
            <td>$433,060</td>
          </tr>
          <tr>
            <td>Airi Satou</td>
            <td>Accountant</td>
            <td>Tokyo</td>
            <td>33</td>
            <td>2008/11/28</td>
            <td>$162,700</td>
          </tr>
          <tr>
            <td>Brielle Williamson</td>
            <td>Integration Specialist</td>
            <td>New York</td>
            <td>61</td>
            <td>2012/12/02</td>
            <td>$372,000</td>
          </tr>
          <tr>
            <td>Herrod Chandler</td>
            <td>Sales Assistant</td>
            <td>San Francisco</td>
            <td>59</td>
            <td>2012/08/06</td>
            <td>$137,500</td>
          </tr>
          <tr>
            <td>Rhona Davidson</td>
            <td>Integration Specialist</td>
            <td>Tokyo</td>
            <td>55</td>
            <td>2010/10/14</td>
            <td>$327,900</td>
          </tr>
          <tr>
            <td>Colleen Hurst</td>
            <td>Javascript Developer</td>
            <td>San Francisco</td>
            <td>39</td>
            <td>2009/09/15</td>
            <td>$205,500</td>
          </tr>
          <tr>
            <td>Sonya Frost</td>
            <td>Software Engineer</td>
            <td>Edinburgh</td>
            <td>23</td>
            <td>2008/12/13</td>
            <td>$103,600</td>
          </tr>
          <tr>
            <td>Jena Gaines</td>
            <td>Office Manager</td>
            <td>London</td>
            <td>30</td>
            <td>2008/12/19</td>
            <td>$90,560</td>
          </tr>
          <tr>
            <td>Quinn Flynn</td>
            <td>Support Lead</td>
            <td>Edinburgh</td>
            <td>22</td>
            <td>2013/03/03</td>
            <td>$342,000</td>
          </tr>
          <tr>
            <td>Charde Marshall</td>
            <td>Regional Director</td>
            <td>San Francisco</td>
            <td>36</td>
            <td>2008/10/16</td>
            <td>$470,600</td>
          </tr>
          <tr>
            <td>Haley Kennedy</td>
            <td>Senior Marketing Designer</td>
            <td>London</td>
            <td>43</td>
            <td>2012/12/18</td>
            <td>$313,500</td>
          </tr>

        </tbody>
        <tfoot>
          <tr>
            <th>Name</th>
            <th>Position</th>
            <th>Office</th>
            <th>Age</th>
            <th>Start date</th>
            <th>Salary</th>
          </tr>
        </tfoot>
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
        <c:forEach var="nbElements" items="${ page.maxElementsPerPages }">

          <c:if test="${ page.currentMaxElementsPerPage eq  nbElements}">
            <button type="button" class="btn btn-default active">${ nbElements }</button>
          </c:if>

          <c:if test="${ page.currentMaxElementsPerPage ne  nbElements}">
            <button type="button" class="btn btn-default"
              onclick="changeSize('${nbElements}')">${ nbElements }</button>
          </c:if>

        </c:forEach>
      </div>

    </div>
  </footer>



  <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/dataTables.bootstrap.min.js"></script>
  
  
  
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
  <script src="${pageContext.request.contextPath}/js/validate_computer.js"></script>
  <script src="${pageContext.request.contextPath}/js/dashboard_change_page.js"></script>

  <script>
      $(document).ready(function() {
        $('#example').DataTable();
      });
    </script>

</body>
</html>
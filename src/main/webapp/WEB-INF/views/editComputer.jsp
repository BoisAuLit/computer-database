<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

          <form action="javascript:test()" method="POST" id="myForm">
            <input type="hidden" value="${ param['id'] }" id="id"
              name="id" /> Computer id: ${ param["id"] }
            <fieldset>
              <div class="form-group">
                <label for="computerName">Computer name</label> <input
                  type="text" class="form-control" id="computerName"
                  name="computerName" value="${param['name']}"
                  placeholder="" required>
              </div>
              <div class="form-group">
                <label for="introduced">Introduced date</label> <input
                  type="date" value="${param['introduced'].trim()}"
                  class="form-control" id="introduced" name="introduced"
                  placeholder="Introduced date">
              </div>
              <div class="form-group">
                <label for="discontinued">Discontinued date</label> <input
                  type="date" value="${ param['discontinued'].trim() }"
                  class="form-control" id="discontinued"
                  name="discontinued" placeholder="Discontinued date">
              </div>
              <div class="form-group">
                <label for="companyId">Company</label> <select
                  class="form-control" id="companyId" name="companyId">

                  <c:forEach var="companyDto" items="${ companyDtos }">

                    <c:set var="companyId" value="${companyDto.id}" />
                    <c:set var="companyName" value="${companyDto.name}" />

                    <option value="${companyId}"
                      ${ companyId.toString().trim() eq param['companyId'].trim()  ? 'selected' : 'cousin' }>

                      <fmt:formatNumber type="number" pattern="00"
                        value="${companyId}" />
                      &nbsp;&nbsp;&nbsp;&nbsp;${ companyName }
                    </option>

                  </c:forEach>
                </select>
              </div>
            </fieldset>
            <div class="actions pull-right">
              <input type="submit" value="Confirm"
                class="btn btn-primary"> or <a href="dashboard"
                class="btn btn-default">Cancel</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>

  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script>
			function test() {
				
				console.log("cousin")
				
				var id = $("input#id").val()
				var name = $("input#computerName").val().trim()
				var introduced = $("input#introduced").val().trim()
				var discontinued = $("input#discontinued").val().trim()
				var companyId = $("option:selected").val().trim()

				
				// Name cannot be empyt string
				if (!name.length) {
					alert("Name cannot be filled with empty characters!")
				}
				
				// Validate introduced date
				if (introduced.length){
					var dateArray = introduced.split("-")
					var year = parseInt(dateArray[0])
					var month = parseInt(dateArray[1])
					var day = parseInt(dateArray[2])
					
					console.log(year)
					console.log(month)
					console.log(day)
					
					var introducedDate = new Date(year, month, day)
					var rightNow = Date.now()
// 					if (introducedDate > rightNow) {
// 						alert("Introduced Date cannot be in the futer!"")
// 					} else {
// 						console.log("Introduced date is good.")
// 					}
				}
								
// 				window.location.href = "https://www.google.fr"

				// 	   alert(window.location.href)
			}

			/*   $(document).ready(function () {

			 $('form').validate({ // initialize the plugin
			 rules: {
			 computerName: "required"
			 },
			 invalidHandler: () => {
			 alert("This is not good!")
			 }
			 });

			 }); */

			/* 			function test() {


			 /* var companyId = $("option:selected").val() */

			/* 			function test() {

			
			 /* var to_print = "
			 id = ${id}
			 name = ${computerName}
			 introduced = ${introduced}
			 discontinued = ${discontinued}
			 companyId = ${companyId}
			 " */

			/* alert(id + "\n" + name + "\n" + introduced + "\n" + discontinued
					+ "\n" + companyId); */

			/* 			$(document).ready(function() {

			 $('#myform').validate({ // initialize the plugin
			 rules : {
			 field1 : {
			 required : true,
			 email : true
			 },
			 field2 : {
			 required : true,
			 minlength : 5
			 }
			 }
			 });

			 }); */
		</script>

  <h1>id: ${ param["id"] }</h1>
  <h1>name: ${ param["name"] }</h1>
  <h1>introduced: ${ param["introduced"] }</h1>
  <h1>discontinued: ${ param["discontinued"] }</h1>
  <h1>companyId: ${ param["companyId"] }</h1>


</body>
</html>
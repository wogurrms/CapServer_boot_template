<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="assets/ico/favicon.png">

<title><tiles:insertAttribute name="title" /></title>


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">


<!-- Carousel CSS -->
<link
	href="<c:url value="/resources/link/Theme/assets/css/style.css"/>"
	rel="stylesheet">


<!-- Bootstrap core CSS -->
<link
	href="<c:url value="/resources/link/Theme/assets/css/bootstrap.css"/>"
	rel="stylesheet">

<!-- funkRadio CSS -->
<link href="<c:url value="/resources/css/funkyRadio.css"/>"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/link/Theme/assets/css/main.css"/>"
	rel="stylesheet">

<link
	href="<c:url value="/resources/link/Theme/assets/css/font-awesome.min.css"/>"
	rel="stylesheet">

<link
	href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700'
	rel='stylesheet' type='text/css'>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<script
	src="<c:url value="/resources/link/Theme/assets/js/modernizr.custom.js"/>"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<script type="text/javascript">
</script>
<body>
	<!-- Menu -->
	<nav class="menu" id="theMenu">
		<div class="menu-wrap">
			<h1 class="logo">
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<li><a href="<c:url value="/login"/> ">Login</a></li>
				</c:if>
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<li><a href="<c:url value="/logout"/> ">Logout</a></li>
				</c:if>
			</h1>
			<i class="fa fa-arrow-right menu-close"></i> <a href="/CapServer">Home</a>
			<a href="<c:url value="users"/>"> Users</a> <a
				href="<c:url value="/userpage/todayamount/${pageContext.request.userPrincipal.name}"/>" >TodayAmount</a> <a
				href="<c:url value="/userpage/ranking/${pageContext.request.userPrincipal.name}"/>">Ranking</a> <a
				href="<c:url value="/userpage/chartFromRecord"/>">Chart</a> <a
				href="<c:url value="/userpage/mynicotine/${pageContext.request.userPrincipal.name}"/>">My nicotine</a> <a
				href="<c:url value="/userpage/fagerstrom"/>">FagerStrom Test</a> <a
				href="<c:url value="/userpage/spend/${pageContext.request.userPrincipal.name}"/>">Spend</a> <a
				href="<c:url value="/insertUser"/>">InsertUser</a> <a
				href="<c:url value="/insertRecord/${pageContext.request.userPrincipal.name}"/>">InsertRec</a> <a
				href="<c:url value="/insertYesterDayRecord"/>">InsertYesRec</a> <a
				href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
				class="fa fa-twitter"></i></a> <a href="#"><i class="fa fa-dribbble"></i></a>
			<a href="#"><i class="fa fa-envelope"></i></a>

		</div>

		<!-- Menu button -->
		<div id="menuToggle">
			<i class="fa fa-bars"></i>
		</div>
	</nav>
	<div>
		<tiles:insertAttribute name="header" />
	</div>

	<div>
		<tiles:insertAttribute name="body" />
	</div>

	<div>
		<tiles:insertAttribute name="footer" />
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script
		src="<c:url value="/resources/link/Theme/assets/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/link/Theme/assets/js/main.js"/>"></script>
	<script
		src="<c:url value="/resources/link/Theme/assets/js/masonry.pkgd.min.js"/>"></script>
	<script
		src="<c:url value="/resources/link/Theme/assets/js/imagesloaded.js"/>"></script>
	<script
		src="<c:url value="/resources/link/Theme/assets/js/classie.js"/>"></script>
	<script
		src="<c:url value="/resources/link/Theme/assets/js/AnimOnScroll.js"/>"></script>
	<script>
		new AnimOnScroll(document.getElementById('grid'), {
			minDuration : 0.4,
			maxDuration : 0.7,
			viewportFactor : 0.2
		});
	</script>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="container-wrapper">
	<div class="container">
		<h2>AdminiStrator Page</h2>
		<p class="lead">Product를 관리 할 수 있는 페이지입니다.</p>
	</div>
	
	<div class="container">
		<a href="<c:url value="admin/productInventory"/> "><h2>Product Inventory</h2></a>
		<p class="lead">Here you can view, check and modify the product inventory!</p>
	</div>
	
</div>
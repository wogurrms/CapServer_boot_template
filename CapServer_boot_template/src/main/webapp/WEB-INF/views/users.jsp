<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="container-wrapper">
	<div class="container">
		<h2></h2>
		<p class="lead"></p>
		<table class="table table-condensed">
			<thead>
				<tr class="success">
					<th style="text-align:center">UID</th>
					<th style="text-align:center">Mac</th>
					<th style="text-align:center">Nick</th>
					<th style="text-align:center">Tobacco</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users}">
					<tr>
						<td align="center">${user.uid}</td>
						<td align="center">${user.mac}</td>
						<td align="center">${user.nick}</td>
						<td align="center">${user.tobac.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="container-wrapper">
	<div class="container">
		<h2>FagerStrom Reult Page</h2>
		<p class="lead">파거스트롬 테스트!</p>
		<table class="table table-condensed" action>
			<tr>
				<th style="text-align: center">니코틴 의존도</th>
				<th style="text-align: center">사용자의 상태</th>
			</tr>
			<tr>
				<td align="center">${resultMap.nicotineDependence}</td>
				<td align="center">${resultMap.userStatus}</td>
			</tr>
		</table>

	</div>
</div>


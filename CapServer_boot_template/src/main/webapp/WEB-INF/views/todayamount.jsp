<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Today Amount</h2>
		<p class="lead">오늘의 흡연량 입니다.</p>
		<table class="table table-striped">
			<thead>
				<tr class="success">
					<th style="text-align:center">TodayAmount</th>
					<th style="text-align:center">Avg</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="center"><h2>${responseData.todayAmount}</h2></td>
					<td align="center"><h2><fmt:formatNumber value="${responseData.avg}" pattern=".00"/></h2></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
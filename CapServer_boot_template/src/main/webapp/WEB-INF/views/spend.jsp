<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Total Spend to Smoke</h2>
		<p class="lead">지금 까지 담배에 소비한 금액입니다.</p>
		<table class="table table-striped">
			<thead>
				<tr class="success">
					<th style="text-align:center">Total Spend Amount</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="center"><h2> ${myTotalSpend} 원</h2></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
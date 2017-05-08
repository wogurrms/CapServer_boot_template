
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<div class="container-wrapper">
	<div class="container">
		<h2>FagerStrom Test</h2>
		<p class="lead">파거스트롬 테스트!</p>
		<form name="q1" action=<c:url value="/userpage/fagerstromresult"/> method="get">

			<table class="table table-condensed" action>
				<tr>
					<td>
						<h4>1. 아침에 일어나서 얼마만에 첫 담배를 피우십니까?</h4>
						<div class="funkyradio">
							<div class="funkyradio-success">
								<input type="radio" name="radio-1" id="radio1" value="3" checked />
								<label for="radio1">5분 이내</label>
							</div>
							<div class="funkyradio-success">
								<input type="radio" name="radio-1" id="radio2" value="2" /> <label
									for="radio2">6 ~ 30분</label>
							</div>
							<div class="funkyradio-success">
								<input type="radio" name="radio-1" id="radio3" value="1" /> <label
									for="radio3">30분 초과</label>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td><br>
						<h4>2. 금연구역에서 담배를 참기가 어렵습니까?</h4>
						<div class="funkyradio">
							<div class="funkyradio-success">
								<input type="radio" name="radio-2" id="radio2-1" value="1"
									checked /> <label for="radio2-1">예</label>
							</div>
							<div class="funkyradio-success">
								<input type="radio" name="radio-2" id="radio2-2" value="0" /> <label
									for="radio2-2">아니오</label>
							</div>
						</div></td>
				</tr>
				<tr>
					<td><br>
						<h4>3. 하루 중 담배 맛이 가장 좋을 때는 언제입니까?</h4>
						<div class="funkyradio">
							<div class="funkyradio-success">
								<input type="radio" name="radio-3" id="radio3-1" value="1"
									checked /> <label for="radio3-1">첫 담배</label>
							</div>
							<div class="funkyradio-success">
								<input type="radio" name="radio-3" id="radio3-2" value="0" /> <label
									for="radio3-2">그 외</label>
							</div>
						</div></td>
				</tr>
				<tr>
					<td><br>
						<h4>4. 몸이 아파 하루 종일 누워있을 때에도 담배를 피우십니까?</h4>
						<div class="funkyradio">
							<div class="funkyradio-success">
								<input type="radio" name="radio-4" id="radio4-1" value="1"
									checked /> <label for="radio4-1">예</label>
							</div>
							<div class="funkyradio-success">
								<input type="radio" name="radio-4" id="radio4-2" value="0" /> <label
									for="radio4-2">아니오</label>
							</div>
						</div></td>
				</tr>
				<tr>

					<td><br>
						<button type="submit" class="btn btn-success btn-md">SUBMIT</button></td>
				</tr>
			</table>
		</form>

	</div>
</div>


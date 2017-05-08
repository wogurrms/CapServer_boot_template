
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<script type="text/javascript">
	google.charts.load('current', {
		packages : [ 'corechart', 'line' ]
	});
	google.charts.setOnLoadCallback(drawBasic);

	function getContextPath() {
		var offset = location.href.indexOf(location.host)
				+ location.host.length;
		var ctxPath = location.href.substring(offset, location.href
				.indexOf('/', offset + 1));
		return ctxPath;
	}
	
	function drawBasic() {
		var jsonData = $.ajax({
			url : getContextPath() + "/userpage/chartFromRecordToJsonArray/" + "${pageContext.request.userPrincipal.name}",
			dataType : "json",
			data : data,
			async : false
		}).responseText;
		var data = new google.visualization.DataTable(jsonData);
		var options = {
			chart : {
				title : 'My Smoking Pattern Chart'
			},
			vAxis : {
				viewWindowMode : 'explicit',
				viewWindow : {
					max : 30,
					min : 0
				}
			},
			bars : 'vertical', // Required for Material Bar Charts.
			height : 400
		};

		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, options);
		google.visualization.events.addListener(chart, 'select', selectHandler);

		function selectHandler() {
			var selection = chart.getSelection();
			var requestData = data.getValue(selection[0].row, 0);
			var dailyJsonData = $.ajax({
				url : getContextPath() + "/userpage/chartFromRecordToJsonArrayDaily/" + "${pageContext.request.userPrincipal.name}/"
						+ requestData,
				type : "get",
				async : false
			}).responseText;
			
			data = new google.visualization.DataTable(dailyJsonData);
			chart.draw(data, options);

		}
		

	}
	
	$(document).ready(function(){
		$("#refresh-btn").click(function(){
			var jsonData = $.ajax({
				url : getContextPath() + "/userpage/chartFromRecordToJsonArray/" + "${pageContext.request.userPrincipal.name}",
				dataType : "json",
				data : data,
				async : false
			}).responseText;
			var data = new google.visualization.DataTable(jsonData);
			var options = {
				chart : {
					title : 'My Smoking Pattern Chart'
				},
				vAxis : {
					viewWindowMode : 'explicit',
					viewWindow : {
						max : 30,
						min : 0
					}
				},
				bars : 'vertical', // Required for Material Bar Charts.
				height : 400
			};

			var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
			chart.draw(data, options);
			google.visualization.events.addListener(chart, 'select', selectHandler);

			function selectHandler() {
				var selection = chart.getSelection();
				var requestData = data.getValue(selection[0].row, 0);
				var dailyJsonData = $.ajax({
					url : getContextPath() + "/userpage/chartFromRecordToJsonArrayDaily/" + "${pageContext.request.userPrincipal.name}/"
					+ requestData,
					type : "get",
					async : false
				}).responseText;
				
				data = new google.visualization.DataTable(dailyJsonData);
				chart.draw(data, options);
			}
		})
	})
</script>

<div class="container-wrapper">
	<div class="container">
		<h2></h2>
		<p class="lead"></p>
		<div id="chart_div"></div>
		<p space></p>
		<button id ="refresh-btn" type="button" class="btn btn-default">일간 차트</button>
	</div>
</div>


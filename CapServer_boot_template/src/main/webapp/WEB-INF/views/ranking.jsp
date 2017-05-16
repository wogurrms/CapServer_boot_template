<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<style type="text/css">
.google-visualization-table-td {
	text-align: center !important;
}
.google-visualization-table-th {
	text-align: center !important;
}

</style>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'table' ]
	});
	google.charts.setOnLoadCallback(drawTable);

	function drawTable() {
		var jsonData = $.ajax({
			url : "${pageContext.request.contextPath}"
					+ "/userpage/rankingFromRecordToJsonArray",
			dataType : "json",
			data : data,
			async : false
		}).responseText;

		var data = new google.visualization.DataTable(jsonData);

		var table = new google.visualization.Table(document
				.getElementById('table_div'));

		table.draw(data, {
			showRowNumber : true,
			width : '100%',
			height : '100%'
		});
	}
</script>

<div class="container-wrapper">
	<div class="container">
		<h2>Your Ranking in All Users</h2>
		<p class="lead">전체 사용자 중 당신의 랭킹입니다.</p>
		<div id="table_div" class="google-visualization-table-td"></div>

	</div>
</div>
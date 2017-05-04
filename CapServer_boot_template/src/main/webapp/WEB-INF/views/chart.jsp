
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<script type="text/javascript">
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawBasic);

function drawBasic() {


	function getContextPath() {
		var offset = location.href.indexOf(location.host)
				+ location.host.length;
		var ctxPath = location.href.substring(offset, location.href.indexOf(
				'/', offset + 1));
		return ctxPath;
	}
	

	var jsonData = $.ajax({
		url : getContextPath() + "/chartFromRecordToJsonArray",
		dataType : "json",
		data : data,
		async : false
	}).responseText;

	
      var data = new google.visualization.DataTable(jsonData);

      var options = {
        chart: {
          title: 'My Smoking Pattern Chart',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017',
        },
          vAxis: {
            viewWindowMode:'explicit',
            viewWindow: {
              max:30,
              min:0
            }
        },
        bars: 'vertical', // Required for Material Bar Charts.
        height: 400
      };


      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

      chart.draw(data,options);
    }
</script>

<div class="container-wrapper">
	<div class="container">
		<h2></h2>
		<p class="lead"></p>
		<div id="chart_div"></div>

	</div>
</div>


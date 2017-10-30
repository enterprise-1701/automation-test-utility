package com.cubic.reportengine.report;

interface ReportTemplates {

	String SUMMARY_REPORT =
			"<html>\r\n" + 
			"	<head>		\r\n" + 
			"		<title>Automation Execution Results Summary</title>\r\n" + 
			"		<style type='text/css'>\r\n" + 
			"			body {\r\n" + 
			"				background-color: #FFFFFF;\r\n" + 
			"				font-family: Verdana, Geneva, sans-serif;\r\n" + 
			"				text-align: left;\r\n" + 
			"			}\r\n" + 
			"			table {\r\n" + 
			"				box-shadow: 9px 9px 10px 4px #BDBDBD;\r\n" + 
			"				border: 0px solid #4D7C7B;\r\n" + 
			"				border-collapse: collapse;\r\n" + 
			"				border-spacing: 0px;\r\n" + 
			"				width: 1000px;\r\n" + 
			"				margin-left: auto;\r\n" + 
			"				margin-right: auto;\r\n" + 
			"			}\r\n" + 
			"			tr.heading {\r\n" + 
			"				background-color: #041944;\r\n" + 
			"				color: #FFFFFF;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				background: -o-linear-gradient(bottom, #999999 5%, #000000 100%);\r\n" + 
			"				background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000));\r\n" + 
			"				background: -moz-linear-gradient( center top, #999999 5%, #000000 100%);\r\n" + 
			"				filter: progid: DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);\r\n" + 
			"				background: -o-linear-gradient(top, #999999, 000000);\r\n" + 
			"			}\r\n" + 
			"			tr.subheading {\r\n" + 
			"				background-color: #6A90B6;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				text-align: left;\r\n" + 
			"			}\r\n" + 
			"			tr.content {\r\n" + 
			"				background-color: #FFFFFF;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				display: table-row;\r\n" + 
			"			}\r\n" + 
			"			tr.content2 {\r\n" + 
			"				background-color: #;\r\n" + 
			"				E1E1E1border: 1px solid #4D7C7B;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				display: table-row;\r\n" + 
			"			}\r\n" + 
			"			td,\r\n" + 
			"			th {\r\n" + 
			"				padding: 5px;\r\n" + 
			"				border: 1px solid #4D7C7B;\r\n" + 
			"				text-align: inherit /;\r\n" + 
			"			}\r\n" + 
			"			th.Logos {\r\n" + 
			"				padding: 0px;\r\n" + 
			"				border: 0px ;\r\n" + 
			"				\r\n" + 
			"			}\r\n" + 
			"			td.justified {\r\n" + 
			"				text-align: justify;\r\n" + 
			"			}\r\n" + 
			"			td.pass {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: green;\r\n" + 
			"			}\r\n" + 
			"			td.fail {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: red;\r\n" + 
			"			}\r\n" + 
			"			\r\n" + 
			"			tr.content3 {\r\n" + 
			"							background-color: #c4d2e1;\r\n" + 
			"							color: #000000;\r\n" + 
			"							}\r\n" + 
			"\r\n" + 
			"			td.content4 {\r\n" + 
			"							font-family: Verdana;\r\n" + 
			"							font-size: 0.8em;\r\n" + 
			"							}				\r\n" + 
			"		</style>\r\n" + 
			"			\r\n" + 
			"	<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n" + 
			"	<script type=\"text/javascript\">\r\n" + 
			"	  google.charts.load(\"current\", {packages:[\"corechart\"]});\r\n" + 
			"	  google.charts.setOnLoadCallback(drawChart);\r\n" + 
			"	  function drawChart() {\r\n" + 
			"		var data = google.visualization.arrayToDataTable([\r\n" + 
			"		  ['Status', 'Count'],\r\n" + 
			"		  ['PASS',     <!--Total No Of Passed Scripts-->],\r\n" + 
			"		  ['FAIL',      <!--Total No Of Failed Scripts-->]\r\n" + 
			"		]);\r\n" + 
			"\r\n" + 
			"		var options = {\r\n" + 
			"		  slices: {0: {color: 'Green'}, 1:{color: 'Red'}},\r\n" + 
			"		  backgroundColor: '#c4d2e1',\r\n" + 
			"		  pieHole: 0.3,\r\n" + 
			"		  is3D: true\r\n" + 
			"		};\r\n" + 
			"\r\n" + 
			"		var chart = new google.visualization.PieChart(document.getElementById('donutchart'));\r\n" + 
			"		chart.draw(data, options);\r\n" + 
			"	  }\r\n" + 
			"	</script>         \r\n" + 
			"	 \r\n" + 
			"	<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n" + 
			"	  <script type=\"text/javascript\">\r\n" + 
			"		google.charts.load(\"current\", {packages:['corechart']});\r\n" + 
			"		google.charts.setOnLoadCallback(drawChart);\r\n" + 
			"		function drawChart() {\r\n" + 
			"		  var data = google.visualization.arrayToDataTable([\r\n" + 
			"			[\"Element\", \"Total\", { role: \"style\" } ],\r\n" + 
			"			[\"Total Tests\", <!--Total No Of Test Scripts-->, \"#0059b3\"],\r\n" + 
			"			[\"Pass\", <!--Total No Of Passed Scripts-->, \"green\"],\r\n" + 
			"			[\"Fail\", <!--Total No Of Failed Scripts-->, \"red\"],\r\n" + 
			"		  ]);\r\n" + 
			"	 \r\n" + 
			"		  var view = new google.visualization.DataView(data);\r\n" + 
			"		  view.setColumns([0, 1,\r\n" + 
			"						   { calc: \"stringify\",\r\n" + 
			"							 sourceColumn: 1,\r\n" + 
			"							 type: \"string\",\r\n" + 
			"							 role: \"annotation\" },\r\n" + 
			"						   2]);\r\n" + 
			"	 \r\n" + 
			"		  var options = { \r\n" + 
			"			 width: 300,\r\n" + 
			"			height: 200,\r\n" + 
			"					backgroundColor: '#c4d2e1',\r\n" + 
			"			bar: {groupWidth: \"95%\"},\r\n" + 
			"			legend: { position: \"none\" }\r\n" + 
			"		   \r\n" + 
			"		  };\r\n" + 
			"		  var chart = new google.visualization.ColumnChart(document.getElementById(\"columnchart_values\"));\r\n" + 
			"		  chart.draw(view, options);\r\n" + 
			"	  }\r\n" + 
			"	</script>			\r\n" + 
			"	</head>\r\n" + 
			"	\r\n" + 
			"<body>\r\n" + 
			"<br>\r\n" + 
			"<table >\r\n" + 
			"	<tr>\r\n" + 
			"	   <th class='Logos'>\r\n" + 
			"	   <img align='right' src=\"DetailedReports\\logos\\Cubic.png\"/>\r\n" + 
			"	   </th>\r\n" + 
			"	</tr>\r\n" + 
			"</table>\r\n" + 
			"<table id='header'>\r\n" + 
			"		<tr class='heading'>\r\n" + 
			"			<th colspan='4'\r\n" + 
			"				style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> Automation Execution Result Summary </th>\r\n" + 
			"		</tr>\r\n" + 
			"		<tr class='subheading'>\r\n" + 
			"			<td>&nbsp;Date&nbsp;&&nbsp;Time&nbsp;:&nbsp;</td>\r\n" + 
			"			<td><!--Date & Time-->&nbsp;</td>\r\n" + 
			"			<td>&nbsp;Total	No Of Test Scripts&nbsp;:&nbsp;</td>\r\n" + 
			"			<td><!--Total No Of Test Scripts--></td>\r\n" + 
			"		</tr>\r\n" + 
			"		<tr class='subheading'>\r\n" + 
			"			<td>&nbsp;Host Name&nbsp;:&nbsp;</td>\r\n" + 
			"			<td><!--Host Name--></td>\r\n" + 
			"			<td>&nbsp;Total No Of Passed Scripts&nbsp;:</td>\r\n" + 
			"			<td><!--Total No Of Passed Scripts--></td>\r\n" + 
			"		</tr>\r\n" + 
			"		<tr class='subheading'>\r\n" + 
			"			<td>&nbsp;OS Name&nbsp;:</td>\r\n" + 
			"			<td><!--OS_Name--></td>\r\n" + 
			"			<td>&nbsp;Total No Of Failed Scripts&nbsp;:</td>\r\n" + 
			"			<td><!--Total No Of Failed Scripts--> </td>\r\n" + 
			"		</tr>\r\n" + 
			"		<tr class='subheading'>\r\n" + 
			"			<td>&nbsp;Browser Name&nbsp;:</td>\r\n" + 
			"			<td><!--Browser Name--></td>\r\n" + 
			"			<td>&nbsp;Overall Execution Time&nbsp;:</td>\r\n" + 
			"			<td><!--Overall Execution Time--></td>\r\n" + 
			"		</tr>\r\n" + 
			"</table>\r\n" + 
			"		\r\n" + 
			"<Table Border Width=100%>\r\n" + 
			"<Tr  class=\"content3\">\r\n" + 
			"  <Td  class=\"content4\" colspan=2 Width=100%>\r\n" + 
			"      <div id=\"donutchart\" style=\"width: 371px; height: 200px;\"></div>\r\n" + 
			"    \r\n" + 
			"  </Td>\r\n" + 
			"  <Td  class=\"content4\" colspan=2 Width=100%>\r\n" + 
			"     <div id=\"columnchart_values\" style=\"width: 490px; height: 200px;\"></div>\r\n" + 
			"    \r\n" + 
			"  </Td>\r\n" + 
			"</Tr>\r\n" + 
			"</table>\r\n" + 
			"\r\n" + 
			"<table>				\r\n" + 
			"	<tr class='heading'>\r\n" + 
			"		<th width=10%>S.NO</th>\r\n" + 
			"       <th width=15%>TestCase ID</th>\r\n" +
			"		<th width=50%>TestCase Summary</th>\r\n" + 
			"		<th width=15%>Time</th>\r\n" + 
			"		<th width=25%>Status</th>\r\n" + 
			"	</tr>	";
	
	String SUMMARY_REPORT_PASS = 
			"<tr class='content2'>\r\n" + 
			"		<td align='center'><!--Sno--></td>\r\n" +
			"		<td align='center'>\r\n" + 
			"			<a href='<!--ResultsDetailedPath-->' target='_blank'><!--TestCase ID--></a>\r\n" + 
			"		</td>\r\n" + 			
			"		<td class='justified'><!--TestScriptDescription--></td>\r\n" + 
			"		<td align='center'><!--Time--></td>\r\n" + 
			"		<td class='pass' align='center'>PASS</td>\r\n" + 
			"	</tr>";

	String SUMMARY_REPORT_FAIL = 
			"	<tr class='content2'>\r\n" + 
			"		<td align='center'><!--Sno--></td>\r\n" +
			"		<td align='center'>\r\n" + 
			"			<a href='<!--ResultsDetailedPath-->' target='_blank'><!--TestCase ID--></a>\r\n" + 
			"		</td>\r\n" + 
			"		<td class='justified'><!--TestScriptDescription--></td>\r\n" + 
			"		<td align='center'><!--Time--></td>\r\n" + 
			"		<td class='fail' align='center'>FAIL</td>\r\n" + 
			"	</tr>";
	
	String DETAILED_REPORT = 
			"<html>\r\n" + 
			"	<head>\r\n" + 
			"		<meta charset='UTF-8'>			\r\n" + 
			"		<style type='text/css'> \r\n" + 
			"			body {\r\n" + 
			"				background-color: #FFFFFF;\r\n" + 
			"				font-family: Verdana, Geneva, sans-serif;\r\n" + 
			"				text-align: left;\r\n" + 
			"			}\r\n" + 
			"			small {\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"			}\r\n" + 
			"			table {\r\n" + 
			"				box-shadow: 9px 9px 10px 4px #BDBDBD;\r\n" + 
			"				border: 0px solid #4D7C7B;\r\n" + 
			"				border-collapse: collapse;\r\n" + 
			"				border-spacing: 0px;\r\n" + 
			"				width: 1000px;\r\n" + 
			"				margin-left: auto;\r\n" + 
			"				margin-right: auto;\r\n" + 
			"			}\r\n" + 
			"			tr.heading {\r\n" + 
			"				background-color: #041944;\r\n" + 
			"				color: #FFFFFF;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				background: -o-linear-gradient(bottom, #999999 5%, #000000 100%);\r\n" + 
			"				background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000));\r\n" + 
			"				background: -moz-linear-gradient( center top, #999999 5%, #000000 100%);\r\n" + 
			"				filter: progid: DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);\r\n" + 
			"				background: -o-linear-gradient(top, #999999, 000000);\r\n" + 
			"			}\r\n" + 
			"			tr.subheading {\r\n" + 
			"				background-color: #FFFFFF;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				text-align: left;\r\n" + 
			"			}\r\n" + 
			"			tr.section {\r\n" + 
			"				background-color: #A4A4A4;\r\n" + 
			"				color: #333300;\r\n" + 
			"				cursor: pointer;\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				text-align: left;\r\n" + 
			"				background: -o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);\r\n" + 
			"				background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5));\r\n" + 
			"				background: -moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100%);\r\n" + 
			"				filter: progid: DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);\r\n" + 
			"				background: -o-linear-gradient(top, #56aaff, e5e5e5);\r\n" + 
			"			}\r\n" + 
			"			tr.subsection {\r\n" + 
			"				cursor: pointer;\r\n" + 
			"			}\r\n" + 
			"			tr.content {\r\n" + 
			"				background-color: #FFFFFF;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-size: 0.7em;\r\n" + 
			"				display: table-row;\r\n" + 
			"			}\r\n" + 
			"			tr.content2 {\r\n" + 
			"				background-color: #E1E1E1;\r\n" + 
			"				border: 1px solid #4D7C7B;\r\n" + 
			"				color: #000000;\r\n" + 
			"				font-size: 0.75em;\r\n" + 
			"				display: table-row;\r\n" + 
			"			}\r\n" + 
			"			td,\r\n" + 
			"			th {\r\n" + 
			"				padding: 5px;\r\n" + 
			"				border: 1px solid #4D7C7B;\r\n" + 
			"				text-align: inherit /;\r\n" + 
			"			}\r\n" + 
			"			th.Logos {\r\n" + 
			"				padding: 5px;\r\n" + 
			"				border: 0px solid #4D7C7B;\r\n" + 
			"				text-align: inherit /;\r\n" + 
			"			}\r\n" + 
			"			td.justified {\r\n" + 
			"				text-align: left;\r\n" + 
			"			}\r\n" + 
			"			td.pass {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: green;\r\n" + 
			"			}\r\n" + 
			"			td.fail {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: red;\r\n" + 
			"			}\r\n" + 
			"			td.done,\r\n" + 
			"			td.screenshot {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: black;\r\n" + 
			"			}\r\n" + 
			"			td.debug {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: blue;\r\n" + 
			"			}\r\n" + 
			"			td.warning {\r\n" + 
			"				font-weight: bold;\r\n" + 
			"				color: orange;\r\n" + 
			"			}							\r\n" + 
			"		</style>\r\n" + 
			"	</head>\r\n" + 
			"	\r\n" + 
			"	<body>\r\n" + 
			"	</br>\r\n" + 
			"	<table id='Logos'>\r\n" + 
			"		<thead>\r\n" + 
			"			<tr class='content'>\r\n" + 
			"				<th class='Logos' colspan='2'>\r\n" + 
			"					<img align='right' src=logos\\Cubic.png></img>\r\n" + 
			"				</th>					\r\n" + 
			"			</tr>\r\n" + 
			"		</thead>\r\n" + 
			"	</table>\r\n" + 
			
			"<table id='header'>\r\n" + 
			"		<tr class='heading'>\r\n" + 
			"			<th colspan='4'\r\n" + 
			"				style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> Automation Execution Report Detailed</th>\r\n" + 
			"		</tr>\r\n" + 
			"		<tr class='subheading'>\r\n" + 
			"			<td colspan=4 align='center'><!--TestCaseName--></td>\r\n" + 
			"		</tr>\r\n" + 
			"</table>	"+
			
			"	<table>\r\n" + 
			"		<thead>\r\n" + 
			"			<tr class='heading'>\r\n" + 
			"				<th width=10%>Step No.</th>\r\n" + 
			"				<th width=15%>Steps</th>\r\n" + 
			"				<th width=55%>Details</th>\r\n" + 
			"				<th width=20%>Status</th>	\r\n" + 
			"			</tr>\r\n" + 
			"		</thead>";
	
	String DETAILED_REPORT_PASS = 
			"		<tr class='content2'>\r\n" + 
			"			<td align='center'><!--Step No--></td>\r\n" + 
			"			<td class='justified'><!--Step Name--></td>\r\n" + 
			"			<td class='justified'><!--Description--></td>\r\n" + 
			"  			<!--Status TD-->" +  
			"		</tr>";
	
	String DETAILED_REPORT_FAIL = "<Tr class=\"content2\">\r\n" + 
			"  <Td align='center'><Font color=red face=Verdana size=2><!--Step No--></Font></Td>\r\n" + 
			"  <Td><Font color=red face=Verdana size=2><!--Step Name--></Font></Td>\r\n" + 
			"  <Td><Font color=red face=Verdana size=2><!--Description--></Font></Td>\r\n" + 
			"  <!--Status TD-->" + 
			"</Tr>";
	
	String DETAILED_REPORT_WARNING = 
			"		<tr class='content2'>\r\n" + 
			"			<td align='center'><!--Step No--></td>\r\n" + 
			"			<td class='justified'><!--Step Name--></td>\r\n" + 
			"			<td class='justified'><!--Description--></td>\r\n" + 
			"			<td class='Pass' align='center'><img src='logos\\warning.png' width='18' height='18' title='Warning'/></td>\r\n" + 
			"		</tr>";
	
	String DETAILED_REPORT_FAIL_WITH_SCREENSHOT = "<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=logos/fail.ico  height='18' width='18' title='Failed'/>&nbsp;&nbsp;<a href = \"<!--strErrorPath-->\" target='_blank'><img src=logos/Cam.png  height='18' width='18' title='Click to view results'/></a></td>";
	String DETAILED_REPORT_FAIL_WITHOUT_SCREENSHOT = "<Td align='center'><img src=logos/fail.ico  height='18' width='18' title='Failed'/></Td>";
	
	String DETAILED_REPORT_PASS_WITH_SCREENSHOT = "<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=logos/pass.ico  height='18' width='18' title='Passed'/>&nbsp;&nbsp;<a href = \"<!--strErrorPath-->\" target='_blank'><img src=logos/Cam.png  height='18' width='18' title='Click to view results'/></a></td>";
	String DETAILED_REPORT_PASS_WITHOUT_SCREENSHOT = "<Td align='center'><img src=logos/pass.ico  height='18' width='18' title='Passed'/></Td>";
	
}



<html>
<head>
<title>Data source test</title>
</head>
<script type="text/javascript">
	function clickTomcatJdbcConnectionPoolLinkMultipleTime() {
		for(var index = 0; index < 3; index++ ) {
			document.getElementById('tomcatJdbcTest').click();
		    window.history.back();
		}
	}
	
	function clickc3poConnectionPoolLinkMultipleTime() {
		for(var index = 0; index < 3; index++ ) {
		    document.getElementById('c3poTest').click();
		    window.history.back();
		}
	}
</script>
<body>
	
	<center>
		<h3>Tomcat-Jdbc connection pool</h3>
		<h4>
			<!--<button type="button" onclick="clickTomcatJdbcConnectionPoolLinkMultipleTime();">Click multiple time!</button> <br/>-->
			<a href="tomcatJdbcTest.htm" id="tomcatJdbcTest" >Click to view tomcat-jdbc connection pool error</a>
		</h4>
	</center>
	
	<center>
		<h3>C3PO connection pool</h3>
		<h4>
			<!--<button type="button" onclick="clickc3poConnectionPoolLinkMultipleTime();">Click multiple time!</button> <br/>-->
			<a href="c3poTest.htm" id="c3poTest">Click to view c3po connection pool error</a>
		</h4>
	</center>
</body>
</html>

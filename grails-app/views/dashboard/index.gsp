<html>
	<head>
		<title>Select the application</title>
		<meta name="layout" content="deployer" />
	</head>
	<body>
		
		<div id="pageBody">
            <h1>Select the application</h1>
        
			<g:form controller="dashboard" action="load">
			
				<p>
					<label>Access Key: </label>
					<input type="text" size="80" name="accessKey" />
				</p>
				<p>
					<label>Secret Key: </label>
					<input type="text" size="80" name="secretKey" />
				</p>
				<p>
					<label>Application: </label>
					<select name="app.name">
						<g:each in="${apps}" var="app">
							<option value="${app.name}">${app.name}</option>
						</g:each>
					</select>		
				</p>
				<p>
					<input type="submit" value="continue" />
				</p>

			</g:form>

		</div>
				
	</body>
</html>
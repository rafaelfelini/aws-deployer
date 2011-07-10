<html>
	<head>
		<title>Select the application</title>
		<meta name="layout" content="deployer" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'index.css')}" />
	</head>
	<body>
		
		<div id="index" align="center">
            <h1>AWS Deployer</h1>
        
			<g:form controller="dashboard" action="load">
			
				<fieldset>
					<p>
						<label>Application: </label>
						<select name="app.name">
							<g:each in="${apps}" var="app">
								<option value="${app.name}">${app.name}</option>
							</g:each>
						</select>		
					</p>
					<p>
						<label>Access Key: </label>
						<input type="text" size="80" name="accessKey" />
					</p>
					<p>
						<label>Secret Key: </label>
						<input type="text" size="80" name="secretKey" />
					</p>
					<p>
						<label>AWS Region: </label>
						<select name="region">
							<g:each in="${regions}" var="region">
								<option value="${region.code}">${region.name}</option>
							</g:each>
						</select>		
					</p>
					<p>
						<button type="submit">open application dashboard</button>
					</p>
				</fieldset>
				
			</g:form>

		</div>
				
	</body>
</html>
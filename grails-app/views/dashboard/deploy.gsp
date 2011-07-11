<html>
	<head>
		<title>Dashboard: ${app.name}</title>
		<meta name="layout" content="deployer" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'deploy.css')}" />
	</head>
	<body>
		
		<div id="deploy">
            <h1>${app.name}</h1>
			<h2>Deployment process</h2>

			<p>Launching EC2 instances</p>
			<table>
				<tr>
					<th>Availability Zone</th>
					<th>Instance Id</th>
					<th>Status</th>
				</tr>
				<g:set var="totalRows" value="${0}" />
				<g:each in="${azs}" var="az">				
					<g:each in="${az.instances}" var="instance">
						<g:set var="totalRows" value="${totalRows + 1}" />
						<tr class="${totalRows % 2 == 0 ? 'odd' : 'even'}">
							<td>${az.name}</td>
							<td>${instance.instanceId}</td>
							<td class="starting" id="td-status-${instance.instanceId}">starting...</td>
						</tr>
					</g:each>				
				</g:each>
				
			</table>
			
		</div>
	</body>
</html>
<html>
	<head>
		<title>Dashboard: ${app.name}</title>
		<meta name="layout" content="deployer" />
	</head>
	<body>
		
		<div id="pageBody">
            <h1>Application: ${app.name}</h1>
        	<br />
			<g:each in="${runningInstances}" var="${instance}" status="stts">
				<div id="instance-target-${stts}" class="tooltip-target">${instance.instanceId}</div>
				<div id="instance-content-${stts}" class="tooltip-content">
					<g:render template="instance-details" model="${[instance: instance]}" />
				</div>
			</g:each>
			
		</div>
		
		<script>
			$(document).ready(function(){
				$(".tooltip-target").ezpz_tooltip();
			});
		</script>
		
	</body>
</html>
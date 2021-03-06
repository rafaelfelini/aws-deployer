<html>
	<head>
		<title>Dashboard: ${app.name}</title>
		<meta name="layout" content="deployer" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'dashboard.css')}" />
		<script src="${resource(dir:'js',file:'jquery.numeric.js')}"></script>
		<script src="${resource(dir:'js',file:'jquery.ezpz_tooltip.min.js')}"></script>
	</head>
	<body>
		
		<div id="dashboard">
            <h1>${app.name}</h1>
			<h2>${session.regionName} (${session.region})</h2>

			<div id="left-column" class="column">
				<div class="title">Running Instances</div>
				
				<p id="instanceDetailsInstructions">
					The deployer looks for instances with a tag named <strong>app-name</strong> and value <strong>${app.name}</strong>
				</p>
				
				<g:if test="${runningInstances}">
					<g:each in="${runningInstances}" var="${instance}" status="stts">
						<div class="instance-details">
							<span id="instance-target-${stts}" class="tooltip-target">
								<img src="${resource(dir:'images/skin', file:'information.png')}" />
							</span>
							${instance.instanceId}
						</div>
						<div id="instance-content-${stts}" class="tooltip-content">
							<g:render template="instance-details" model="${[instance: instance]}" />
						</div>
					</g:each>
				</g:if>
				<g:else>
					No instances running for this application in this region.
				</g:else>
								
			</div>
			
			<div id="deploy-column" class="column last">
				<div class="title">Deploy this application</div>
				
				<g:form controller="dashboard" action="deploy">
					<input type="hidden" name="app.name" value="${app.name}" />
					<fieldset>
						<legend>Instance details</legend>

						<div id="deploy-column-left-column">

							<p>
								<label>AMI <span>*</span></label>
								<input name="imageId" type="text" value="${app.imageId}" readonly />
							</p>
							<p>
								<label>Keypair <span>*</span></label>
								<input name="keyName" type="text" value="${app.keyName}" readonly />
							</p>
							<p>
								<label>Type</label>
								<select name="instanceType">
									<optgroup label="Micro Instances">
										<option value="t1.micro" ${(app.instanceType == 't1.micro') ? 'selected' : ''}>Micro Instance</option>
									</optgroup>
									<optgroup label="Standard Instances">
										<option value="m1.small" ${(app.instanceType == 'm1.small') ? 'selected' : ''}>Small Instance</option>
										<option value="m1.large" ${(app.instanceType == 'm1.large') ? 'selected' : ''}>Large Instance</option>
										<option value="m1.xlarge" ${(app.instanceType == 'm1.xlarge') ? 'selected' : ''}>Extra Large Instance</option>
									</optgroup>
									<optgroup label="High-Memory Instances">
										<option value="m2.xlarge" ${(app.instanceType == 'm2.xlarge') ? 'selected' : ''}>High-Memory Extra Large Instance</option>
										<option value="m2.2xlarge" ${(app.instanceType == 'm2.2xlarge') ? 'selected' : ''}>High-Memory Double Extra Large Instance</option>
										<option value="m2.4xlarge" ${(app.instanceType == 'm2.4xlarge') ? 'selected' : ''}>High-Memory Quadruple Extra Large Instance</option>							
									</optgroup>
									<optgroup label="High-CPU Instances">
										<option value="c1.medium" ${(app.instanceType == 'c1.medium') ? 'selected' : ''}>High-CPU Medium Instance</option>
										<option value="c1.xlarge" ${(app.instanceType == 'c1.xlarge') ? 'selected' : ''}>High-CPU Extra Large Instance</option>
									</optgroup>
									<optgroup label="Cluster Compute Instances">
										<option value="cc1.4xlarge" ${(app.instanceType == 'cc1.4xlarge') ? 'selected' : ''}>Cluster Compute Quadruple Extra Large Instance</option>
									</optgroup>
									<optgroup label="Cluster GPU Instances">
										<option value="cg1.4xlarge" ${(app.instanceType == 'cg1.4xlarge') ? 'selected' : ''}>Cluster GPU Quadruple Extra Large Instance</option>
									</optgroup>
								</select>
							</p>
							<p>
								<label>Security Groups <span>*</span></label>
								<input name="securityGroups" type="text" value="${app.securityGroups}" readonly />
							</p>
							<p>
								<label>ELB to attach <span>*</span></label>
								<input name="elb" type="text" value="${app.elb}" readonly />
							</p>
							<p>
								<label>Current instances</label>
								<input class="radio" type="radio" name="behaviour" value="replace" checked />Replace
								<input class="radio" type="radio" name="behaviour" value="keep" />Keep
							</p>
							<p>
								<label class="az">Instances to launch (per availability zone)</label>
							</p>
							
							<g:each in="${availabilityZones}" var="az">
								<p>
									<label style="padding-left: 10px">${az}</label>
									<input class="numeric" type="text" name="ammt_${az}" value="0" />
								</p>
							</g:each>
							

						</div>
						<div id="deploy-column-rigth-column">
							
							<p>
								<label>Update system</label>
								<input class="radio" type="radio" name="update" value="yes" checked />Yes
								<input class="radio" type="radio" name="update" value="no" />No
							</p>

							<p>
								<label>JVM</label>
								<select name="jvm">
									<option value="jre sun" ${(app.container == 'jre sun') ? 'selected' : ''}>JRE Sun</option>
									<option value="openjdk" ${(app.container == 'openjdk') ? 'selected' : ''}>OpenJDK</option>
								</select>
							</p>

							<p>
								<label>Tomcat</label>
								<select name="tomcat">
									<option value="tomcat6" ${(app.container == 'tomcat6') ? 'selected' : ''}>Tomcat 6</option>
								</select>
							</p>

							<p>
								<label>JVM args</label>
								<input name="jvmargs" type="text" value="${app.jvmargs}" />
							</p>
							
							<br class="darkmagic">
							<br />
							
							<fieldset id="application">
								<legend>Application repository details</legend>
								<p>
									<label>Protocol</label>
									<select id="protocol" name="protocol">
										<option value="http">http (regular http download)</option>
									</select>
								</p>
								<p>
									<label>URL</label>
									<input type="text" id="url" name="url" class="appurl" />
								</p>
								<p>
									<label>User</label>
									<input type="text" id="user" name="user" class="appurl" />
								</p>
								<p>
									<label>Password</label>
									<input type="text" id="pass" name="pass" class="appurl" />
								</p>
								<p>
									<span id="appurl-address">http://</span>
								</p>

							</fieldset>
							
						</div>
												
						<p>
							<button type="submit">start deployment process</button>
						</p>
					</fieldset>
					
				</g:form>
				<p class="bottom">
					<span>*</span> You can change in the application configuration.
				</p>
			</div>
						
		</div>
		
		<br class="darkmagic" />
								
		<script>
				
			$(document).ready(function(){
				$(".numeric").numeric();
				$(".tooltip-target").ezpz_tooltip();
				
				$(".appurl").keyup(function() {

					var protocol = $("#protocol").val();
					var user = $("#user").val();
					var pass = $("#pass").val();
					var url = $("#url").val();

					var fullUrl = protocol + "://";
					
					if (user || pass) {
						fullUrl += user + ":" + pass + "@";
					}
					
					fullUrl += url;
					
					$("#appurl-address").html(fullUrl);
				});
			});
		</script>
		
	</body>
</html>
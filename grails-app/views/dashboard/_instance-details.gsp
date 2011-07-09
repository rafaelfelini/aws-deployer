<p>
	Details for <strong>${instance.instanceId}</strong>
</p>
<ul>
	<li><strong>Architecture: </strong>${instance.architecture}</li>
	<li><strong>Image Id: </strong>${instance.imageId}</li>
	<li><strong>Instance Id: </strong>${instance.instanceId}</li>
	<li><strong>Instance Type: </strong>${instance.instanceType}</li>
	<li><strong>Kernel Id: </strong>${instance.kernelId}</li>
	<li><strong>KeyName: </strong>${instance.keyName}</li>
	<li><strong>Zone: </strong>${instance.placement}</li>
	<li><strong>Private DNS name: </strong>${instance.privateDnsName}</li>
	<li><strong>Private Ip Address: </strong>${instance.privateIpAddress}</li>
	<li><strong>Public DNS Name: </strong>${instance.publicDnsName}</li>
	<li><strong>Public Ip Address: </strong>${instance.publicIpAddress}</li>
	<li><strong>Root Device Type: </strong>${instance.rootDeviceType}</li>
	<li><strong>Security Groups: </strong>${instance.securityGroups?.join(', ')}</li>
	<li><strong>State: </strong>${instance.state}</li>
	<li><strong>Tags: </strong>${instance.tags?.join(', ')}</li>
</ul>

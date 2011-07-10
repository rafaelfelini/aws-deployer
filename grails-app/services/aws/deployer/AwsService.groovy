package aws.deployer

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.ec2.model.Filter
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.DescribeInstancesResult
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesRequest

import org.springframework.web.context.request.RequestContextHolder

class AwsService {

	def accessKey
	def secretKey

	static scope = "session"
    static transactional = true

	def ec2Client
	def grailsApplication
	
	def session() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}

    def ec2Client() {
		
		if (!ec2Client) {
			def credentials = new BasicAWSCredentials(session().accessKey, session().secretKey)
			ec2Client = new AmazonEC2Client(credentials)
		}
		return ec2Client
    }

	def applicationInstances(application) {
	
		def instances = []
		
		def describeInstancesRequest = new DescribeInstancesRequest()
		describeInstancesRequest.withFilters(new Filter().withName("tag-key").withValues("app-name"), new Filter().withName("tag-value").withValues(application))
		
		def describeInstancesResult = ec2Client().describeInstances(describeInstancesRequest)
		describeInstancesResult.reservations?.each { reservation -> 
			reservation.instances?.each { i ->
				instances << [	architecture: i.architecture, 
								imageId: i.imageId, 
								instanceId: i.instanceId, 
								instanceType: i.instanceType, 
								kernelId: i.kernelId, 
								keyName: i.keyName, 
								placement: i.placement.availabilityZone, 
								privateDnsName: i.privateDnsName, 
								privateIpAddress: i.privateIpAddress, 
								publicDnsName: i.publicDnsName, 
								publicIpAddress: i.publicIpAddress, 
								rootDeviceType: i.rootDeviceType, 
								securityGroups: i.securityGroups.collect { it.groupName }, 
								state: i.state.name, 
								tags: i.tags.collect { "${it.key}=${it.value}" }]
			}
		}
		
		return instances
	}
	
	def getAvailabilityZones() {
		
		def describeAvailabilityZonesResult = ec2Client().describeAvailabilityZones() 
		return describeAvailabilityZonesResult.availabilityZones.collect { it.zoneName }
	}
	
}

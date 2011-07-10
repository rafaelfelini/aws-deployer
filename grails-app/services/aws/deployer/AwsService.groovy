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

    static transactional = true

	def ec2Client
	def grailsApplication
	
	def endpoints = [
		[code: 'us-east',      name: 'US-East (Northern Virginia)'  , ec2: 'ec2.us-east-1.amazonaws.com',      elb: 'elasticloadbalancing.us-east-1.amazonaws.com'],
		[code: 'us-west',      name: 'US-West (Northern California)', ec2: 'ec2.us-west-1.amazonaws.com',      elb: 'elasticloadbalancing.us-west-1.amazonaws.com'],
		[code: 'eu-west',      name: 'EU (Ireland)'                 , ec2: 'ec2.eu-west-1.amazonaws.com',      elb: 'elasticloadbalancing.eu-west-1.amazonaws.com'],
		[code: 'ap-southeast', name: 'Asia Pacific (Singapore)'     , ec2: 'ec2.ap-southeast-1.amazonaws.com', elb: 'elasticloadbalancing.ap-southeast-1.amazonaws.com'],
		[code: 'ap-northeast', name: 'Asia Pacific (Japan)'         , ec2: 'ec2.ap-northeast-1.amazonaws.com', elb: 'elasticloadbalancing.ap-northeast-1.amazonaws.com']
	]
	
	def session() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}

    def ec2Client() {
		
		if (!ec2Client) {
			def credentials = new BasicAWSCredentials(session().accessKey, session().secretKey)
			ec2Client = new AmazonEC2Client(credentials)
			
			def region = session().region
			if (region) {
				ec2Client.endpoint = endpoints.find { it.code == region }.ec2
			}
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
	
	def getRegionName(region) {
		return endpoints.find { it.code == region }.name
	}
	
}

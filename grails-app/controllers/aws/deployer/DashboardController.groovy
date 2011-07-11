package aws.deployer

import javax.servlet.ServletOutputStream

class DashboardController {
	
	def awsService
	
	//show applications
	def index = {
		
		def regions = []
		regions << [code: 'us-east',      name: 'US-East (Northern Virginia)']
		regions << [code: 'us-west',      name: 'US-West (Northern California)']
		regions << [code: 'eu-west',      name: 'EU (Ireland)']
		regions << [code: 'ap-southeast', name: 'Asia Pacific (Singapore)']
		regions << [code: 'ap-northeast', name: 'Asia Pacific (Japan)']

		[apps: Application.findAll([sort: "name", order: "asc"]), regions: regions]				
	}

    def load = {
	
		def accessKey = params.accessKey
		def secretKey = params.secretKey
		def region    = params.region
		def appName   = params.app.name
		
		if (!region || !accessKey || !secretKey || !appName) {
			flash.message = "Fill in you credentials"
			redirect controller: "dashboard", action: "index"
		}
	
		session.accessKey  = accessKey
		session.secretKey  = secretKey
		session.region     = region
		session.regionName = awsService.getRegionName(params.region)

		def app = Application.findByName(appName)		
		def runningInstances = awsService.applicationInstances(app.name)
		def availabilityZones = awsService.getAvailabilityZones()
		
		session.app = app

		[app: app, runningInstances: runningInstances, availabilityZones: availabilityZones]
	}
	
	def deploy = {
		
		def app = Application.findByName(params.app.name)
		
		def azs = []
		azs << [name: "us-east-1a", instances: [[instanceId:'a1', publicDnsName: 'ec2.34.1827-a1.amazonaws.com'], [instanceId:'a2', publicDnsName: 'ec2.34.1827-a2.amazonaws.com'], [instanceId:'a3', publicDnsName: 'ec2.34.1827-a3.amazonaws.com'], [instanceId:'a4', publicDnsName: 'ec2.34.1827-a4.amazonaws.com'], [instanceId:'a5', publicDnsName: 'ec2.34.1827-a5.amazonaws.com']]]
		azs << [name: "us-east-1b", instances: [[instanceId:'b1', publicDnsName: 'ec2.34.1827-b1.amazonaws.com'], [instanceId:'b2', publicDnsName: 'ec2.34.1827-b2.amazonaws.com'], [instanceId:'b3', publicDnsName: 'ec2.34.1827-b3.amazonaws.com'], [instanceId:'b4', publicDnsName: 'ec2.34.1827-b4.amazonaws.com'], [instanceId:'b5', publicDnsName: 'ec2.34.1827-b5.amazonaws.com']]]
		azs << [name: "us-east-1c", instances: [[instanceId:'c1', publicDnsName: 'ec2.34.1827-c1.amazonaws.com'], [instanceId:'c2', publicDnsName: 'ec2.34.1827-c2.amazonaws.com'], [instanceId:'c3', publicDnsName: 'ec2.34.1827-c3.amazonaws.com'], [instanceId:'c4', publicDnsName: 'ec2.34.1827-c4.amazonaws.com'], [instanceId:'c5', publicDnsName: 'ec2.34.1827-c5.amazonaws.com']]]
		
		[app: app, azs: azs]
	}
}

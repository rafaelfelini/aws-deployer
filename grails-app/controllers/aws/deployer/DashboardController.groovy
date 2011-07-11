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
		azs << [name: "us-east-1a", instances: [[instanceId:'a1'], [instanceId:'a2'], [instanceId:'a3'], [instanceId:'a4'], [instanceId:'a5']]]
		azs << [name: "us-east-1b", instances: [[instanceId:'b1'], [instanceId:'b2'], [instanceId:'b3'], [instanceId:'b4'], [instanceId:'b5']]]
		azs << [name: "us-east-1c", instances: [[instanceId:'c1'], [instanceId:'c2'], [instanceId:'c3'], [instanceId:'c4'], [instanceId:'c5']]]
		
		[app: app, azs: azs]
	}
}

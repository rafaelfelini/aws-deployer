package aws.deployer

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
}

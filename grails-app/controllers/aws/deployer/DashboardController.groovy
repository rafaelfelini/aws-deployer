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
	
		session.accessKey  = params.accessKey
		session.secretKey  = params.secretKey
		session.region     = params.region
		session.regionName = awsService.getRegionName(params.region)

		def app = Application.findByName(params.app.name)		
		def runningInstances = awsService.applicationInstances(app.name)
		def availabilityZones = awsService.getAvailabilityZones()
		
		session.app = app

		[app: app, runningInstances: runningInstances, availabilityZones: availabilityZones]
	}
}

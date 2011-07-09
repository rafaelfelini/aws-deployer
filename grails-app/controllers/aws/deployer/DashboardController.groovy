package aws.deployer

class DashboardController {
	
	def awsService
	
	//show applications
	def index = {
		[apps: Application.findAll([sort: "name", order: "asc"])]				
	}

    def load = {
	
		session.accessKey = params.accessKey
		session.secretKey = params.secretKey

		def app = Application.get(params.app.name)
		def runningInstances = awsService.applicationInstances(app.name)

		[app: app, runningInstances: runningInstances]
	}
}

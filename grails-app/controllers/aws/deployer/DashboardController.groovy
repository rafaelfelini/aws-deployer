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

		def app = Application.findByName(params.app.name)		
		def runningInstances = awsService.applicationInstances(app.name)
		
		session.app = app

		[app: app, runningInstances: runningInstances]
	}
}

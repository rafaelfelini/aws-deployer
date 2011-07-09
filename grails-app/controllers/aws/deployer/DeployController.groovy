package aws.deployer

class DeployController {
	
	def awsService
	
	//show applications
	def index = {
		[apps: Application.findAll([sort: "name", order: "asc"])]				
	}

    def dashboardFlow = {
	
		session.accessKey = params.accessKey
		session.secretKey = params.secretKey

		//select app
		loadAppDashboard {
			action {				
				def app = Application.get(params.app.id)
				def runningInstances = awsService.applicationInstances(app.name)

				[app: app, runningInstances: runningInstances]
			}
			on("success").to "showAppDashboard"
		}
		
		showAppDashboard {
			
		}
				
		showError {
			action {
				log.error "error"
				log.error flow.exception
			}
		}
		
	}
}

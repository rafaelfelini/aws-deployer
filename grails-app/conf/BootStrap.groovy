import aws.deployer.Application

class BootStrap {

    def init = { servletContext ->
	
		def appSimbo = new Application(name: "simbo")
		if (!appSimbo.save()) {
			appSimbo.errors.allErrors.each { log.error it }
		}
		
		def appSimboAdm = new Application(name: "simbo-adm")
		if (!appSimboAdm.save()) {
			appSimboAdm.errors.allErrors.each { log.error it }
		}
		
    }
    def destroy = {
    }
}

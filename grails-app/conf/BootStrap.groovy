import aws.deployer.Application

class BootStrap {

    def init = { servletContext ->
	
		def jvmargs = "-server -Xms384m -Xmx384m -XX:PermSize=96m -XX:MaxPermSize=96m -Djava.awt.headless=true -XX:NewRatio=3 -XX:SurvivorRatio=6 -XX:+UseParallelGC -XX:+CMSClassUnloadingEnabled"
		def appSimbo = new Application(name: "simbo", keyName: "simbo-kp", imageId: "ami-8c1fece5", instanceType: "cg1.4xlarge", instances: 2, securityGroups: "simbo-sg", elb: "simbo-prod", container: "tomcat6", jvmargs: jvmargs, jvm: "jre sun")
		if (!appSimbo.save()) {
			appSimbo.errors.allErrors.each { log.error it }
		}
		
//		def appSimboAdm = new Application(name: "simbo-adm")
//		if (!appSimboAdm.save()) {
//			appSimboAdm.errors.allErrors.each { log.error it }
//		}		
		
    }
    def destroy = {
    }
}

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
		mavenCentral()
    }
    dependencies {
		compile 'javax.mail:mail:1.4.1'
		compile 'commons-httpclient:commons-httpclient:3.1'
		compile 'commons-logging:commons-logging:1.1.1'
		compile 'org.codehaus.jackson:jackson-core-asl:1.7.2'
		compile 'com.amazonaws:aws-java-sdk:1.2.3', {
			excludes 'stax-api', 'jackson-core-asl', 'commons-httpclient', 'commons-logging'
		}
    }
}

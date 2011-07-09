package aws.deployer

class Application implements Serializable {

	String name
	
	String keyName
	String imageId
	String instanceType
	Integer instances
	
	String elb
	String securityGroups
	
	String container
	String jvm
	String jvmargs
			
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
	}
}

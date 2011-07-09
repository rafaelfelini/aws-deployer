package aws.deployer

class Application implements Serializable {

	String name
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
    	name(nullable: false, blank: false)
	}
}

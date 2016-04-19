package com.qiyestore.grails.plugin.admin_web

class TRole {

	String authority
	String comment
	boolean enabled = true
	Date dateCreated
	Date lastUpdated
	Long createdById

	static mapping = {
		version false
		cache true
	}

	static constraints = {
		createdById nullable:true
		authority blank: false, unique: true
		comment nullable:true
	}

	String toString() {
		authority
	}

	/**
	* Judge if the role can access the feature
	* @param feature
	* @return
	*/
   def hasFeature(feature) {
	   if(feature.configAttribute.contains(authority)) {
		   return 'true'
	   }
	   return 'false'
   }
}

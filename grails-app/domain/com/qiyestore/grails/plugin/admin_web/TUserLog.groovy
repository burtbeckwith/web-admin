package com.qiyestore.grails.plugin.admin_web

class TUserLog {
	static final int ACTIONTYPE_DEFAULT = 0
	static final int ACTIONTYPE_LOGIN = 1

	TUser user
	Integer actionType = ACTIONTYPE_DEFAULT
	Date actionTime = new Date()

	static mapping = {
		version false
		cache true
	}

}

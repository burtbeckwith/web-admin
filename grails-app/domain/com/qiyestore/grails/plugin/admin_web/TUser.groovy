package com.qiyestore.grails.plugin.admin_web

class TUser {

	static final int STATUS_NOACTIVE = 0 //未激活
	static final int STATUS_USE = 1//正常使用(已激活)
	static final int STATUS_EXPIRE = -1//到期 开发者账号到期
	static final int STATUS_LOCK = -2//锁定
	static final int STATUS_DELETE = -3//已删除


	String username
	String name
	String password
	String email
	String phone
	String nickname
	String comment
	Integer status=STATUS_NOACTIVE
	boolean enabled = true
	Date dateCreated
	Date lastUpdated
	Long lastLoginTime
	String lastRemoteIp
	String icon
	Long createdById
	Long accountExpiredTime

	static constraints = {

		username blank: false, unique: true
		icon(nullable:true)
		name(nullable:true)
		email blank: false, email: true
		password blank: false
		phone(nullable:true)
		nickname(nullable:true)
		comment(nullable:true)
		status(nullable:true)
		lastRemoteIp(nullable:true)
		lastLoginTime(nullable:true)
		createdById(nullable:true)
		accountExpiredTime(nullable:true)
		dateCreated(nullable:true)
		lastUpdated(nullable:true)
	}

	static mapping = {
		version false
		cache true
	}


	Set<TRole> getAuthorities() {
		TUserTRole.findAllByUser(this).collect { it.role } as Set
	}

	String toString() {
		username
	}

	boolean getAccountEnabled() {

		if (!enabled) {
			return false
		}

		if(accountExpiredTime && System.currentTimeMillis() > accountExpiredTime){
			return false
		}

		return true
	}

}

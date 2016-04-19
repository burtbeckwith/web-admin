package com.qiyestore.grails.plugin.admin_web

class TDashboardBox {

	static final int TYPE_COUNTBOX = 1
	static final int TYPE_LISTBOX = 2

	String image			// 图片
	String title			// 标题
	String descrption		// 描述
	Integer type = TYPE_COUNTBOX	// 类型
	String controllerName	// controllerName
	String actionName		// actionName

	static constraints = {
		image nullable: true
		descrption nullable: true
	}

	static mapping = {
		version false
		cache true
	}

}

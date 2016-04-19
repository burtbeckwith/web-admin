package com.qiyestore.grails.plugin.admin_web

class DashboardController {

	def index() {
		def countBoxes = TDashboardBox.createCriteria().list {
			eq('type', TDashboardBox.TYPE_COUNTBOX)
		}
		def listBoxes = TDashboardBox.createCriteria().list {
			eq('type', TDashboardBox.TYPE_LISTBOX)
		}
		[countBoxes:countBoxes, listBoxes:listBoxes]
	}

	def countAllUser() {
		def userCount = TUser.createCriteria().get {
			eq('enabled', true)
			projections {
				rowCount()
			}
		}
		[userCount: userCount, link: '/user/userList']
	}

	def listSystemMessage() {
		// FOR TEST
	}


	def listUserLog() {
		def userLogs = TUserLog.createCriteria().list(max:5) {
			order('id','desc')
		}
		[userLogs: userLogs]
	}
}
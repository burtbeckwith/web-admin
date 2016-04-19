package com.qiyestore.grails.plugin.admin_web

import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.Session

class UserSecurity {

	//当前用户
	private static final String CURRENT_USER_ID_KEY = "current_user_id"
	//当前用户连接
	private static final String CURRENT_USER_PERMISSION_KEY = "current_user_permission"
	//当前用户角色
	private static final String CURRENT_USER_ROLE_KEY = "current_user_role"

	static void initUser(user){

		def roles = TUserTRole.createCriteria().list {
			createAlias "user", "user"
			createAlias "role", "role"
			eq("user",user)
			projections {
				property("role")
			}
		}
		//将用户角色以及权限添加到session中
		def userRoles = []
		Set<String> userPermi = []

		if(roles){
			roles.each {role->
				userRoles << role.authority
			}

			//println "userRoles="+userRoles;
			def permissons = TRoleTModule.createCriteria().list {
				createAlias "role", "role"
				createAlias "module", "module"
				'in'("role",roles)
				isNotNull("module.permissions")
				projections {
					property("module.permissions")
				}
			}

			//println "permissons="+userRoles;
			//设置权限，使用SET去除重复的链接
			if(permissons){
				permissons.each {per->
					if(per){
						def pary = per.split("\\|\\|")
						pary.each {p->
							userPermi.add(p)
						}
					}
				}
			}
		}

		// add userlog
		new TUserLog(user:user, actionType: TUserLog.ACTIONTYPE_LOGIN, actionTime: new Date()).save(flush:true)

		//初始化session 数据库
		getSession().setAttribute(CURRENT_USER_ID_KEY, user?.id)
		getSession().setAttribute(CURRENT_USER_ROLE_KEY, userRoles)
		getSession().setAttribute(CURRENT_USER_PERMISSION_KEY,userPermi)
	}

	static Long getCurrentUserId(){
		return getSession().getAttribute(CURRENT_USER_ID_KEY) as Long
	}


	/**
	 * 获得当前用户
	 * @return
	 */
	static TUser getCurrentUser(){

		return TUser.get(getCurrentUserId())
	}

	/**
	 * 当前用户是否包含某个角色
	 * @param permission
	 * @return
	 */
	static boolean hasRole(roleName) {

		return getAllRole()?.contains(roleName)
	}


	/**
	 * 包含任意角色
	 * @return
	 */
	static boolean hasAnyRole(roleNames){

		def roles = getAllRole()

		for (role in roleNames) {
			if(roles?.contains(role)){
				return true
			}
		}

		return false
	}


	/**
	 * 获得用户所有的角色
	 * @return
	 */
	static getAllRole(){

		return getSession().getAttribute(CURRENT_USER_ROLE_KEY)
	}


	/**
	 * 获得用户的所有权限
	 * @return
	 */
	static getAllPermission(){

		return getSession().getAttribute(CURRENT_USER_PERMISSION_KEY)
	}


	/**
	 * 获得
	 * @return
	 */
	static Session getSession(){

		return SecurityUtils.subject.session
	}
}


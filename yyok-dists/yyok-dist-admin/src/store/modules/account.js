import {getAccountInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setToken} from '@/utils/auth'

const account = {
  state: {
    token: getToken(),
    user: {},
    roles: [],
    authority: [],//授权
    authentication: [],//身份验证; 认证；鉴定;
    loadResources: false,
    acl: [],
    abac: []
    // 第一次加载菜单时用到
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_ACCOUNT: (state, account) => {
      state.account = account
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_AUTHORITIES: (state, authority) => {//授权
      state.authority = authority
    },
    SET_AUTHENTICATION: (state, authentication) => {//身份验证; 认证；鉴定;
      state.authentication = authentication
    },
    SET_LOAD_RESOURCES: (state, roles) => {//
      state.loadResources = roles.resources
    },
    SET_USERS: (state, user) => {
      state.user = user
    },
    SET_ACL:(state, acl)=> {
      state.acl = acl
    },
    SET_ABAC:(state, abac)=> {
      state.abac = abac
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const rememberMe = userInfo.rememberMe
      return new Promise((resolve, reject) => {
        login(userInfo.username, userInfo.password, userInfo.code, userInfo.uuid).then(res => {
          setToken(res.token, rememberMe)
          commit('SET_TOKEN', res.token)
          setAccountInfo(res.account, commit)
          // 第一次加载菜单时用到， 具体见 src 目录下的 permission.js
          commit('SET_LOAD_RESOURCES', true)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetAccountInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getAccountInfo().then(res => {
          setAccountInfo(res, commit)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(res => {
          logOut(commit)
          resolve()
        }).catch(error => {
          logOut(commit)
          reject(error)
        })
      })
    },

    updateLoadResources({ commit }) {
      return new Promise((resolve, reject) => {
        commit('SET_LOAD_RESOURCES', false)
      })
    }
  }
}

export const logOut = (commit) => {
  commit('SET_TOKEN', '')
  commit('SET_AUTHORITIES', [])
  commit('SET_ACCOUNT', {})
  commit('SET_AUTHORITY', [])
  commit('SET_LOAD_RESOURCES', false)
  commit('SET_ROLES', [])
  commit('SET_USER', {})
  removeToken()
}

export const setAccountInfo = (res, commit) => {

  // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
  if (res.roles.length === 0) {
    commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
  }else {
    commit('SET_ROLES', res.roles)
    //alert(JSON.stringify(res.roles))
  }

  if (res.authority.length === 0) {
    commit('SET_AUTHORITIES', ['AUTHORITIES_SYSTEM_DEFAULT'])
  } else {
    commit('SET_AUTHORITIES', res.authority)
  }
  commit('SET_ACCOUNT', res)
  commit('SET_ACL', res.acl)
  commit('SET_ABAC', res.abac)
  commit('SET_AUTHENTICATION', res.authentication)
  commit('SET_USERS', res.user)
  //alert("users=="+JSON.stringify(res))
}

export default account

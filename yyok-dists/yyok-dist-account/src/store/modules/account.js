import { login, getInfo, logout } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

const account = {
  state: {
    token: getToken(),
    user: {},
    account: {},
    roles: [],
    // 第一次加载菜单时用到
    loadResources: false
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER: (state, user) => {
      state.user = user
    },
    SET_ACCOUNT: (state, account) => {
      state.account = account
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_LOAD_RESOURCES: (state, loadResources) => {
      state.loadResources = loadResources
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
    GetInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
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
  commit('SET_ROLES', [])
  removeToken()
}

export const setAccountInfo = (res, commit) => {
  // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
  if (res.roles.length === 0) {
    commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
  } else {
    commit('SET_ROLES', res.roles)
  }
  commit('SET_ACCOUNT', res)
  commit('SET_USER', res)
}

export default account

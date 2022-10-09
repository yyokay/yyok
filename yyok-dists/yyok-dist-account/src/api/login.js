import request from '@/utils/request'

export function login(username, password, code, uuid) {
  return request({
    url: '/sys/auth/login',
    method: 'post',
    data: {
      username,
      password,
      code,
      uuid
    }
  })
}

export function getInfo() {
  return request({
    url: '/sys/auth/info',
    method: 'get'
  })
}

export function getCodeImg() {
  return request({
    url: '/sys/auth/code',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/sys/auth/logout',
    method: 'delete'
  })
}

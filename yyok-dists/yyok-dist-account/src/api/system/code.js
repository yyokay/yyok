import request from '@/utils/request'

export function resetEmail(data) {
  return request({
    url: 'api/sys/code/resetEmail',
    method: 'post',
    data
  })
}

export function updatePass(pass) {
  return request({
    url: 'api/sys/users/updatePass/' + pass,
    method: 'get'
  })
}

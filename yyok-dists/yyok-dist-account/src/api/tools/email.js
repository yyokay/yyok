import request from '@/utils/request'

export function get() {
  return request({
    url: 'api/sys/email',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'api/sys/email',
    data,
    method: 'put'
  })
}

export function send(data) {
  return request({
    url: 'api/sys/email',
    data,
    method: 'post'
  })
}

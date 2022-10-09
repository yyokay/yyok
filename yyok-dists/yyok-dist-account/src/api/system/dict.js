import request from '@/utils/request'

export function getDicts() {
  return request({
    url: '/api/sys/dict/all',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/api/sys/dict',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/api/sys/dict/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/api/sys/dict',
    method: 'put',
    data
  })
}

export default { add, edit, del }

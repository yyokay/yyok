import request from '@/utils/request'

export function getDepts(params) {
  return request({
    url: 'api/sys/dept',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: 'api/sys/dept',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/dept',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/dept',
    method: 'put',
    data
  })
}

export function getDeptSuperior(data) {
  return request({
    url: 'api/sys/dept',
    method: 'get',
    data
  })
}

export default { add, edit, del, getDepts, getDeptSuperior }

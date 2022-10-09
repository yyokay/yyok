import request from '@/utils/request'

// 获取所有的Role
export function getAll() {
  return request({
    url: 'api/sys/roles/all',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/sys/roles',
    method: 'post',
    data
  })
}

export function get(id) {
  return request({
    url: 'api/sys/roles/' + id,
    method: 'get'
  })
}

export function getLevel() {
  return request({
    url: 'api/sys/roles/level',
    method: 'get'
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/roles',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/roles',
    method: 'put',
    data
  })
}

export function editMenu(data) {
  return request({
    url: 'api/sys/roles/menu',
    method: 'put',
    data
  })
}

export default { add, edit, del, get, editMenu, getLevel }

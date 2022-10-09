import request from '@/utils/request'

export function getResourcesTree() {
  return request({
    url: '/api/sys/resources/tree',
    method: 'get'
  })
}

export function buildResources() {
  return request({
    url: '/api/sys/resources/build',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/api/sys/resources/',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: '/api/sys/resources/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: '/api/sys/resources/',
    method: 'put',
    data
  })
}

export default { add, edit, del, getResourcesTree }

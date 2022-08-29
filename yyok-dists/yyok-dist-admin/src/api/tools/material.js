import request from '@/utils/request'

export function getPage(query) {
  return request({
    url: 'api/sys/material/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: 'api/sys/material',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: 'api/sys/material/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: 'api/sys/material/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: 'api/sys/material',
    method: 'put',
    data: obj
  })
}

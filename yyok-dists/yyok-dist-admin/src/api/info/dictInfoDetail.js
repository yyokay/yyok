import request from '@/utils/request'

export function get(dictInfoName) {
  const params = {
    dictInfoName,
    page: 0,
    size: 9999
  }
  return request({
    url: 'api/info/dictInfoDetail',
    method: 'get',
    params
  })
}

export function getDictInfoMap(dictInfoName) {
  const params = {
    dictInfoName,
    page: 0,
    size: 9999
  }
  return request({
    url: 'api/info/dictInfoDetail/map',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: 'api/info/dictInfoDetail',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/info/dictInfoDetail/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/info/dictInfoDetail',
    method: 'put',
    data
  })
}

export default { add, edit, del }

import request from '@/utils/request'

export function getDictInfos() {
  return request({
    url: 'api/info/dictInfo/all',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/info/dictInfo',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/info/dictInfo/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/info/dictInfo',
    method: 'put',
    data
  })
}

export default { add, edit, del }

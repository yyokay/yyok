import request from '@/utils/request'

export function get(tableName) {
  return request({
    url: 'api/sys/genConfig/' + tableName,
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'api/sys/genConfig',
    data,
    method: 'put'
  })
}

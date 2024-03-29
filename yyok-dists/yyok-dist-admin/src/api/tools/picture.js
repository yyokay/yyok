import request from '@/utils/request'

export function del(ids) {
  return request({
    url: 'api/sys/pictures',
    method: 'delete',
    data: ids
  })
}

export function sync() {
  return request({
    url: 'api/sys/pictures/synchronize',
    method: 'post'
  })
}

export default { del, sync }

import request from '@/utils/request'

export function del(keys) {
  return request({
    url: 'sys/auth/online',
    method: 'delete',
    data: keys
  })
}

export function delT(keys) {
  return request({
    url: 'sys/auth/online/delete',
    method: 'post',
    data: keys
  })
}


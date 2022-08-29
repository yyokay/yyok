import request from '@/utils/request'

export function count() {
  return request({
    url: 'api/sys/visits',
    method: 'post'
  })
}

export function get() {
  return request({
    url: 'api/sys/visits',
    method: 'get'
  })
}

export function getChartData() {
  return request({
    url: 'api/sys/visits/chartData',
    method: 'get'
  })
}



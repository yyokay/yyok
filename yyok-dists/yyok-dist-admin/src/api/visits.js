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

export function gett() {
  return request({
    url: 'api/sys/data/count',
    method: 'get'
  })
}

export function chart() {
  return request({
    url: 'api/sys/data/chart',
    method: 'get'
  })
}

export function getOrderCount() {
  return request({
    url: 'api/sys/yxStoreOrder/orderCount',
    method: 'get'
  })
}

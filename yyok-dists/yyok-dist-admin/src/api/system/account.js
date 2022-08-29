import request from '@/utils/request'
import {encrypt} from '@/utils/rsaEncrypt'

export function add(data) {
  return request({
    url: 'api/sys/accounts',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/accounts',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/accounts',
    method: 'put',
    data
  })
}

export function editAccount(data) {
  return request({
    url: 'api/sys/accounts/center',
    method: 'put',
    data
  })
}

export function updatePass(account) {
  const data = {
    oldPass: encrypt(account.oldPass),
    newPass: encrypt(account.newPass)
  }
  return request({
    url: 'api/sys/accounts/updatePass/',
    method: 'post',
    data
  })
}

export function updateEmail(form) {
  const data = {
    password: encrypt(form.pass),
    email: form.email
  }
  return request({
    url: 'api/sys/accounts/updateEmail/' + form.code,
    method: 'post',
    data
  })
}

export function getAccounts() {
  return request({
    url: '/api/sys/accounts/getAccounts',
    method: 'get'
  })
}

export default { add, edit, del }


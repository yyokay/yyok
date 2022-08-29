import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    const { value } = binding
    const authority = store.getters && store.getters.authority
    if (value && value instanceof Array && value.length > 0) {
      const permissionAuthority = value

      const hasPermission = authority.some(role => {
        return permissionAuthority.includes(role)
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`使用方式： v-permission="['admin','editor']"`)
    }
  }
}

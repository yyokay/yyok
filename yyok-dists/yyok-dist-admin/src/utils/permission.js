import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const authorities = store.getters && store.getters.authority
    const permissionAuthorities = value

    const hasPermission = authorities.some(authority => {
      return permissionAuthorities.includes(authority)
    })

    if (!hasPermission) {
      return false
    }
    return true
  } else {
    console.error(`need Authorities! Like v-permission="['admin','editor']"`)
    return false
  }
}

import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../layout/index'

Vue.use(Router)

export const constantRouterMap = [
  { path: '/login',
    meta: { title: '登录', noCache: true },
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/features/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/features/401'], resolve),
    hidden: true
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: (resolve) => require(['@/views/features/redirect'], resolve)
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: (resolve) => require(['@/views/dashboard/home'], resolve),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'index', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/shop',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'workspace',
        component: (resolve) => require(['@/views/generator/config'], resolve),
        name: '我的',
        meta: { title: '我的', icon: 'index', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/dev',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'coding',
        component: (resolve) => require(['@/views/generator/index'], resolve),
        name: ' 开发',
        meta: { title: ' 开发', icon: 'index', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/account',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'center',
        component: (resolve) => require(['@/views/system/account/center'], resolve),
        name: '账户管理中心',
        meta: { title: '账户管理中心'}
      }
    ]
  }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

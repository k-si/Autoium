import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  {
    path: '/api',
    component: Layout,
    redirect: '/api/manage',
    name: '接口管理',
    meta: { title: '接口管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: 'Table',
        hidden: true,
        component: () => import('@/views/table/index'),
        meta: { title: '用例管理', icon: 'table' }
      },
      {
        path: 'manage',
        name: 'Manage',
        component: () => import('@/views/apiManage/index'),
        meta: { title: '接口测试', icon: 'example' }
      }
    ]
  },
  {
    path: '/case',
    component: Layout,
    redirect: '/case/table',
    name: '用例管理',
    meta: { title: '用例管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: '用例列表',
        component: () => import('@/views/caseManage/list'),
        meta: { title: '用例列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加用例',
        component: () => import('@/views/caseManage/save'),
        meta: { title: '添加用例', icon: 'form' }
      },
      {
        path: 'edit/:id', // 添加和修改想用同一个页面,:id(相当于是个占位符,里面要传参数,在url里面进行传参)
        name: '编辑用例',
        component: () => import('@/views/caseManage/save'),
        meta: { title: '编辑用例', noCache: true },
        hidden: true // 不显示此路由
      }
    ]
  },
  {
    path: '/param',
    component: Layout,
    name: '参数维护',
    meta: { title: '参数维护', icon: 'example' },
    children: [
      {
        path: '/list',
        name: '参数列表',
        component: () => import('@/views/param/list'),
        meta: { title: '参数列表', icon: 'table' }
      },
      {
        path: '/add',
        name: '新增参数',
        component: () => import('@/views/param/add'),
        meta: { title: '新增参数', icon: 'table' }
      },
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

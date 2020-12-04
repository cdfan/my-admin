import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * 预制路由
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/common/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/common/login/index'),
    hidden: true,
    meta: { noCache: true }
  },
  {
    path: '/404',
    component: () => import('@/views/common/error-page/404/index'),
    hidden: true,
    meta: { title: '404', noCache: true }
  },
  {
    path: '/401',
    component: () => import('@/views/common/error-page/401/index'),
    hidden: true,
    meta: { title: '401', noCache: true }
  },
  { // 首页
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/common/dashboard/index'),
        name: 'dashboard',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  // 个人信息
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/common/profile/index'),
        name: 'profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// 重置路由，只留下不需要权限的路由
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist
// 全局路由拦截器
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      // 判断是否已经设置了路由以及权限
      const hasPermissions = store.getters.permission_routes && store.getters.permission_routes.length > 0
      if (hasPermissions) {
        next()
      } else {
        try {
          // 获取用户所拥有的路由
          const accessRoutes = await store.dispatch('permission/generateRoutes')
          // dynamically add accessible routes
          router.addRoutes(accessRoutes)

          next({ ...to, replace: true })
        } catch (error) {
          console.error(error)
          // 登出，并重定向到登录页面
          await store.dispatch('user/logout')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

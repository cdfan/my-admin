import Vue from 'vue'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import './icons' // icon
import './permission' // permission control

import * as filters from './filters' // 全局 filters
import * as utils from './utils' // 全局 method

import waves from '@/directive/waves' //  按钮水波纹
import permission from '@/directive/permission' //  按钮权限
import defaultSettings from '@/settings'
import VueAMap from 'vue-amap' // 高德地图

// if (process.env.NODE_ENV === 'production') {
// const { mockXHR } = require('../mock')
// mockXHR()
// }
Vue.use(Element, {
  size: defaultSettings.themeSize // set element-ui default size
})
// 设置全局指令和组件
Vue.use(waves)
Vue.use(permission)

// 设置全局方法
Object.keys(utils).forEach(key => {
  Vue.prototype[key] = utils[key]
})

// 地图初始化
Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
  key: 'xxxxxxxxxxx',
  plugin: ['Scale', 'ToolBar', 'Geolocation'],
  v: '1.4.4'
})

// 设置全局过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// 设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

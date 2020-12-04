// 菜单相关api
import request from '@/utils/request'

// 获取用户拥有的路由信息
export function getUserRoutes() {
  return request({
    url: '/menu/getUserRoutes',
    method: 'get'
  })
}

// 获取菜单
export function menuInfo(query) {
  return request({
    url: '/menu/menuInfo',
    method: 'get',
    params: query
  })
}
// 获取所有菜单节点,并返回树结构
export function getMenuNodes(query) {
  return request({
    url: '/menu/getMenuNodes',
    method: 'get',
    params: query
  })
}

// 新增菜单
export function createMenu(data) {
  return request({
    url: '/menu/menuInfo',
    method: 'post',
    data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/menu/menuInfo',
    method: 'put',
    data
  })
}

// 删除菜单
export function deleteMenu(id) {
  return request({
    url: '/menu/menuInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询菜单数量
export function countMenu(query) {
  return request({
    url: '/menu/menuInfoCount',
    method: 'get',
    params: query
  })
}

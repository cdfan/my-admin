// 角色相关api
import request from '@/utils/request'

// 获取角色
export function roleInfo(query) {
  return request({
    url: '/role/roleInfoPage',
    method: 'get',
    params: query
  })
}

// 创建角色
export function createRole(data) {
  return request({
    url: '/role/roleInfo',
    method: 'post',
    data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/role/roleInfo',
    method: 'put',
    data
  })
}

// 删除角色
export function deleteRole(id) {
  return request({
    url: '/role/roleInfo/' + id,
    method: 'delete'
  })
}

// 查询角色所拥有的菜单id
export function getHasMenuId(roleId) {
  return request({
    url: '/role/getHasMenuId/' + roleId,
    method: 'get'
  })
}

// 查询所有角色信息
export function getRoleList() {
  return request({
    url: '/role/roleInfo',
    method: 'get'
  })
}

// 根据条件查询角色数量
export function countRole(query) {
  return request({
    url: '/role/roleInfoCount',
    method: 'get',
    params: query
  })
}

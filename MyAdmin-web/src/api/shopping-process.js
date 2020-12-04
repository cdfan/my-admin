// 购物流程相关api
import request from '@/utils/request'

// 获取购物流程
export function shoppingProcessInfo(query) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfoPage',
    method: 'get',
    params: query
  })
}

// 根据id获取数据
export function getShoppingProcess(id) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfo/' + id,
    method: 'get'
  })
}

// 新增购物流程
export function createShoppingProcess(data) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfo',
    method: 'post',
    data
  })
}

// 修改购物流程
export function updateShoppingProcess(data) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfo',
    method: 'put',
    data
  })
}

// 删除购物流程
export function deleteShoppingProcess(id) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询购物流程数据数量
export function countShoppingProcess(query) {
  return request({
    url: '/shoppingProcess/shoppingProcessInfoCount',
    method: 'get',
    params: query
  })
}

// 任务处理
export function handleTask(data) {
  return request({
    url: '/shoppingProcess/handleTask',
    method: 'post',
    data
  })
}

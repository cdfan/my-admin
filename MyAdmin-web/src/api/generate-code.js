// 代码生成演示相关api
import request from '@/utils/request'

// 获取代码生成演示
export function generateCodeInfo(query) {
  return request({
    url: '/generateCode/generateCodeInfoPage',
    method: 'get',
    params: query
  })
}

// 新增代码生成演示
export function createGenerateCode(data) {
  return request({
    url: '/generateCode/generateCodeInfo',
    method: 'post',
    data
  })
}

// 修改代码生成演示
export function updateGenerateCode(data) {
  return request({
    url: '/generateCode/generateCodeInfo',
    method: 'put',
    data
  })
}

// 删除代码生成演示
export function deleteGenerateCode(id) {
  return request({
    url: '/generateCode/generateCodeInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询代码生成演示数据数量
export function countGenerateCode(query) {
  return request({
    url: '/generateCode/generateCodeInfoCount',
    method: 'get',
    params: query
  })
}

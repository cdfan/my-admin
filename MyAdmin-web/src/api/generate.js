// 代码生成配置相关api
import request from '@/utils/request'

// 获取代码生成配置
export function generateInfo(query) {
  return request({
    url: '/generate/generateInfoPage',
    method: 'get',
    params: query
  })
}

// 新增代码生成配置
export function createGenerate(data) {
  return request({
    url: '/generate/generateInfo',
    method: 'post',
    data
  })
}

// 修改代码生成配置
export function updateGenerate(data) {
  return request({
    url: '/generate/generateInfo',
    method: 'put',
    data
  })
}

// 删除代码生成配置
export function deleteGenerate(id) {
  return request({
    url: '/generate/generateInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询代码生成配置数据数量
export function countGenerate(query) {
  return request({
    url: '/generate/generateInfoCount',
    method: 'get',
    params: query
  })
}

// 查询数据库中的表信息
export function getTablesInfo() {
  return request({
    url: '/generate/getTablesInfo',
    method: 'get'
  })
}

// 下载以及生成代码
export function downloadCode(query) {
  return request({
    url: '/generate/downloadCode',
    method: 'get',
    responseType: 'blob',
    params: query
  })
}

// 预览及生成代码
export function previewCode(query) {
  return request({
    url: '/generate/previewCode',
    method: 'get',
    params: query
  })
}

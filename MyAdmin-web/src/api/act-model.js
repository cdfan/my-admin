// 流程模型相关api
import request from '@/utils/request'

// 获取流程模型
export function actModelInfo(query) {
  return request({
    url: '/actModel/actModelInfoPage',
    method: 'get',
    params: query
  })
}

// 新增流程模型
export function createActModel(data) {
  return request({
    url: '/actModel/actModelInfo',
    method: 'post',
    data
  })
}

// 修改流程模型
export function updateActModel(data) {
  return request({
    url: '/actModel/actModelInfo',
    method: 'put',
    data
  })
}

// 删除流程模型
export function deleteActModel(id) {
  return request({
    url: '/actModel/actModelInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询流程模型数据数量
export function countActModel(query) {
  return request({
    url: '/actModel/actModelInfoCount',
    method: 'get',
    params: query
  })
}

// 根据模型id生成并获取对应的bpmn.xml
export function getModelFile(id) {
  return request({
    url: '/actModel/getModelFile/' + id,
    method: 'get'
  })
}

// 根据模型id生成并获取对应的流程图
export function getModelImage(id) {
  return request({
    url: '/actModel/getModelImage/' + id,
    method: 'get'
  })
}

// 下载模型资源
export function downloadResource(id) {
  return request({
    url: '/actModel/downloadResource/' + id,
    method: 'get',
    responseType: 'blob'
  })
}

// 部署模型
export function deploymentModel(id) {
  return request({
    url: '/actModel/deploymentModel/' + id,
    method: 'get'
  })
}

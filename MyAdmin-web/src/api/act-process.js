// 流程模型相关api
import request from '@/utils/request'

// 获取流程数据
export function actProcessInfo(query) {
  return request({
    url: '/actProcess/actProcessInfoPage',
    method: 'get',
    params: query
  })
}

// 修改流程状态
export function updateState(data) {
  return request({
    url: '/actProcess/updateProcessState',
    method: 'put',
    data
  })
}

// 删除流程
export function deleteActProcess(deploymentId) {
  return request({
    url: '/actProcess/actProcessInfo/' + deploymentId,
    method: 'delete'
  })
}

// 获取流程的bpmn.xml文件
export function getProcessFile(id) {
  return request({
    url: '/actProcess/getProcessFile/' + id,
    method: 'get'
  })
}

// 获取流程的流程图
export function getProcessImage(id) {
  return request({
    url: '/actProcess/getProcessImage/' + id,
    method: 'get'
  })
}

// 下载流程资源
export function downloadResource(id) {
  return request({
    url: '/actProcess/downloadResource/' + id,
    method: 'get',
    responseType: 'blob'
  })
}

// 流程关联业务
export function businessRelation(data) {
  return request({
    url: '/actProcess/businessRelation',
    method: 'put',
    data
  })
}

// 根据流程添加流程模型
export function convertModel(id) {
  return request({
    url: '/actProcess/convertModel/' + id,
    method: 'get'
  })
}

// 查询所有流程定义信息
export function getProcDefList() {
  return request({
    url: '/actProcess/getProcDefList',
    method: 'get'
  })
}
// 根据菜单编码获取关联的流程定义
export function getRelationProcDef(menuCode) {
  return request({
    url: '/actProcess/getRelationProcDef/' + menuCode,
    method: 'get'
  })
}

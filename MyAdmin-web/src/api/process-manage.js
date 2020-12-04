// 工作流活动流程管理api
import request from '@/utils/request'

// 分页获取运行中流程
export function processRuningInfo(query) {
  return request({
    url: '/processManage/processRuningInfoPage',
    method: 'get',
    params: query
  })
}

// 修改运行中流程的流程状态（挂起或激活）
export function updateProcState(procInstId) {
  return request({
    url: '/processManage/updateProcState/' + procInstId,
    method: 'get'
  })
}

// 分页获取以结束流程
export function processFinishInfo(query) {
  return request({
    url: '/processManage/processFinishInfoPage',
    method: 'get',
    params: query
  })
}

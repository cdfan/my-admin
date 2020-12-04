// 流程公共api
import request from '@/utils/request'

// 根据流程实例id获取当前任务的下一任务办理人
export function nextTaskUser(id) {
  return request({
    url: '/workflowHandle/nextTaskUser/' + id,
    method: 'get'
  })
}

// 根据流程实例id获取当前流程执行任务记录
export function historyTask(id) {
  return request({
    url: '/workflowHandle/historyTask/' + id,
    method: 'get'
  })
}

// 根据流程实例id获取流转轨迹高亮流程图
export function getHighlightTrackImage(id) {
  return request({
    url: '/workflowHandle/getHighlightTrackImage/' + id,
    method: 'get'
  })
}

// 请假审批流演示相关api
import request from '@/utils/request'

// 获取请假审批流演示
export function leaveProcessInfo(query) {
  return request({
    url: '/leaveProcess/leaveProcessInfoPage',
    method: 'get',
    params: query
  })
}

// 新增请假审批流演示
export function createLeaveProcess(data) {
  return request({
    url: '/leaveProcess/leaveProcessInfo',
    method: 'post',
    data
  })
}

// 修改请假审批流演示
export function updateLeaveProcess(data) {
  return request({
    url: '/leaveProcess/leaveProcessInfo',
    method: 'put',
    data
  })
}

// 删除请假审批流演示
export function deleteLeaveProcess(id) {
  return request({
    url: '/leaveProcess/leaveProcessInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询请假审批流演示数据数量
export function countLeaveProcess(query) {
  return request({
    url: '/leaveProcess/leaveProcessInfoCount',
    method: 'get',
    params: query
  })
}

// 根据id获取数据
export function getLeaveProcess(id) {
  return request({
    url: '/leaveProcess/leaveProcessInfo/' + id,
    method: 'get'
  })
}

// 任务处理
export function handleTask(data) {
  return request({
    url: '/leaveProcess/handleTask',
    method: 'post',
    data
  })
}

// 撤销请假审批流演示
export function revokeLeaveProcess(id) {
  return request({
    url: '/leaveProcess/revokeLeaveProcess/' + id,
    method: 'delete'
  })
}

// 提交请假审批流演示
export function commitLeaveProcess(data) {
  return request({
    url: '/leaveProcess/commitLeaveProcess',
    method: 'post',
    data
  })
}


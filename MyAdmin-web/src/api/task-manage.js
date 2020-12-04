// 工作流任务管理api
import request from '@/utils/request'

// 分页获取待办任务
export function taskTodoInfo(query) {
  return request({
    url: '/taskManage/taskTodoInfoPage',
    method: 'get',
    params: query
  })
}

// 分页获取待办任务
export function taskDoneInfo(query) {
  return request({
    url: '/taskManage/taskDoneInfoPage',
    method: 'get',
    params: query
  })
}

// 获取待办任务数量
export function countTaskTodo(query) {
  return request({
    url: '/taskManage/countTaskTodo',
    method: 'get'
  })
}

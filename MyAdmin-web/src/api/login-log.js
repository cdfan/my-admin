// 登录日志相关api
import request from '@/utils/request'

// 获取登录日志
export function loginLogInfo(query) {
  return request({
    url: '/loginLog/loginLogInfoPage',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function deleteLoginLog(ids) {
  return request({
    url: '/loginLog/loginLogInfo/' + ids,
    method: 'delete'
  })
}

// 当前用户登录日志查询
export function userLoginLog() {
  return request({
    url: '/loginLog/userLoginLog',
    method: 'get'
  })
}

// 日志统计
export function logStatistics() {
  return request({
    url: '/logStatistics/logStatisticsInfo',
    method: 'get'
  })
}

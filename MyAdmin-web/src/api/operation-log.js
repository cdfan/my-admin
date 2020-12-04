// 操作日志相关api
import request from '@/utils/request'

// 获取操作日志
export function operationLogInfo(query) {
  return request({
    url: '/operationLog/operationLogInfoPage',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function deleteOperationLog(ids) {
  return request({
    url: '/operationLog/operationLogInfo/' + ids,
    method: 'delete'
  })
}

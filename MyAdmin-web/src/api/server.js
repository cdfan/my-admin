// 服务器信息相关api
import request from '@/utils/request'

// 获取服务器信息
export function serverInfo() {
  return request({
    url: '/server/serverInfo',
    method: 'get'
  })
}

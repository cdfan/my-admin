// 字典相关api
import request from '@/utils/request'

// 获取字典
export function dictInfo(query) {
  return request({
    url: '/dict/dictInfoPage',
    method: 'get',
    params: query
  })
}

// 新增字典
export function createDict(data) {
  return request({
    url: '/dict/dictInfo',
    method: 'post',
    data
  })
}

// 修改字典
export function updateDict(data) {
  return request({
    url: '/dict/dictInfo',
    method: 'put',
    data
  })
}

// 删除字典
export function deleteDict(id) {
  return request({
    url: '/dict/dictInfo/' + id,
    method: 'delete'
  })
}

// 根据条件查询字典数量
export function countDict(query) {
  return request({
    url: '/dict/dictInfoCount',
    method: 'get',
    params: query
  })
}

// 根据字典编码查询字典详情
export function getDictDetails(code) {
  return request({
    url: '/dict/getDictDetails/' + code,
    method: 'get'
  })
}

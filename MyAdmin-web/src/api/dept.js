// 部门相关api
import request from '@/utils/request'

// 获取部门
export function deptInfo(query) {
  return request({
    url: '/dept/deptInfo',
    method: 'get',
    params: query
  })
}

// 新增部门
export function createDept(data) {
  return request({
    url: '/dept/deptInfo',
    method: 'post',
    data
  })
}
// 修改部门
export function updateDept(data) {
  return request({
    url: '/dept/deptInfo',
    method: 'put',
    data
  })
}
// 删除部门
export function deleteDept(id) {
  return request({
    url: '/dept/deptInfo/' + id,
    method: 'delete'
  })
}

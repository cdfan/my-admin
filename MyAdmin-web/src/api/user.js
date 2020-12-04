// 用户相关api
import request from '@/utils/request'

// 登录
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

// 登出
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function loginCode(data) {
  return request({
    url: '/loginCode',
    method: 'post',
    data
  })
}

// 验证验证码是否正确
export function verifyLoginCode(data) {
  return request({
    url: '/verifyLoginCode',
    method: 'post',
    data
  })
}

/**
 * 用户管理操作
 */

// 分页查询用户
export function userInfo(query) {
  return request({
    url: '/user/userInfoPage',
    method: 'get',
    params: query
  })
}

// 根据用户账号获取当前用户相关信息
export function userData(query) {
  return request({
    url: '/user/userData',
    method: 'get',
    params: query
  })
}

// 新增用户
export function createUser(data) {
  return request({
    url: '/user/userInfo',
    method: 'post',
    data
  })
}

// 修改用户及其用户相关信息
export function updateUser(data) {
  return request({
    url: '/user/userInfo',
    method: 'put',
    data
  })
}

// 删除用户及用户相关信息
export function deleteUser(id) {
  return request({
    url: '/user/userInfo/' + id,
    method: 'delete'
  })
}

// 根据条件获取用户数量
export function countUser(query) {
  return request({
    url: '/user/userInfoCount',
    method: 'get',
    params: query
  })
}

// 修改用户基本信息
export function updateUserBaseInfo(data) {
  return request({
    url: '/user/updateUserBaseInfo',
    method: 'put',
    data
  })
}

// 发送邮箱验证码
export function sendEmailCode(query) {
  return request({
    url: '/user/sendEmailCode',
    method: 'get',
    params: query
  })
}

// 验证验证码是否正确
export function userEmailValidate(data) {
  return request({
    url: '/user/userEmailValidate',
    method: 'post',
    data
  })
}

// 修改用户密码
export function updatePassword(data) {
  return request({
    url: '/user/updatePassword',
    method: 'put',
    data
  })
}

// 验证密码是否正确
export function validatePassword(data) {
  return request({
    url: '/user/validatePassword',
    method: 'post',
    data
  })
}

// 获取所有用户信息
export function allUserInfo() {
  return request({
    url: '/user/userInfo',
    method: 'get'
  })
}

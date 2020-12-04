// 全国地址相关api
import request from '@/utils/request'

// 获取用于级联选择的全国省份
export function getProvinceCascader() {
  return request({
    url: '/addrProvince/getProvinceCascader',
    method: 'get'
  })
}

// 通过省份编码获取该省份下的所有城市用于级联选择
export function getCityCascader(provinceCode) {
  return request({
    url: '/addrCity/getCityCascader/' + provinceCode,
    method: 'get'
  })
}

// 通过城市编码获取该城市下的所有地区用于级联选择
export function getAreaCascader(cityCode) {
  return request({
    url: '/addrArea/getAreaCascader/' + cityCode,
    method: 'get'
  })
}

// 通过地区编码获取该地区下的所有街道用于级联选择
export function getStreetCascader(areaCode) {
  return request({
    url: '/addrStreet/getStreetCascader/' + areaCode,
    method: 'get'
  })
}


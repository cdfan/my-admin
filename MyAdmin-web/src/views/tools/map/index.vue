<template>
  <div class="app-container">
    <el-amap-search-box class="search-box" :search-option="searchOption" :on-search-result="onSearchResult"></el-amap-search-box>
    <el-amap ref="map" vid="amap"  :center="center" :zoom="zoom" class="amap" :plugin="plugins">
      <el-amap-marker vid="marker" :position="center" :label="markLabel"></el-amap-marker>
    </el-amap>
    <div class="position-info">
      <svg-icon class-name="address-icon" icon-class="address" />
      <span class="position-message">{{ positionMessage }}</span>
    </div>
  </div>
</template>

<script>

// 地图
export default {
  name: 'amap',
  data() {
    const self = this
    return {
      plugins: [
        'Scale',
        'ToolBar',
        {
          pName: 'Geolocation',
          events: {
            init(o) {
              // o 是高德地图定位插件实例
              self.geolocationComponent = o
              o.getCurrentPosition()
            },
            complete(result) {
              self.center = [result.position.lng, result.position.lat]
              self.positionMessage = '精确定位成功，当前位置：' + result.formattedAddress +
              '， 经度：' + result.position.lng + '，纬度：' + result.position.lat
            },
            error(result) {
              self.positionMessage = '精确定位失败，'
              // 进行城市定位
              self.geolocationComponent.getCityInfo((cityStatus, cityResult) => {
                if (cityStatus === 'error') {
                  self.positionMessage = self.positionMessage + '城市定位失败，终止定位'
                } else {
                  self.center = [cityResult.center[0], cityResult.center[1]]
                  self.positionMessage = self.positionMessage + '城市定位成功，当前位置：' + cityResult.province + ' ' + (cityResult.city ? cityResult.city : '') +
                  '， 经度：' + cityResult.center[0] + '，纬度：' + cityResult.center[1]
                }
              })
            }
          }
        }
      ],
      zoom: 12,
      center: [114.085947, 22.547],
      markLabel: { content: '当前位置', offset: [-15, -25] },
      searchOption: {
        city: '全国',
        citylimit: true
      },
      geolocationComponent: undefined,
      positionMessage: '正在定位'
    }
  },
  methods: {
    onSearchResult(pois) {
      if (pois.length > 0) {
        const { lng, lat, name } = pois[0]
        this.center = [lng, lat]
        this.positionMessage = '当前搜索位置：' + name + '， 经度：' + lng + '，纬度：' + lat
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container{
  height: calc(100vh - 85px);
  .amap{
    height: 98%;
  }
  .position-info{
    margin-top: 5px;
    color: #606266;
    .position-message{
      margin-left: 5px;
    }
  }
  .search-box{
    position: absolute;
    top: 120px;
    right: 40px;
  }
  .address-icon{
    color: #409EFF;
  }
}
</style>

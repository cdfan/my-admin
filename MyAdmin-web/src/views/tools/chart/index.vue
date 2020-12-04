<template>
  <div class="app-container">
      <el-row :gutter="10" class="rowStyle">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>键盘图表</span>
            </div>
            <div class="chartBody">
              <Keyboard :id="'Keyboard'" :class="'Keyboard'" height="100%" width="100%" />
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>3D地球</span>
            </div>
            <div class="chartBody">
              <Earth height="100%" width="100%"/>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10" class="rowStyle">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card>
            <div slot="header" class="clearfix">
              <span>折线图</span>
            </div>
            <div class="chartBodyBig">
              <LineMarker :id="'LineMarker'" :class="'LineMarker'" height="100%" width="100%"/>
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>混合图表</span>
            </div>
            <div class="chartBodyBig">
              <MixChart :id="'MixChart'" :class="'MixChart'" height="100%" width="100%"/>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card>
            <div slot="header" class="clearfix">
              <span>雷达图</span>
            </div>
            <div class="chartBody">
              <RaddarChart :id="'RaddarChart'" :class="'RaddarChart'" height="100%" width="100%"/>
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>饼图</span>
            </div>
            <div class="chartBody">
              <PieChart :id="'PieChart'" :class="'PieChart'" height="100%" width="100%"/>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import Keyboard from '@/components/Charts/Keyboard'
import LineMarker from '@/components/Charts/LineMarker'
import MixChart from '@/components/Charts/MixChart'
import Earth from '@/components/Charts/Earth'
import RaddarChart from '@/components/Charts/RaddarChart'
import PieChart from '@/components/Charts/PieChart'

// echarts 图表
export default {
  name: 'chart',
  components: {
    Keyboard,
    LineMarker,
    MixChart,
    Earth,
    RaddarChart,
    PieChart
  },
  data() {
    return {
    }
  }
}
</script>

<style lang="scss" scoped>
.rowStyle{
  margin-bottom: 10px;
}
.chartBody{
   height: 50vh;
}
.chartBodyBig{
  height: 60vh;
}

</style>

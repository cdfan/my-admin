<template>
  <div class="drawer-container">
    <div>
      <h3 class="drawer-title">布局配置</h3>

      <div class="drawer-item">
        <span>主题色</span>
        <theme-picker style="float: right;height: 26px;margin: -3px 8px 0 0;" @change="themeChange" />
      </div>

      <div class="drawer-item">
        <span>开启标签页</span>
        <el-switch v-model="tagsView" class="drawer-switch" />
      </div>

      <!-- <div class="drawer-item">
        <span>固定头部区域</span>
        <el-switch v-model="fixedHeader" class="drawer-switch" />
      </div> -->

      <div class="drawer-item">
        <span>显示Logo</span>
        <el-switch v-model="sidebarLogo" class="drawer-switch" />
      </div>

    </div>
  </div>
</template>

<script>
import ThemePicker from '@/components/ThemePicker'
import { updateUserBaseInfo } from '@/api/user'

export default {
  components: { ThemePicker },
  data() {
    return {}
  },
  computed: {
    fixedHeader: {
      get() {
        return this.$store.state.settings.fixedHeader
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'fixedHeader',
          value: val
        })
      }
    },
    tagsView: {
      get() {
        return this.$store.state.settings.tagsView
      },
      set(val) {
        const tagsView = val ? '1' : '0'
        updateUserBaseInfo({ 'tagsView': tagsView })
          .then(res => {
            this.$store.dispatch('settings/changeSetting', {
              key: 'tagsView',
              value: val
            })
          }).catch(() => {
          })
      }
    },
    sidebarLogo: {
      get() {
        return this.$store.state.settings.sidebarLogo
      },
      set(val) {
        const sidebarLogo = val ? '1' : '0'
        updateUserBaseInfo({ 'sidebarLogo': sidebarLogo })
          .then(res => {
            this.$store.dispatch('settings/changeSetting', {
              key: 'sidebarLogo',
              value: val
            })
          }).catch(() => {
          })
      }
    }
  },
  methods: {
    themeChange(val) {
      if (this.$store.state.settings.theme !== val) {
        updateUserBaseInfo({ 'themeColor': val })
          .then(res => {
            this.$store.dispatch('settings/changeSetting', {
              key: 'theme',
              value: val
            })
            this.notifyMessage('success', '主题切换成功！')
          }).catch(() => {
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.drawer-container {
  padding: 24px;
  font-size: 14px;
  line-height: 1.5;
  word-wrap: break-word;

  .drawer-title {
    margin-bottom: 12px;
    color: rgba(0, 0, 0, .85);
    font-size: 14px;
    line-height: 22px;
  }

  .drawer-item {
    color: rgba(0, 0, 0, .65);
    font-size: 14px;
    padding: 12px 0;
  }

  .drawer-switch {
    float: right
  }
}
</style>

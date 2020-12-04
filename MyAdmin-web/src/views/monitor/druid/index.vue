<template>
  <elFrame :src="src" />
</template>

<script>
import elFrame from '@/components/Iframe/index'

export default {
  name: 'druid',
  components: { elFrame },
  data() {
    return {
      src: process.env.VUE_APP_BASE_API + '/druid/index.html'
    }
  }
}
</script>


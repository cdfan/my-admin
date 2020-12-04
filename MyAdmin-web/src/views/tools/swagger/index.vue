<template>
  <elFrame :src="src" />
</template>

<script>
import elFrame from '@/components/Iframe/index'

export default {
  name: 'swagger',
  components: { elFrame },
  data() {
    return {
      src: process.env.VUE_APP_BASE_API + '/swagger-ui.html'
    }
  }
}
</script>


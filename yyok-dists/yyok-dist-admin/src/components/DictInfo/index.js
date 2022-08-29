import DictInfo from './DictInfo'

const install = function(Vue) {
  Vue.mixin({
    data() {
      if (this.$options.dictInfos instanceof Array) {
        const dictInfo = {
          dictInfo: {},
          label: {}
        }
        return {
          dictInfo
        }
      }
      return {}
    },
    created() {
      if (this.$options.dictInfos instanceof Array) {
        new DictInfo(this.dictInfo).init(this.$options.dictInfos, () => {
          this.$nextTick(() => {
            this.$emit('dictInfoReady')
          })
        })
      }
    }
  })
}

export default { install }

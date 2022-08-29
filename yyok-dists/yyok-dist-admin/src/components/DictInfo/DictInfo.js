import Vue from 'vue'
import {get as getDictInfoDetail} from '@/api/info/dictInfoDetail'

export default class DictInfo {
  constructor(dictInfo) {
    this.dictInfo = dictInfo
  }

  async init(names, completeCallback) {
    if (names === undefined || name === null) {
      throw new Error('need DictInfo names')
    }
    const ps = []
    names.forEach(n => {
      Vue.set(this.dictInfo.dict, n, {})
      Vue.set(this.dictInfo.label, n, {})
      Vue.set(this.dictInfo, n, [])
      ps.push(getDictInfoDetail(n).then(data => {
        this.dictInfo[n].splice(0, 0, ...data.content)
        data.content.forEach(d => {
          if (parseInt(d.value).toString() != 'NaN') {
            d.value = parseInt(d.value)
          }
          Vue.set(this.dictInfo.dictInfo[n], d.value, d)
          Vue.set(this.dictInfo.label[n], d.value, d.label)
        })
      }))
    })
    await Promise.all(ps)
    completeCallback()
  }
}

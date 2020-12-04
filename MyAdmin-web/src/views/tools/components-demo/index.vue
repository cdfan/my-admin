<template>
  <div class="app-container">
      <el-row :gutter="10" class="rowStyle">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>业务字典选择组件</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="业务字典编码">
                  <el-select v-model="dictCode" placeholder="请选择业务字典编码" clearable filterable class="full-width" @change="clearDictDetails">
                    <el-option
                      v-for="dict in dictsInfo"
                      :key="dict.code"
                      :label="dict.code"
                      :value="dict.code">
                      <span style="float: left">{{ dict.code }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.name }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="业务字典详情">
                  <DictSelect :dictTypeCode="dictCode" placeholder="请选择" :value.sync="dictSelectValue" class="full-width"/>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ dictSelectValue }}
                  </el-tag>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card>
            <div slot="header" class="clearfix">
              <span>部门选择组件</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="地址选择，多选">
                  <DeptSelect placeholder="请选择部门" :value.sync="depts" :multiple="true"/>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ depts }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="地址选择，单选">
                  <div style="display: block">
                    <DeptSelect placeholder="请选择部门" :value.sync="dept" :appendToBody="true"/>
                  </div>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ dept }}
                  </el-tag>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10" class="rowStyle">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>全国地址级联选择组件</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="地址选择">
                  <AddrCascader ref="addrCascader" placeholder="请选择地址" :value.sync="addrCode"/>
                </el-form-item>
                <el-form-item label="获取值，编码">
                  <el-tag class="full-width" type="info">
                    {{ addrCode }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="获取值，名称">
                  <el-tag class="full-width" type="info">
                    {{ addrName }}
                  </el-tag>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>菜单选择组件</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="菜单选择，多选">
                  <MenuSelect placeholder="请选择菜单" :value.sync="menus" :multiple="true" :appendToBody="true"/>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ menus }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="子菜单选择，单选">
                  <div>
                    <MenuSelect placeholder="请选择子菜单" :value.sync="menu" :disableBranchNodes="true" :appendToBody="true"/>
                  </div>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ menu }}
                  </el-tag>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10" class="rowStyle">
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>图标选择组件</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="图标选择">
                  <el-popover  placement="bottom"  width="460"  trigger="click"  @show="$refs['iconSelect'].reset()">
                    <IconSelect ref="iconSelect" @selected="iconSelected" />
                    <el-input slot="reference" v-model="icon" placeholder="点击选择图标" readonly>
                      <svg-icon
                        v-if="icon"
                        slot="prefix"
                        :icon-class="icon"
                        class="el-input__icon"
                      />
                      <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                    </el-input>
                  </el-popover>
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ icon }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="图标">
                  <div class="icon-item" v-if="icon">
                    <svg-icon :icon-class="icon" />
                  </div>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
        <el-col :lg="12" :md="12" :sm="24" :xs="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>代码着色</span>
            </div>
            <div class="componentBody">
              <el-form>
                <el-form-item label="Java">
                  <div v-highlight class="codeStyle">
                    <pre class="codexml full-width"><code class="java">{{javaCode}}</code></pre>
                  </div>
                </el-form-item>
                <el-form-item label="Html">
                  <div v-highlight class="codeStyle">
                    <pre class="codexml full-width"><code class="html">{{htmlCode}}</code></pre>
                  </div>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row class="rowStyle">
        <el-col :span="24">
          <el-card >
            <div slot="header" class="clearfix">
              <span>富文本编辑器组件</span>
            </div>
            <div class="componentBodyBig">
              <el-form>
                <el-form-item>
                  <Editor :value.sync="editorVal" />
                </el-form-item>
                <el-form-item label="获取值">
                  <el-tag class="full-width" type="info">
                    {{ editorVal }}
                  </el-tag>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import { dictInfo } from '@/api/dict'
import DictSelect from '@/components/DictSelect' // 业务字典下拉选
import AddrCascader from '@/components/AddrCascader' // 全国地址级联选择
import DeptSelect from '@/components/DeptSelect' // 部门选择下拉选
import MenuSelect from '@/components/MenuSelect' // 菜单选择下拉选
import IconSelect from '@/components/IconSelect' // 图标选择
import Editor from '@/components/Editor' // 富文本编辑器
import highlight from '@/directive/highlight' //  代码样式
// 系统组件
export default {
  name: 'componentsDemo',
  components: {
    DictSelect,
    DeptSelect,
    MenuSelect,
    AddrCascader,
    IconSelect,
    Editor
  },
  directives: { highlight },
  data() {
    return {
      dictsInfo: [],
      dictCode: '',
      dictSelectValue: undefined,
      addrCode: [],
      addrName: undefined,
      dept: undefined,
      depts: [],
      menu: undefined,
      menus: [],
      icon: undefined,
      editorVal: undefined,
      javaCode: 'public void test(Object args){\n    System.out.println("this is java code.....");\n}',
      htmlCode: '<div class="div-class">\n    <span>this is html code.....</span>\n</div>'
    }
  },
  watch: {
    addrCode(val) {
      if (val) {
        this.addrName = this.$refs.addrCascader.getSelectAddrName()
      }
    }
  },
  created() {
    this.getDictList()
  },
  methods: {
    // 获取所有父级业务字典编码
    getDictList() {
      dictInfo({ dictType: '0', page: 1, limit: 9999 })
        .then(response => {
          this.dictsInfo = response.data.result.records
        })
        .catch(() => {
        })
    },
    clearDictDetails() {
      this.dictSelectValue = undefined
    },
    // 选择图标
    iconSelected(name) {
      this.icon = name
    }
  }
}
</script>

<style lang="scss" scoped>
.rowStyle{
  margin-bottom: 10px;
}
.componentBody{
  height: 350px;
}
.componentBodyBig{
  height: 500px;
  overflow: auto;
}
.full-width{
  width: 100%;
}
.icon-item {
  text-align: center;
  width: 100px;
  float: left;
  font-size: 30px;
  color: #24292e;
  cursor: pointer;
}
.codexml{
  overflow: auto;
}
.codeStyle{
  line-height: 25px;
}
</style>

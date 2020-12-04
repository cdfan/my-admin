<template>
  <div class="app-container">

    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="菜单名称" prop="menuName">
        <el-input v-model="listQuery.menuName" placeholder="菜单名称，支持模糊搜索" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item label="菜单编码" prop="menuCode">
        <el-input v-model="listQuery.menuCode" placeholder="请输入菜单编码" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'menu_query'"  type="primary" icon="el-icon-search" @click="getList">
          搜索
        </el-button>
        <el-button v-waves v-permission="'menu_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'menu_add'" type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
          新增
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      border
      stripe
      row-key="menuId"
      :expand-row-keys="expands"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="菜单名称" prop="menuName" align="left" width="250px"/>
      <el-table-column label="菜单编码" prop="menuCode" align="left" width="180px"/>
      <el-table-column label="类型" align="center"  width="80px">
        <template slot-scope="{row}">
          <el-tag v-if="row.ismenu=='0'">按钮</el-tag>
          <el-tag v-if="row.ismenu=='1'" type="warning">菜单</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="菜单图标" prop="icon" align="center" width="80px" >
        <template slot-scope="{row}">
          <svg-icon :icon-class="row.icon ? row.icon : ''" />
        </template>
      </el-table-column>
      <el-table-column label="菜单url" prop="url" align="left" min-width="250px"/>
      <el-table-column label="排序" prop="orderNum" align="center"  width="60px" />
      <el-table-column label="状态" align="center" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-switch
            @change="handleModifyStatus(row)"
            v-model="row.state"
            active-value='1'
            inactive-value='0'>
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'menu_edit'"  type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-permission="'menu_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog  :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false' width="800px">
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="80px">
        <el-form-item label="类型" prop="ismenu">
          <el-radio-group v-model="dataform.ismenu">
            <el-radio-button :label="'1'">菜单</el-radio-button>
            <el-radio-button :label="'0'">按钮</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="父级菜单" prop="pid">
          <MenuSelect ref="menuSelect" :value.sync="dataform.pid" placeholder="请选择上级菜单"/>
        </el-form-item>
        <el-form-item v-show="dataform.ismenu==='1'" label="菜单图标" prop="icon">
          <el-popover  placement="bottom"  width="460"  trigger="click"  @show="$refs['iconSelect'].reset()">
            <IconSelect ref="iconSelect" @selected="selected" />
            <el-input slot="reference" v-model="dataform.icon" placeholder="点击选择图标" readonly>
              <svg-icon
                v-if="dataform.icon"
                slot="prefix"
                :icon-class="dataform.icon"
                class="el-input__icon"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon" />
            </el-input>
          </el-popover>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input  clearable v-model="dataform.menuName" maxlength="16" show-word-limit placeholder="请输入菜单名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单编码" prop="menuCode">
              <el-input  clearable v-model="dataform.menuCode" maxlength="32" show-word-limit placeholder="菜单编码，且不可重复"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item v-show="dataform.ismenu==='1'" label="菜单url" prop="url" >
          <el-input clearable  v-model="dataform.url" placeholder="请输入菜单url，以/开头"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="state">
              <el-switch
                v-model="dataform.state"
                active-value='1'
                inactive-value='0'
                active-text="启用"
                inactive-text="禁用">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="orderNum">
              <el-input-number controls-position="right" :min="0" clearable v-model.number="dataform.orderNum" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { menuInfo, createMenu, updateMenu, deleteMenu, countMenu } from '@/api/menu'
import IconSelect from '@/components/IconSelect'
import MenuSelect from '@/components/MenuSelect' // 菜单选择下拉选

export default {
  name: 'menus',
  components: { IconSelect, MenuSelect },
  data() {
    // 表单验证器
    const menuUrlValidator = (rule, value, callback) => {
      // 如果是菜单类型则url是必须的
      if (this.dataform.ismenu === '1') {
        if (!value) {
          callback(new Error('当前类型为菜单，请输入菜单url'))
        } else {
          const patt = new RegExp('^/[\\w/-]+$')
          if (!patt.test(value)) {
            callback(new Error('请输入正确的菜单url'))
          } else {
            callback()
          }
        }
      } else {
        callback()
      }
    }
    const pidValidator = (rule, value, callback) => {
      // 如果是按钮则父级菜单必填，否则可以为空
      if (this.dataform.ismenu === '0') {
        if (!value) {
          callback(new Error('当前类型为按钮，请选择父级菜单'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    const menuCodevalidator = (rule, value, callback) => {
      const patt = new RegExp('^\\w{1,32}$')
      if (!patt.test(value)) {
        callback(new Error('菜单编码只支持字母数字下划线,并且长度小于32'))
      } else {
        // 验证是否重复
        let query
        if (this.dialogStatus === 'create') {
          query = { menuCode: value }
        } else {
          query = { menuCode: value, menuId: this.dataform.menuId }
        }
        countMenu(query)
          .then(response => {
            if (response.data.result > 0) {
              callback(new Error('菜单编码已存在，请重新输入'))
            } else {
              callback()
            }
          })
      }
    }
    return {
      list: [],
      listLoading: true,
      fullLoading: false,
      // 默认展开节点
      expands: [],
      listQuery: {
        menuCode: undefined,
        menuName: undefined
      },
      dataform: {
        menuId: undefined,
        menuName: undefined,
        menuCode: undefined,
        pid: undefined,
        url: undefined,
        icon: undefined,
        ismenu: '1',
        orderNum: undefined,
        state: '1'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { max: 16, message: '长度必须小于16个字符', trigger: 'blur' }
        ],
        menuCode: [
          { required: true, message: '请输入菜单编码', trigger: 'blur' },
          { validator: menuCodevalidator, trigger: 'blur' }
        ],
        pid: [
          { validator: pidValidator, trigger: 'change' }
        ],
        url: [
          { validator: menuUrlValidator, trigger: 'blur' }
        ],
        icon: [
          { pattern: '\\w+', message: '图标名称只支持字母数值下划线', trigger: 'blur' }
        ],
        ismenu: [
          { required: true, message: '请选择菜单类型', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入排序顺序', trigger: 'blur' },
          { type: 'number', message: '排序顺序只支持数值', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      menuInfo(this.listQuery)
        .then(response => {
          this.expands = []
          this.list = response.data.result
          this.listLoading = false
          // 默认展开第一行
          if (this.list.length > 0) {
            this.expands.push(this.list[0].menuId.toString())
          }
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    handleModifyStatus(row) {
      const obj = {}
      obj.menuId = row.menuId
      obj.state = row.state
      updateMenu(obj)
        .then(() => {
          this.notifyMessage()
        })
    },
    resetTemp() {
      this.dataform = {
        menuId: undefined,
        menuName: undefined,
        menuCode: undefined,
        pid: undefined,
        url: undefined,
        icon: undefined,
        ismenu: '1',
        orderNum: undefined,
        state: '1'
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        // 重新加载菜单数据
        this.$refs.menuSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    createData() {
      this.clearData()
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          this.fullLoading = true
          createMenu(this.dataform)
            .then(() => {
              this.fullLoading = false
              this.dialogFormVisible = false
              this.notifyMessage()
              this.getList()
            })
            .catch(() => {
              this.fullLoading = false
              this.dialogFormVisible = false
            })
        }
      })
    },
    handleUpdate(row) {
      this.dataform = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        // 重新加载菜单数据
        this.$refs.menuSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    updateData() {
      this.clearData()
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.dataform)
          this.fullLoading = true
          updateMenu(tempData)
            .then(() => {
              this.fullLoading = false
              this.dialogFormVisible = false
              this.notifyMessage()
              this.getList()
            })
            .catch(() => {
              this.fullLoading = false
              this.dialogFormVisible = false
            })
        }
      })
    },
    handleDelete(row) {
      this.$confirm('删除该记录，如果当前记录下包含子节点请先删除子节点，是否继续删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteMenu(row.menuId)
          .then(() => {
            this.fullLoading = false
            this.notifyMessage()
            this.getList()
          })
          .catch(() => {
            this.fullLoading = false
          })
      }).catch(() => {
        this.notifyMessage('info', '删除取消！')
      })
    },
    // 选择图标
    selected(name) {
      this.dataform.icon = name
    },
    clearData() {
      // 当类型为按钮时将图标以及url清除
      if (this.dataform.ismenu === '0') {
        this.dataform.icon = ''
        this.$refs['iconSelect'].reset()
        this.dataform.url = ''
      }
    }
  }
}
</script>

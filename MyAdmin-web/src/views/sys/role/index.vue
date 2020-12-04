<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="listQuery.roleName" placeholder="角色名称，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="角色编码" prop="roleCode">
        <el-input v-model="listQuery.roleCode" placeholder="请输入角色编码" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'role_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'role_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'role_add'"  type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="roleId"
      @sort-change="sortChange($event, listQuery, handleFilter)"
      :expand-row-keys="expands"
    >
      <el-table-column type="expand" >
        <template slot-scope="props">
          <p class="table-expand-title">授权菜单:</p>
          <el-tree :data="props.row.menuTree" :props="{children: 'children', label: 'menuName'}"></el-tree>
        </template>
      </el-table-column>
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="角色名称" prop="roleName" align="left" width="200px" sortable="custom"/>
      <el-table-column label="角色编码" prop="roleCode" align="left" width="150px" sortable="custom"/>
      <el-table-column label="描述" prop="remark" align="left" min-width="200px" sortable="custom"/>
      <el-table-column label="修改人" prop="updateUser" align="left" min-width="180px" sortable="custom"/>
      <el-table-column label="修改时间" prop="updateTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'role_edit'"  type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-permission="'role_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
          <el-button v-waves v-permission="'role_permission'" size="mini" type="success" @click="permissionMenu(row)">
            授权
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog width="800px" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false'>
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input clearable v-model="dataform.roleName" maxlength="16" show-word-limit placeholder="请输入角色名称"/>
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input :disabled="dialogStatus!=='create'" clearable v-model="dataform.roleCode" maxlength="16" show-word-limit  placeholder="请输入角色编码,且不可重复"/>
        </el-form-item>
        <el-form-item label="授权" v-permission="'role_permission'" >
          <el-button type="success" round @click="permissionMenu">授权菜单</el-button>
        </el-form-item>
        <el-form-item label="描述" prop="remark">
          <el-input type="textarea" autosize placeholder="请输入描述或备注" clearable v-model="dataform.remark" />
        </el-form-item>
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

    <el-dialog  width="500px" title="授权菜单" :visible.sync="permissionMenuVisible" :close-on-click-modal='false' append-to-body>
      <el-input
        placeholder="输入关键字进行过滤"
        clearable
        v-model="filterMenuText">
      </el-input>
      <el-tree
        class="filter-tree"
        :data="menuList"
        :props="{ children: 'children',  label: 'menuName' }"
        default-expand-all
        :filter-node-method="filterNode"
        show-checkbox
        check-strictly
        node-key="menuId"
        ref="tree">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="permissionMenuVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="authorization()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { roleInfo, createRole, updateRole, deleteRole, getHasMenuId, countRole } from '@/api/role'
import { menuInfo } from '@/api/menu'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'role',
  components: { Pagination },
  data() {
    const roleCodeValidator = (rule, value, callback) => {
      const patt = new RegExp('^\\w{1,16}$')
      if (!patt.test(value)) {
        callback(new Error('角色编码只支持字母数字下划线,长度小于16'))
      } else {
        // 验证是否重复
        let query
        if (this.dialogStatus === 'create') {
          query = { roleCode: value }
        } else {
          query = { roleCode: value, roleId: this.dataform.roleId }
        }
        countRole(query)
          .then(response => {
            if (response.data.result > 0) {
              callback(new Error('角色编码已存在，请重新输入'))
            } else {
              callback()
            }
          })
      }
    }
    return {
      list: [],
      // 菜单树
      menuList: [],
      // 菜单树过滤字符串
      filterMenuText: '',
      total: 0,
      listLoading: true,
      fullLoading: false,
      // 默认展开节点
      expands: [],
      listQuery: {
        page: 1,
        limit: 10,
        sort: undefined,
        order: undefined,
        roleCode: undefined,
        roleName: undefined
      },
      dataform: {
        roleId: undefined,
        roleName: undefined,
        roleCode: undefined,
        remark: undefined,
        // 选中的所有菜单树节点
        allMenuIds: []
      },
      // 新增修改弹框
      dialogFormVisible: false,
      // 权限菜单选择弹框
      permissionMenuVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { max: 16, message: '长度必须小于16个字符', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          { validator: roleCodeValidator, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  watch: {
    filterMenuText(val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    getList() {
      this.listLoading = true
      roleInfo(this.listQuery)
        .then(response => {
          this.expands = []
          this.list = response.data.result.records
          this.total = response.data.result.total
          this.listLoading = false
          // 默认展开第一行
          if (this.list.length > 0) {
            this.expands.push(this.list[0].roleId.toString())
          }
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.dataform = {
        roleId: undefined,
        roleName: undefined,
        roleCode: undefined,
        remark: undefined,
        // 选中的所有菜单树节点
        allMenuIds: []
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['formRef'].clearValidate()
      })
    },
    createData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          this.fullLoading = true
          createRole(this.dataform)
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
        this.$refs['formRef'].clearValidate()
      })
    },
    updateData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.dataform)
          this.fullLoading = true
          updateRole(tempData)
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
      this.$confirm('删除该记录，如果当前角色被用户所引用请先解除关系再删除，是否继续删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteRole(row.roleId)
          .then(() => {
            this.fullLoading = false
            this.notifyMessage()
            this.handleFilter()
          })
          .catch(() => {
            this.fullLoading = false
          })
      }).catch(() => {
        this.notifyMessage('info', '删除取消！')
      })
    },
    permissionMenu(row) {
      // 获取有效的树菜单
      this.fullLoading = true
      menuInfo({ 'state': '1' })
        .then(response => {
          this.menuList = response.data.result
          this.permissionMenuVisible = true
          this.$nextTick(() => {
            // 设置默认选中
            if (row.roleId) {
            // 单独设置权限
              this.resetTemp()
              this.dialogStatus = 'auth'
              this.dataform.roleId = row.roleId
              getHasMenuId(row.roleId)
                .then(response => {
                  this.$refs.tree.setCheckedKeys(response.data.result)
                })
            } else {
            // 通过新增或修改设置权限
              if (this.dataform.allMenuIds && this.dataform.allMenuIds.length > 0) {
                this.$refs.tree.setCheckedKeys(this.dataform.allMenuIds)
              } else {
                if (this.dataform.roleId) {
                  getHasMenuId(this.dataform.roleId)
                    .then(response => {
                      this.$refs.tree.setCheckedKeys(response.data.result)
                    })
                } else {
                  this.$refs.tree.setCheckedKeys([])
                }
              }
            }
            this.fullLoading = false
          })
        })
        .catch(() => {
          this.fullLoading = false
        })
    },
    filterNode(value, data, node) {
      if (!value) return true
      return data.menuName.indexOf(value) !== -1
    },
    authorization() {
      // 选中的所有节点
      this.dataform.allMenuIds = this.$refs.tree.getCheckedKeys()
      // 单独设置权限
      if (this.dialogStatus === 'auth') {
        const tempData = Object.assign({}, this.dataform)
        updateRole(tempData)
          .then(() => {
            this.notifyMessage()
            this.getList()
          })
      }
      this.permissionMenuVisible = false
    }
  }
}
</script>
<style lang="scss" scoped>
.table-expand-title {
  color: #99a9bf;
  margin: 0;
  margin-bottom: 10px;
}
.filter-tree{
  height: 50vh;
  overflow: auto;
  margin-top: 2vh;
}
</style>

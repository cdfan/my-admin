<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xl="4" :lg="5" :md="6" :sm="6" :xs="24">
        <el-input
          placeholder="部门名称，支持模糊搜索"
          clearable
          v-model="filterDeptText">
        </el-input>

        <el-tree
          class="filter-tree"
          :data="deptTreeData"
          :props="{ children: 'children',  label: 'deptName' }"
          default-expand-all
          :filter-node-method="filterNode"
          :expand-on-click-node="false"
          node-key="deptId"
          ref="tree"
          highlight-current
          @node-click="deptTreeNodeClick">
        </el-tree>
      </el-col>
      <el-col :xl="20" :lg="19" :md="18" :sm="18" :xs="24">
        <el-form :model="listQuery" ref="filterForm" :inline="true">
          <el-form-item label="账号" prop="userCode">
            <el-input v-model="listQuery.userCode" placeholder="请输入账号"  @keyup.enter.native="handleFilter" />
          </el-form-item>
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="listQuery.userName" placeholder="用户名，支持模糊搜索" @keyup.enter.native="handleFilter" />
          </el-form-item>
          <el-form-item label="角色" prop="roleId">
            <el-select v-model="listQuery.roleId" clearable filterable placeholder="请选择角色">
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button v-waves v-permission="'user_query'" type="primary" icon="el-icon-search" @click="handleFilter" v-loading.fullscreen.lock="fullLoading">
              搜索
            </el-button>
            <el-button v-waves v-permission="'user_query'" type="success" icon="el-icon-refresh" @click="resetFormInfo">
              重置
            </el-button>
          </el-form-item>
        </el-form>
        <el-form>
          <el-form-item>
            <el-button v-waves  v-permission="'user_add'" type="primary" icon="el-icon-plus" @click="handleCreate">
              新增
            </el-button>
            <el-button  v-waves v-permission="'user_export'" type="warning" icon="el-icon-download" @click="handleDownload">
              导出
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
          @sort-change="sortChange($event, listQuery, handleFilter)"
          row-key="userId"
          :expand-row-keys="expands"
        >
          <el-table-column type="expand" >
            <template slot-scope="props">
              <el-form label-position="left" class="table-expand-title">
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="角色">
                      <el-tag class="tags-margin" effect="plain" type="info" v-for="role in props.row.roles" :key="role.roleId">{{ role.roleName }}</el-tag>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="部门">
                      <el-tag class="tags-margin" effect="plain" type="info" v-for="dept in props.row.depts" :key="dept.deptId">{{ dept.deptName }}</el-tag>
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="备注">
                      <span>{{ props.row.remark }}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="序号" type="index" width="50px" />
          <el-table-column label="账号" prop="userCode" align="center" width="80px" sortable="custom"/>
          <el-table-column label="用户名" prop="userName"  align="center" width="100px" sortable="custom"/>
          <el-table-column label="电话" align="center" prop="phone" width="110px" sortable="custom"/>
          <el-table-column label="邮箱" align="center" prop="email" min-width="150px" sortable="custom"/>
          <el-table-column label="性別" prop="sex" align="center" width="80px" sortable="custom">
            <template slot-scope="{row}">
              <span>{{ row.sex | sexFilter }}</span>
            </template>
          </el-table-column>
          <el-table-column label="修改时间" prop="updateTime" align="center" width="140px" sortable="custom">
            <template slot-scope="{row}">
              <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="state" align="center" class-name="status-col" width="80px" sortable="custom">
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
              <el-button v-waves v-permission="'user_edit'" type="primary" size="mini" @click="handleUpdate(row)">
                修改
              </el-button>
              <el-button v-waves v-permission="'user_delete'" size="mini" type="danger" @click="handleDelete(row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
      </el-col>
    </el-row>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false' width="800px">
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="70px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="账号" prop="userCode">
              <el-input :disabled="dialogStatus!=='create'" v-model="dataform.userCode" maxlength="16" show-word-limit placeholder="请输入账号，且不可重复"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input clearable v-model="dataform.userName" maxlength="32" show-word-limit placeholder="请输入用户名"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="dataform.roleIds" multiple clearable filterable reserve-keyword placeholder="请选择角色">
            <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="deptIds">
          <DeptSelect ref="deptSelect" :value.sync="dataform.deptIds" placeholder="请选择所属部门" :multiple="true" :flat="true"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input clearable type="email" v-model="dataform.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input clearable v-model="dataform.phone" maxlength="11" show-word-limit placeholder="请输入电话号码"/>
            </el-form-item>
          </el-col>
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
            <el-form-item label="性别" prop="sex">
              <el-switch
                v-model="dataform.sex"
                active-color="#87CEFA"
                inactive-color="#FFC0CB"
                active-value='1'
                inactive-value='0'
                active-text="男"
                inactive-text="女">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="密码" prop="password" v-if="dialogStatus==='create'">
          <el-input v-model="dataform.password" placeholder="请输入密码"  show-password autocomplete="new-password"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dataform.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入描述或备注"/>
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
  </div>
</template>

<script>
import { userInfo, createUser, updateUser, deleteUser, countUser } from '@/api/user'
import { deptInfo } from '@/api/dept'
import { getRoleList } from '@/api/role'
import Pagination from '@/components/Pagination'
import { md5Encrypt } from '@/utils/jsencrypt'
import DeptSelect from '@/components/DeptSelect' // 部门选择下拉选

export default {
  name: 'user',
  components: { Pagination, DeptSelect },
  filters: {
    sexFilter(sexNum) {
      return sexNum === '1' ? '男' : '女'
    }
  },
  watch: {
    filterDeptText(val) {
      this.$refs.tree.filter(val)
    }
  },
  data() {
    // 表单验证器
    const passwordValidator = (rule, value, callback) => {
      // 如果是新增，密码必须填写，否则不需要
      if (this.dialogStatus === 'create') {
        if (!value) {
          callback(new Error('请输入用户密码'))
        } else {
          const patt = new RegExp('^\\w{6,}$')
          if (!patt.test(value)) {
            callback(new Error('密码不能小于6位，且只能是字母数值下划线的组合'))
          } else {
            callback()
          }
        }
      } else {
        callback()
      }
    }
    const userCodeValidator = (rule, value, callback) => {
      const patt = new RegExp('^\\w{1,16}$')
      if (!patt.test(value)) {
        callback(new Error('账号只支持字母数字下划线,长度小于16'))
      } else {
        // 验证是否重复
        let query
        if (this.dialogStatus === 'create') {
          query = { userCode: value }
        } else {
          query = { userCode: value, userId: this.dataform.userId }
        }
        countUser(query)
          .then(response => {
            if (response.data.result > 0) {
              callback(new Error('用户账号已存在，请重新输入'))
            } else {
              callback()
            }
          })
      }
    }
    return {
      list: [],
      total: 0,
      listLoading: true,
      // 全局遮罩
      fullLoading: false,
      // 默认展开节点
      expands: [],
      listQuery: {
        page: 1,
        limit: 10,
        sort: undefined,
        order: undefined,
        userCode: undefined,
        userName: undefined,
        deptId: undefined,
        roleId: undefined
      },
      // 部门树过滤条件
      filterDeptText: '',
      // 部门树
      deptTreeData: [],
      // 角色列表
      roleList: [],
      dataform: {
        userId: undefined,
        userCode: undefined,
        userName: undefined,
        roleIds: [],
        deptIds: [],
        email: undefined,
        phone: undefined,
        state: '1',
        sex: '1',
        password: undefined,
        remark: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      rules: {
        userCode: [
          { required: true, message: '请输入账号，且不可重复', trigger: 'blur' },
          { validator: userCodeValidator, trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { max: 32, message: '长度必须小于32个字符', trigger: 'blur' }
        ],
        roleIds: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ],
        deptIds: [
          { required: true, message: '请选择所属部门', trigger: 'change' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }
        ],
        phone: [
          { pattern: '^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$', message: '请输入正确的电话号码', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
        password: [
          { validator: passwordValidator, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getDetpTree()
    this.getRoleList()
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      userInfo(this.listQuery)
        .then(response => {
          this.expands = []
          this.list = response.data.result.records
          this.total = response.data.result.total
          this.listLoading = false
          // 默认展开第一行
          if (this.list.length > 0) {
            this.expands.push(this.list[0].userId.toString())
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
    handleModifyStatus(row) {
      this.$confirm('确定修改用户状态？当禁用后该用户将被冻结, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const obj = {}
        obj.userId = row.userId
        obj.state = row.state
        updateUser(obj)
          .then(() => {
            this.notifyMessage()
          })
      }).catch(() => {
        row.state = row.state === '1' ? '0' : '1'
        this.notifyMessage('info', '操作取消！')
      })
    },
    resetTemp() {
      this.dataform = {
        userId: undefined,
        userCode: undefined,
        userName: undefined,
        roleIds: [],
        deptIds: [],
        email: undefined,
        phone: undefined,
        state: '1',
        sex: '1',
        password: undefined,
        remark: undefined
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      // 重新获取部门以及角色列表
      this.getRoleList()
      this.$nextTick(() => {
        this.$refs.deptSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    createData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          this.fullLoading = true
          // 对传输密码加密，防止明文传输
          const cryptoPassword = md5Encrypt(this.dataform.password)
          const tempDataForm = Object.assign({}, this.dataform)
          tempDataForm.password = cryptoPassword
          createUser(tempDataForm)
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
      // 重新获取部门以及角色列表
      this.getRoleList()
      this.$nextTick(() => {
        this.$refs.deptSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    updateData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.dataform)
          this.fullLoading = true
          updateUser(tempData)
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
      this.$confirm('删除用户后用户账户将被注销且该用户将立即退出系统,建议禁用用户而不是删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.fullLoading = true
        deleteUser(row.userId)
          .then(() => {
            this.fullLoading = false
            this.notifyMessage()
            this.handleFilter()
          })
          .catch(() => {
            this.fullLoading = false
          })
      }).catch(() => {
        this.notifyMessage('info', '删除取消!')
      })
    },
    resetFormInfo() {
      this.resetForm('filterForm', this.listQuery)
      // 将部门也重置
      this.$refs.tree.setCurrentKey(null)
      this.listQuery.deptId = undefined
      this.getList()
    },
    // 部门树操作
    getDetpTree() {
      deptInfo()
        .then(response => {
          this.deptTreeData = response.data.result
        })
    },
    filterNode(value, data, node) {
      if (!value) return true
      return data.deptName.indexOf(value) !== -1
    },
    deptTreeNodeClick(data) {
      this.listQuery.deptId = data.deptId
      this.handleFilter()
    },
    // 角色操作
    getRoleList() {
      getRoleList()
        .then(response => {
          this.roleList = response.data.result
        })
    },
    handleDownload() {
      this.fullLoading = true
      import('@/utils/vendor/Export2Excel').then(excel => {
        // 如果当前数据的总数大于每页数，则说明list中的数据为分页数据，所以需要重新获取所有数据,否则list中存储的就是所有数据
        new Promise((resolve, reject) => {
          if (this.total > this.listQuery.limit) {
            const tempQuery = Object.assign({}, this.listQuery)
            tempQuery.page = 1
            tempQuery.limit = 99999
            userInfo(tempQuery)
              .then(response => {
                resolve(response.data.result.records)
              })
              .catch(() => {
                this.fullLoading = false
              })
          } else {
            resolve(this.deepClone(this.list)) // copy object array
          }
        }).then(list => {
          const tHeader = ['用户名', '账号', '电话', '邮箱', '性别', '状态', '角色', '部门', '修改时间', '备注']
          const filterVal = ['userName', 'userCode', 'phone', 'email', 'sex', 'state', 'roles', 'depts', 'updateTime', 'remark']
          const data = this.formatJson(filterVal, list)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: '用户信息'
          })
          this.fullLoading = false
          this.notifyMessage()
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(data => filterVal.map(val => {
        if (val === 'sex') {
          data[val] = data[val] === '1' ? '男' : '女'
        } else if (val === 'state') {
          data[val] = data[val] === '1' ? '启用' : '禁用'
        } else if (val === 'roles') {
          data[val] = data[val].map(role => {
            return role.roleName
          }).join(',')
        } else if (val === 'depts') {
          data[val] = data[val].map(dept => {
            return dept.deptName
          }).join(',')
        } else if (val === 'updateTime') {
          data[val] = this.parseTime(data[val], '{y}-{m}-{d} {h}:{i}')
        }
        return data[val]
      }))
    }
  }
}
</script>
<style lang="scss" scoped>

.table-expand-title{
  font-size: 0;
 .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
 }
 /deep/ label {
    width: 60px;
    color: #99a9bf;
  }
}
.filter-tree{
  margin-top: 2vh;
}
.tags-margin{
  margin-right: 0.5vw
}
</style>

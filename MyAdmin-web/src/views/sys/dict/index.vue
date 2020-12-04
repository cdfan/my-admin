<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="字典名称" prop="name">
        <el-input v-model="listQuery.name" placeholder="字典名称，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="字典编码" prop="code">
        <el-input v-model="listQuery.code" placeholder="字典编码，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="listQuery.state" clearable filterable placeholder="请选择字典状态">
          <el-option
            v-for="item in stateOption"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'dict_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'dict_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'dict_add'"  type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="dictId"
      @sort-change="sortChange($event, listQuery, handleFilter)"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="字典名称" prop="name" align="left" width="200px" sortable="custom"/>
      <el-table-column label="字典编码" prop="code" align="left" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <el-tooltip effect="dark" content="点击进入字典详情" placement="bottom-start">
            <el-tag style="cursor:pointer" @click="openDictDetails(row)" >{{ row.code }}</el-tag>
          </el-tooltip>
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
      <el-table-column label="具体描述" prop="msg" align="left" min-width="250px" sortable="custom"/>
      <el-table-column label="修改人" prop="updateUser" align="left" min-width="150px" sortable="custom"/>
      <el-table-column label="修改时间" prop="updateTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'dict_edit'"  type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-permission="'dict_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
          <el-button v-waves v-permission="'dict_query'" size="mini" type="success" @click="openDictDetails(row)">
            详情
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog width="800px" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false'>
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="80px">
        <el-form-item label="字典名称" prop="name">
          <el-input clearable v-model="dataform.name" maxlength="16" show-word-limit placeholder="请输入字典名称"/>
        </el-form-item>
        <el-form-item label="字典编码" prop="code">
          <el-input :disabled="dialogStatus!=='create'" clearable v-model="dataform.code" maxlength="16" show-word-limit  placeholder="请输入字典编码,且不可重复"/>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-switch
            v-model="dataform.state"
            active-value='1'
            inactive-value='0'
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
        </el-form-item>
        <el-form-item label="描述" prop="msg">
          <el-input type="textarea" autosize placeholder="请输入描述或备注" clearable v-model="dataform.msg" />
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

    <el-dialog width="1024px" :title="dictDetailsTitle" top="10vh" :visible.sync="dialogDictDetails" >
      <DictDetails :dictObj="dataform" v-if="dialogDictDetails"/>
    </el-dialog>
  </div>
</template>

<script>
import { dictInfo, createDict, updateDict, deleteDict, countDict } from '@/api/dict'
import Pagination from '@/components/Pagination' // 分页
import DictDetails from './components/DictDetails' // 字典详情

export default {
  name: 'dict',
  components: { Pagination, DictDetails },
  data() {
    const codeValidator = (rule, value, callback) => {
      const patt = new RegExp('^\\w{1,16}$')
      if (!patt.test(value)) {
        callback(new Error('字典编码只支持字母数字下划线,长度小于16'))
      } else {
        // 验证是否重复
        let query
        if (this.dialogStatus === 'create') {
          query = { code: value }
        } else {
          query = { code: value, dictId: this.dataform.dictId }
        }
        countDict(query)
          .then(response => {
            if (response.data.result > 0) {
              callback(new Error('字典编码已存在，请重新输入'))
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
      fullLoading: false,
      listQuery: {
        page: 1,
        limit: 10,
        sort: undefined,
        order: undefined,
        code: undefined,
        name: undefined,
        // 字典类型为父节点
        dictType: '0',
        state: undefined
      },
      dataform: {
        dictId: undefined,
        name: undefined,
        code: undefined,
        state: '1',
        dictType: '0',
        msg: undefined
      },
      // 新增修改弹框
      dialogFormVisible: false,
      // 字典详情弹框
      dialogDictDetails: false,
      // 字典详情标题
      dictDetailsTitle: '',
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      stateOption: [{
        value: '1',
        label: '启用'
      }, {
        value: '0',
        label: '禁用'
      }],
      rules: {
        name: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { max: 16, message: '长度必须小于16个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入字典编码', trigger: 'blur' },
          { validator: codeValidator, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    resetTemp() {
      this.dataform = {
        dictId: undefined,
        name: undefined,
        code: undefined,
        state: '1',
        dictType: '0',
        msg: undefined
      }
    },
    getList() {
      this.listLoading = true
      dictInfo(this.listQuery)
        .then(response => {
          this.list = response.data.result.records
          this.total = response.data.result.total
          this.listLoading = false
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
      const obj = {}
      obj.dictId = row.dictId
      obj.state = row.state
      updateDict(obj)
        .then(() => {
          this.notifyMessage()
        })
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
          createDict(this.dataform)
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
          updateDict(tempData)
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
      this.$confirm('删除该记录同时删除对应字典详情，是否继续删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteDict(row.dictId)
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
    openDictDetails(row) {
      this.dictDetailsTitle = row.name + '：字典详情'
      this.dataform = Object.assign({}, row) // copy obj
      this.dialogDictDetails = true
    }
  }
}
</script>

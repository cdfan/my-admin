<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="名称" prop="name">
        <el-input v-model="listQuery.name" placeholder="请输入名称" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="listQuery.code" placeholder="请输入编码" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="日期" prop="day">
        <el-date-picker
          v-model="listQuery.day"
          type="daterange"
          align="right"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'generateCode_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'generateCode_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'generateCode_add'"  type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="id"
      @sort-change="sortChange($event, listQuery, handleFilter)"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="名称" prop="name" align="left" width="150px" sortable="custom"/>
      <el-table-column label="编码" prop="code" align="left" width="150px" sortable="custom"/>
      <el-table-column label="状态" prop="state" align="left" width="150px" sortable="custom"/>
      <el-table-column label="备注" prop="remark" align="left" width="150px" sortable="custom"/>
      <el-table-column label="排序" prop="orderNum" align="left" width="150px" sortable="custom"/>
      <el-table-column label="日期" prop="day" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.day | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" prop="createUser" align="left" width="150px" sortable="custom"/>
      <el-table-column label="创建时间" prop="createTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" prop="updateUser" align="left" width="150px" sortable="custom"/>
      <el-table-column label="修改时间" prop="updateTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'generateCode_edit'"  size="mini" type="primary" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-permission="'generateCode_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog width="800px" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false'>
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input clearable v-model="dataform.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input clearable v-model="dataform.code" placeholder="请输入编码"/>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-input clearable v-model="dataform.state" placeholder="请输入状态"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input clearable v-model="dataform.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input clearable v-model="dataform.orderNum" placeholder="请输入排序"/>
        </el-form-item>
        <el-form-item label="日期" prop="day">
          <el-date-picker
            v-model="dataform.day"
            type="date"
            placeholder="请选择日期">
          </el-date-picker>
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
import { generateCodeInfo, createGenerateCode, updateGenerateCode, deleteGenerateCode } from '@/api/generate-code'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'generateCode',
  components: { Pagination },
  data() {
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
        day: undefined
      },
      dataform: {
        id: undefined,
        name: undefined,
        code: undefined,
        state: undefined,
        remark: undefined,
        orderNum: undefined,
        day: undefined
      },
      // 新增修改弹框
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      pickerOptions: {
        shortcuts: [{
          text: '当前时间',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      // 规则默认都不能为空，请自行修改
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请输入状态', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入排序', trigger: 'blur' }
        ],
        day: [
          { required: true, message: '请输入日期', trigger: 'blur' }
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
        id: undefined,
        name: undefined,
        code: undefined,
        state: undefined,
        remark: undefined,
        orderNum: undefined,
        day: undefined
      }
    },
    getList() {
      this.listLoading = true
      const query = this.deepClone(this.listQuery)
      if (query.day) {
        query.day = query.day.join(',')
      }
      generateCodeInfo(query)
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
          createGenerateCode(this.dataform)
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
          updateGenerateCode(tempData)
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
      this.$confirm('确认删除该记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteGenerateCode(row.id)
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
    }
  }
}
</script>

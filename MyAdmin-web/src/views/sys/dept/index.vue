<template>
  <div class="app-container">

    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="部门简称" prop="deptName">
        <el-input v-model="listQuery.deptName" placeholder="部门简称，支持模糊搜索" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'dept_query'"  type="primary" icon="el-icon-search" @click="getList">
          搜索
        </el-button>
        <el-button v-waves v-permission="'dept_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'dept_add'" type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="deptId"
      :expand-row-keys="expands"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="部门简称" prop="deptName" align="left" width="200px"/>
      <el-table-column label="部门全称" prop="deptFullname" align="left" width="300px"/>
      <el-table-column label="排序" prop="orderNum" align="center"  width="60px" />
      <el-table-column label="修改时间" prop="updateTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="left"  min-width="200px" />
      <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'dept_edit'"  type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-permission="'dept_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog width="800px" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal='false'>
      <el-form class="dialog-from-margin" ref="formRef" :rules="rules" status-icon :model="dataform" label-position="left" label-width="80px">
        <el-form-item label="上级部门" prop="pid">
          <DeptSelect ref="deptSelect" :value.sync="dataform.pid" placeholder="请选择上级部门"/>
        </el-form-item>
        <el-form-item label="部门简称" prop="deptName">
          <el-input  clearable v-model="dataform.deptName" maxlength="16" show-word-limit placeholder="请输入部门简称"/>
        </el-form-item>
        <el-form-item label="部门全称" prop="deptFullname">
          <el-input  clearable v-model="dataform.deptFullname" maxlength="32" show-word-limit placeholder="请输入部门全称"/>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input-number controls-position="right" :min="0" clearable v-model.number="dataform.orderNum" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
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
  </div>
</template>

<script>
import { deptInfo, createDept, updateDept, deleteDept } from '@/api/dept'
import DeptSelect from '@/components/DeptSelect' // 部门选择下拉选

export default {
  name: 'dept',
  components: { DeptSelect },
  data() {
    return {
      list: [],
      listLoading: true,
      fullLoading: false,
      // 默认展开节点
      expands: [],
      listQuery: {
        deptName: undefined
      },
      dataform: {
        deptId: undefined,
        deptName: undefined,
        deptFullname: undefined,
        pid: undefined,
        orderNum: undefined,
        remark: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      rules: {
        deptName: [
          { required: true, message: '请输入部门简称', trigger: 'blur' },
          { max: 16, message: '长度必须小于16个字符', trigger: 'blur' }
        ],
        deptFullname: [
          { max: 32, message: '长度必须小于32个字符', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入排序顺序', trigger: 'blur' },
          { type: 'number', message: '排序顺序只支持数值', trigger: 'blur' }
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
      deptInfo(this.listQuery)
        .then(response => {
          this.expands = []
          this.list = response.data.result
          this.listLoading = false
          // 默认展开第一行
          if (this.list.length > 0) {
            this.expands.push(this.list[0].deptId.toString())
          }
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    resetTemp() {
      this.dataform = {
        deptId: undefined,
        deptName: undefined,
        deptFullname: undefined,
        pid: undefined,
        orderNum: undefined,
        remark: undefined
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        // 重新加载上级部门数据
        this.$refs.deptSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    createData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          this.fullLoading = true
          createDept(this.dataform)
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
        // 重新加载上级部门数据
        this.$refs.deptSelect.loadNodes()
        this.$refs['formRef'].clearValidate()
      })
    },
    updateData() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.dataform)
          this.fullLoading = true
          updateDept(tempData)
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
      this.$confirm('删除该记录，如果当前记录下包含子节点请先删除子节点，如果当前部门下存在用户请先解除关系，是否继续删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteDept(row.deptId)
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
    }
  }
}
</script>

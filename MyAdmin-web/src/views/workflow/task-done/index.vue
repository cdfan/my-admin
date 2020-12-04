<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="listQuery.taskName" placeholder="任务名称，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="所属流程" prop="procDefId">
        <el-select v-model="listQuery.procDefId" clearable filterable placeholder="请选择所属流程">
          <el-option
            v-for="item in procDefList"
            :key="item.procDefId"
            :label="item.procDefName"
            :value="item.procDefId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'taskDone_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'taskDone_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
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
      row-key="taskId"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="任务发起人" prop="startUserName" align="left" width="200px"/>
      <el-table-column label="任务名称" prop="taskName" align="left" width="200px"/>
      <el-table-column label="所属流程" prop="procName" align="left" min-width="200px"/>
      <el-table-column label="耗时" prop="elapsedTime"  align="center" width="150px"/>
      <el-table-column label="任务创建时间" prop="taskStartTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.taskStartTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务办理时间" prop="taskEndTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.taskEndTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'taskTodo_handle'" size="mini" type="primary" @click="handleDetails(row)">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <component ref="dialog" :is="dialogComponent" @query="handleFilter"></component>
  </div>
</template>

<script>
import { taskDoneInfo } from '@/api/task-manage'
import { getProcDefList } from '@/api/act-process'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'taskDone',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        taskName: undefined,
        procDefId: undefined
      },
      dialogComponent: undefined,
      // 流程定义集合
      procDefList: []
    }
  },
  created() {
    this.getList()
    this.getProcDefList()
  },
  methods: {
    getList() {
      this.listLoading = true
      taskDoneInfo(this.listQuery)
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
    getProcDefList() {
      getProcDefList()
        .then(response => {
          this.procDefList = response.data.result
        })
    },
    handleDetails(row) {
      if (!row.formKey) {
        this.$message({
          message: '查看失败，未获取到对应表单',
          type: 'error'
        })
        return false
      } else if (!row.businessKey) {
        this.$message({
          message: '查看失败，未获取到业务数据',
          type: 'error'
        })
        return false
      }
      this.dialogComponent = require(`@/form/${row.formKey}.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'details')
      })
    }
  }
}
</script>


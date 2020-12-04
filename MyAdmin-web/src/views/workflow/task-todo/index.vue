<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="listQuery.taskName" placeholder="任务名称，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'taskTodo_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'taskTodo_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
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
      <el-table-column label="发起人" prop="startUserName" align="left" width="200px"/>
      <el-table-column label="任务名称" prop="taskName" align="left" width="200px"/>
      <el-table-column label="所属流程" prop="procName" align="left" min-width="200px"/>
      <el-table-column label="流程状态" prop="procSuspended" align="center" width="100px">
        <template slot-scope="{row}">
          <el-tag v-if="!row.procSuspended" type="success">激活</el-tag>
          <el-tag v-if="row.procSuspended" type="warning">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="taskStartTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.taskStartTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'taskTodo_handle'" size="mini" type="primary" :disabled="row.procSuspended" @click="handleTask(row)">
            办理
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <component ref="dialog" :is="dialogComponent" @query="handleFilter"></component>
  </div>
</template>

<script>
import { taskTodoInfo } from '@/api/task-manage'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'taskTodo',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        taskName: undefined
      },
      dialogComponent: undefined
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      taskTodoInfo(this.listQuery)
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
    handleTask(row) {
      if (!row.formKey) {
        this.$message({
          message: '办理失败，未获取到对应表单',
          type: 'error'
        })
        return false
      } else if (!row.businessKey) {
        this.$message({
          message: '办理失败，未获取到业务数据',
          type: 'error'
        })
        return false
      }
      this.dialogComponent = require(`@/form/${row.formKey}.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'handle')
      })
    }
  }
}
</script>


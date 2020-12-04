<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="请假类型" prop="leaveType">
        <DictSelect dictTypeCode="leave_type" placeholder="请选择请假类型" :value.sync="listQuery.leaveType"/>
      </el-form-item>
      <el-form-item label="流程状态" prop="procState">
        <DictSelect dictTypeCode="proc_state" placeholder="请选择流程状态" :value.sync="listQuery.procState"/>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'leaveProcess_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'leaveProcess_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'leaveProcess_add'"  type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="leaveId"
      @sort-change="sortChange($event, listQuery, handleFilter)"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="任务状态" prop="procState" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
          <el-tag v-if="row.procState=='1'" type="success">未提交</el-tag>
          <el-tag v-if="row.procState=='2'">办理中</el-tag>
          <el-tag v-if="row.procState=='3'" type="danger">被驳回</el-tag>
          <el-tag v-if="row.procState=='4'" type="info">已完成</el-tag>
          <el-tag v-if="row.procState=='5'" type="warning">已撤销</el-tag>
          <el-tag v-if="row.procState=='6'" type="danger">已作废</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="请假类型" prop="leaveTypeName" align="center" width="110px" sortable="custom"/>
      <el-table-column label="当前任务" prop="taskName" align="left" width="150px"/>
      <el-table-column label="所属流程" prop="procName" align="left" min-width="200px"/>
      <el-table-column label="请假开始时间" prop="startTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.startTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假结束时间" prop="endTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.endTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="310px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'leaveProcess_query'" size="mini" type="info" @click="handleDetails(row)">
            详情
          </el-button>
          <el-button v-waves v-if="row.procState=='1'" v-permission="'leaveProcess_add'" size="mini" type="success" @click="handleTask(row)">
            提交
          </el-button>
          <el-button v-waves v-if="row.procState=='2'" v-permission="'leaveProcess_revoke'" size="mini" type="warning" @click="handleRevoke(row)">
            撤销
          </el-button>
          <el-button v-waves v-if="row.procState=='1'" v-permission="'leaveProcess_edit'"  size="mini" type="primary" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-if="row.procState=='1'||row.procState=='4'||row.procState=='5'||row.procState=='6'" v-permission="'leaveProcess_delete'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <component ref="dialog" :is="dialogComponent" @query="handleFilter"></component>
  </div>
</template>

<script>
import { leaveProcessInfo, deleteLeaveProcess, revokeLeaveProcess } from '@/api/leave-process'
import Pagination from '@/components/Pagination' // 分页
import DictSelect from '@/components/DictSelect' // 业务字典下拉选

export default {
  name: 'leaveProcess',
  components: { Pagination, DictSelect },
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
        leaveType: undefined,
        procState: undefined
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
      leaveProcessInfo(this.listQuery)
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
      this.dialogComponent = require(`@/form/LeaveForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleCreate()
      })
    },
    handleUpdate(row) {
      this.dialogComponent = require(`@/form/LeaveForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleUpdate(row)
      })
    },
    handleTask(row) {
      this.dialogComponent = require(`@/form/LeaveForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'handle')
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除该记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteLeaveProcess(row.leaveId)
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
    handleDetails(row) {
      this.dialogComponent = require(`@/form/LeaveForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'details')
      })
    },
    handleRevoke(row) {
      this.$confirm('确认撤销该申请？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        revokeLeaveProcess(row.leaveId)
          .then(() => {
            this.fullLoading = false
            this.notifyMessage()
            this.handleFilter()
          })
          .catch(() => {
            this.fullLoading = false
          })
      }).catch(() => {
        this.notifyMessage('info', '撤销取消！')
      })
    }
  }
}
</script>


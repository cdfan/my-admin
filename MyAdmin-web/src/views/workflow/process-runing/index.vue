<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="流程名称" prop="procName">
        <el-input v-model="listQuery.procName" placeholder="请输入流程名称" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="流程标识" prop="procKey">
        <el-input v-model="listQuery.procKey" placeholder="请输入流程标识" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="流程状态" prop="procSuspended">
        <el-select v-model="listQuery.procSuspended" clearable filterable placeholder="请选择流程状态">
          <el-option
            v-for="item in procStateOption"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'processRuning_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'processRuning_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
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
      row-key="procInstId"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="流程标识" prop="procKey" align="left" width="150px"/>
      <el-table-column label="流程名称" prop="procName" align="left" min-width="200px"/>
      <el-table-column label="流程版本" prop="procVersion" align="center" width="100px">
        <template slot-scope="{row}">
          <span>{{ "v." + row.procVersion }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流程发起人" prop="startUserName" align="left" width="150px"/>
      <el-table-column label="当前任务" prop="taskName" align="left" width="200px"/>
      <el-table-column label="流程状态" prop="procSuspended" align="center" width="100px">
        <template slot-scope="{row}">
          <el-tag v-if="!row.procSuspended" type="success">激活</el-tag>
          <el-tag v-if="row.procSuspended" type="warning">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="流程启动时间" prop="procStartTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.procStartTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'processRuning_query'" size="mini" type="primary" @click="handleDetails(row)">
            详情
          </el-button>
          <el-button v-if="!row.procSuspended" v-waves v-permission="'processRuning_edit'" size="mini" type="warning" @click="handleState(row)">
            挂起
          </el-button>
          <el-button v-if="row.procSuspended" v-waves v-permission="'processRuning_edit'" size="mini" type="success" @click="handleState(row)">
            激活
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <component ref="dialog" :is="dialogComponent" @query="handleFilter"></component>
  </div>
</template>

<script>
import { processRuningInfo, updateProcState } from '@/api/process-manage'
import Pagination from '@/components/Pagination' // 分页

// 运行中流程
export default {
  name: 'processRuning',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        procName: undefined,
        procKey: undefined,
        procSuspended: undefined
      },
      procStateOption: [{
        value: true,
        label: '挂起'
      }, {
        value: false,
        label: '激活'
      }],
      dialogComponent: undefined
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      processRuningInfo(this.listQuery)
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
    // 查看详情
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
    },
    // 修改流程状态
    handleState(row) {
      this.$confirm('确认修改该流程状态？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        updateProcState(row.procInstId)
          .then(() => {
            this.fullLoading = false
            this.notifyMessage()
            this.handleFilter()
          })
          .catch(() => {
            this.fullLoading = false
          })
      }).catch(() => {
        this.notifyMessage('info', '修改取消！')
      })
    }
  }
}
</script>


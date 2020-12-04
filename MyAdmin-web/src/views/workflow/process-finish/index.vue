<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="流程名称" prop="procName">
        <el-input v-model="listQuery.procName" placeholder="请输入流程名称" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="流程标识" prop="procKey">
        <el-input v-model="listQuery.procKey" placeholder="请输入流程标识" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="流程开始时间" prop="procStartTime">
        <el-date-picker
          v-model="listQuery.procStartTime"
          type="daterange"
          align="right"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item label="流程结束时间" prop="procEndTime">
        <el-date-picker
          v-model="listQuery.procEndTime"
          type="daterange"
          align="right"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'processFinish_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'processFinish_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
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
      <el-table-column label="流程开始时间" prop="procStartTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.procStartTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流程结束时间" prop="procEndTime" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.procEndTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" prop="elapsedTime" align="left" width="150px"/>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'processFinish_query'" size="mini" type="primary" @click="handleDetails(row)">
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
import { processFinishInfo } from '@/api/process-manage'
import Pagination from '@/components/Pagination' // 分页

// 已结束流程
export default {
  name: 'processFinish',
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
        procStartTime: [],
        procEndTime: []
      },
      dialogComponent: undefined,
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
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      const query = this.deepClone(this.listQuery)
      if (query.procStartTime) {
        query.procStartTime = query.procStartTime.join(',')
      }
      if (query.procEndTime) {
        query.procEndTime = query.procEndTime.join(',')
      }
      processFinishInfo(query)
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
    }
  }
}
</script>


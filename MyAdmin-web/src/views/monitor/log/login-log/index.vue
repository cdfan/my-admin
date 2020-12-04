<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="用户账号" prop="userCode">
        <el-input v-model="listQuery.userCode" placeholder="请输入用户账号" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="日志类型" prop="logType">
        <el-select v-model="listQuery.logType" clearable filterable placeholder="请选择日志类型">
          <el-option
            v-for="item in logTypeOption"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="listQuery.state" clearable filterable placeholder="请选择登录状态">
          <el-option
            v-for="item in stateOption"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="登录时间" prop="createTime">
        <el-date-picker
          v-model="listQuery.createTime"
          type="daterange"
          align="right"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'loginLog_query'"  type="primary" icon="el-icon-search" @click="handleFilter" >
          搜索
        </el-button>
        <el-button v-waves v-permission="'loginLog_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'loginLog_delete'"  type="danger" icon="el-icon-delete" :disabled="deleteBnt" @click="handleDelete" v-loading.fullscreen.lock="fullLoading">
          删除
        </el-button>
        <el-button  v-waves v-permission="'operationLog_export'" type="warning" icon="el-icon-download" @click="handleDownload">
          导出
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="table"
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      border
      stripe
      row-key="loginLogId"
      @sort-change="sortChange($event, listQuery, handleFilter, {sort: 'createTime', order: 'desc'})"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="50px" />
      <el-table-column label="用户账号" prop="userCode" align="left" width="110px" sortable="custom"/>
      <el-table-column label="登录时间" prop="createTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志类型" prop="logType" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
            <span v-if="row.logType=='0'">
              <el-tag type="warning">登出</el-tag>
            </span>
            <span v-if="row.logType=='1'">
              <el-tag>登录</el-tag>
            </span>
        </template>
      </el-table-column>
      <el-table-column label="登录状态" prop="state" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
          <span v-if="row.state=='0'">
              <el-tag type="danger">失败</el-tag>
            </span>
            <span v-if="row.state=='1'">
              <el-tag type="success">成功</el-tag>
            </span>
        </template>
      </el-table-column>
      <el-table-column label="登录ip" prop="ip" align="center" width="120px" sortable="custom"/>
      <el-table-column label="登录地址" prop="address" align="left" width="150px" sortable="custom"/>
      <el-table-column label="浏览器类型" prop="browser" align="left" width="120px" sortable="custom"/>
      <el-table-column label="操作系统" prop="os" align="left" width="150px" sortable="custom"/>
      <el-table-column label="描述" prop="msg" align="left" min-width="180px" sortable="custom"/>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { loginLogInfo, deleteLoginLog } from '@/api/login-log'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'loginLog',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      fullLoading: false,
      // 勾选的id集合
      ids: [],
      // 是否禁用删除按钮
      deleteBnt: true,
      listQuery: {
        page: 1,
        limit: 10,
        sort: 'createTime',
        order: 'desc',
        userCode: undefined,
        logType: undefined,
        createTime: [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')],
        state: undefined
      },
      stateOption: [{
        value: '1',
        label: '成功'
      }, {
        value: '0',
        label: '失败'
      }],
      logTypeOption: [{
        value: '1',
        label: '登录'
      }, {
        value: '0',
        label: '登出'
      }],
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
      if (query.createTime) {
        query.createTime = query.createTime.join(',')
      }
      loginLogInfo(query)
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
    handleDelete() {
      this.$confirm('确定删除选中记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteLoginLog(this.ids)
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
    handleSelectionChange(val) {
      this.ids = val.map(row => {
        return row.loginLogId
      })
      if (this.ids && this.ids.length > 0) {
        this.deleteBnt = false
      } else {
        this.deleteBnt = true
      }
    },
    handleRowClick(row, column, event) {
      this.$refs.table.toggleRowSelection(row)
    },
    handleDownload() {
      this.fullLoading = true
      import('@/utils/vendor/Export2Excel').then(excel => {
        // 如果当前数据的总数大于每页数，则说明list中的数据为分页数据，所以需要重新获取所有数据,否则list中存储的就是所有数据
        new Promise((resolve, reject) => {
          if (this.total > this.listQuery.limit) {
            const query = this.deepClone(this.listQuery)
            if (query.createTime) {
              query.createTime = query.createTime.join(',')
            }
            query.page = 1
            query.limit = 99999
            loginLogInfo(query)
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
          const tHeader = ['用户账号', '登录时间', '日志类型', '登录状态', '登录ip', '登录地址', '浏览器类型', '操作系统', '描述']
          const filterVal = ['userCode', 'createTime', 'logType', 'state', 'ip', 'address', 'browser', 'os', 'msg']
          const data = this.formatJson(filterVal, list)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: '登录日志'
          })
          this.fullLoading = false
          this.notifyMessage()
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(data => filterVal.map(val => {
        if (val === 'logType') {
          data[val] = data[val] === '1' ? '登录' : '登出'
        } else if (val === 'state') {
          data[val] = data[val] === '1' ? '成功' : '失败'
        } else if (val === 'createTime') {
          data[val] = this.parseTime(data[val], '{y}-{m}-{d} {h}:{i}')
        }
        return data[val]
      }))
    }
  }
}
</script>

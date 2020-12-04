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
      <el-form-item label="业务名称" prop="businessName">
        <el-input v-model="listQuery.businessName" placeholder="业务名称，支持模糊搜索" clearable @keyup.enter.native="handleFilter" />
      </el-form-item>
      <el-form-item label="操作时间" prop="createTime">
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
        <el-button v-waves v-permission="'operationLog_query'"  type="primary" icon="el-icon-search" @click="handleFilter" >
          搜索
        </el-button>
        <el-button v-waves v-permission="'operationLog_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'operationLog_delete'"  type="danger" icon="el-icon-delete" :disabled="deleteBnt" @click="handleDelete" v-loading.fullscreen.lock="fullLoading">
          删除
        </el-button>
        <el-button  v-waves v-permission="'operationLog_export'" type="warning" icon="el-icon-download" @click="handleDownload">
          导出
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      class="my-table-expand"
      ref="table"
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      border
      stripe
      row-key="operationLogId"
      :expand-row-keys="expands"
      @sort-change="sortChange($event, listQuery, handleFilter, {sort: 'createTime', order: 'desc'})"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand" >
        <template slot-scope="props">
          <el-form class="table-expand-title" label-position="left" label-width="85px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="方法名称">
                  <span>{{ props.row.methodName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="请求类型">
                  <span>{{ props.row.method }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="浏览器类型">
                  <span>{{ props.row.browser }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="操作系统">
                  <span>{{ props.row.os }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="类名称">
                  <span class="line-feed">{{ props.row.className }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="异常信息">
                  <el-button v-waves :disabled="props.row.logType==='1'" size="mini" type="danger" @click="openErrorMsgDetails(props.row)">
                    查看详情
                  </el-button>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="参数信息">
                  <span>{{ props.row.argMsg }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="50px" />
      <el-table-column label="用户账号" prop="userCode" align="left" width="110px" sortable="custom"/>
      <el-table-column label="日志类型" prop="logType" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
            <span v-if="row.logType=='0'">
              <el-tooltip effect="dark" content="点击查看异常信息" placement="bottom-start">
                <el-tag style="cursor:pointer" type="warning" @click="openErrorMsgDetails(row)" >失败日志</el-tag>
              </el-tooltip>
            </span>
            <span v-if="row.logType=='1'">
              <el-tag type="success">成功日志</el-tag>
            </span>
        </template>
      </el-table-column>
      <el-table-column label="业务操作名称" prop="businessName" align="left" min-width="200px" sortable="custom"/>
      <el-table-column label="操作时间" prop="createTime" align="center" width="140px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求地址" prop="uri" align="left" min-width="230px" sortable="custom"/>
      <el-table-column label="请求耗时" prop="elapsedTime" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
            <el-tag>{{row.elapsedTime}}ms</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="登录ip" prop="ip" align="center" width="120px" sortable="custom"/>
      <el-table-column label="登录地址" prop="address" align="left" width="150px" sortable="custom"/>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog width="1024px" title="异常信息" top="5vh" :visible.sync="dialogErrorMsg">
      <div v-highlight v-if="dialogErrorMsg">
        <pre class="exceptionMsg"><code class="java">{{errorMsg}}</code></pre>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { operationLogInfo, deleteOperationLog } from '@/api/operation-log'
import Pagination from '@/components/Pagination' // 分页
import highlight from '@/directive/highlight' //  代码样式

export default {
  name: 'operationLog',
  components: { Pagination },
  directives: { highlight },
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
      // 默认展开节点
      expands: [],
      // 异常信息弹框
      dialogErrorMsg: false,
      // 异常信息
      errorMsg: '',
      listQuery: {
        page: 1,
        limit: 10,
        sort: 'createTime',
        order: 'desc',
        userCode: undefined,
        logType: undefined,
        businessName: undefined,
        createTime: [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')]
      },
      logTypeOption: [{
        value: '1',
        label: '成功日志'
      }, {
        value: '0',
        label: '失败日志'
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
      operationLogInfo(query)
        .then(response => {
          this.expands = []
          this.list = response.data.result.records
          this.total = response.data.result.total
          this.listLoading = false
          // 默认展开第一行
          if (this.list.length > 0) {
            this.expands.push(this.list[0].operationLogId.toString())
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
    handleDelete() {
      this.$confirm('确定删除选中记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteOperationLog(this.ids)
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
        return row.operationLogId
      })
      if (this.ids && this.ids.length > 0) {
        this.deleteBnt = false
      } else {
        this.deleteBnt = true
      }
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
            operationLogInfo(query)
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
          const tHeader = ['用户账号', '日志类型', '业务操作名称', '操作时间', '请求地址', '请求类型', '请求耗时',
            '登录ip', '登录地址', '浏览器类型', '操作系统', '类名称', '方法名称', '参数信息', '异常信息']
          const filterVal = ['userCode', 'logType', 'businessName', 'createTime', 'uri', 'method', 'elapsedTime',
            'ip', 'address', 'browser', 'os', 'className', 'methodName', 'argMsg', 'exceptionMsg']
          // 给参数信息以及异常信息指定列宽，防止列宽太长
          const colsWidth = [{ 'colIndex': 13, 'colWidth': 100 }, { 'colIndex': 14, 'colWidth': 50 }]
          const data = this.formatJson(filterVal, list)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            colsWidth,
            filename: '操作日志'
          })
          this.fullLoading = false
          this.notifyMessage()
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(data => filterVal.map(val => {
        if (val === 'logType') {
          data[val] = data[val] === '1' ? '成功日志' : '失败日志'
        } else if (val === 'createTime') {
          data[val] = this.parseTime(data[val], '{y}-{m}-{d} {h}:{i}')
        }
        return data[val]
      }))
    },
    openErrorMsgDetails(row) {
      this.errorMsg = row.exceptionMsg
      this.dialogErrorMsg = true
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
    color: #99a9bf;
  }
}

// 处理添加展开行后，选择框移位问题
.my-table-expand{
  /deep/ .el-table__body .el-table__row .el-table-column--selection .cell {
    padding-left: 10px;
  }

}
.exceptionMsg{
  overflow: auto;
}
// 超过宽度换行
.line-feed{
  word-break: break-all;
}
</style>

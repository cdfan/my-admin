<template>
  <div class="app-container">
    <el-form :model="listQuery" ref="filterForm" :inline="true">
      <el-form-item label="商品" prop="goodsCode">
        <DictSelect dictTypeCode="goods_type" placeholder="请选择商品类型" :value.sync="listQuery.goodsCode"/>
      </el-form-item>
      <el-form-item label="流程状态" prop="shopProcState">
        <DictSelect dictTypeCode="shop_proc_state" placeholder="请选择流程状态" :value.sync="listQuery.shopProcState"/>
      </el-form-item>
      <el-form-item>
        <el-button v-waves v-permission="'shoppingProcess_query'"  type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves v-permission="'shoppingProcess_query'"  type="success" icon="el-icon-refresh" @click="resetForm('filterForm', listQuery, getList)">
          重置
        </el-button>
        <el-button v-waves v-permission="'shoppingProcess_add'"  type="primary" icon="el-icon-plus" @click="handleCreate" v-loading.fullscreen.lock="fullLoading">
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
      row-key="shopId"
      @sort-change="sortChange($event, listQuery, handleFilter)"
    >
      <el-table-column label="序号" type="index" width="50px" />
      <el-table-column label="任务状态" prop="shopProcState" align="center" width="110px" sortable="custom">
        <template slot-scope="{row}">
          <el-tag v-if="row.shopProcState=='1'" type="warning">未下单</el-tag>
          <el-tag v-if="row.shopProcState=='2'">待出库</el-tag>
          <el-tag v-if="row.shopProcState=='3'">配送中</el-tag>
          <el-tag v-if="row.shopProcState=='4'">待签收</el-tag>
          <el-tag v-if="row.shopProcState=='5'" type="success">已完成</el-tag>
          <el-tag v-if="row.shopProcState=='6'" type="danger">下单失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="商品名称" prop="goodsName" align="center" width="150px" sortable="custom"/>
      <el-table-column label="商品数量" prop="goodsCount" align="center" width="110px" sortable="custom"/>
      <el-table-column label="当前任务" prop="taskName" align="left" width="150px"/>
      <el-table-column label="所属流程" prop="procName" align="left" min-width="200px"/>
      <el-table-column label="创建时间" prop="createTime" align="center" width="150px" sortable="custom">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="310px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-waves v-permission="'shoppingProcess_query'" size="mini" type="info" @click="handleDetails(row)">
            详情
          </el-button>
          <el-button v-waves v-if="row.shopProcState=='1'" v-permission="'shoppingProcess_add'" size="mini" type="success" @click="handleTask(row)">
            提交
          </el-button>
          <el-button v-waves v-if="row.shopProcState=='1'" v-permission="'shoppingProcess_edit'"  size="mini" type="primary" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-waves v-if="row.shopProcState=='1' || row.shopProcState=='5' || row.shopProcState=='6'" v-permission="'shoppingProcess_delete'" size="mini" type="danger" @click="handleDelete(row)">
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
import { shoppingProcessInfo, deleteShoppingProcess } from '@/api/shopping-process'
import Pagination from '@/components/Pagination' // 分页
import DictSelect from '@/components/DictSelect' // 业务字典下拉选

export default {
  name: 'shoppingProcess',
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
        goodsCode: undefined,
        shopProcState: undefined
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
      shoppingProcessInfo(this.listQuery)
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
      this.dialogComponent = require(`@/form/ShoppingCustomerForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleCreate()
      })
    },
    handleUpdate(row) {
      this.dialogComponent = require(`@/form/ShoppingCustomerForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleUpdate(row)
      })
    },
    handleTask(row) {
      this.dialogComponent = require(`@/form/ShoppingCustomerForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'handle')
      })
    },
    handleDetails(row) {
      this.dialogComponent = require(`@/form/ShoppingDetailsForm.vue`).default
      this.$nextTick(() => {
        this.$refs.dialog.handleTask(row, 'details')
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除该记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.fullLoading = true
        deleteShoppingProcess(row.shopId)
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

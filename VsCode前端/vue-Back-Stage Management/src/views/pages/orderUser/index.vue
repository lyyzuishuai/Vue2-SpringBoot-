<template>
  <div class="app-container">
    <el-form :inline="true" :model="ordersQuery" class="demo-form-inline">
      <el-form-item label="订单编号">
        <el-input
          v-model="ordersQuery.ordersNumber"
          placeholder="请输入订单编号"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input
          v-model="ordersQuery.goodsName"
          placeholder="请输入商品名称"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="收货人姓名">
        <el-input
          v-model="ordersQuery.usersName"
          placeholder="请输入收货人姓名"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="收货人电话">
        <el-input
          v-model="ordersQuery.usersPhone"
          placeholder="请输入收货人电话"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="价格区间最小">
        <el-input
          v-model="ordersQuery.startPrice"
          type="number"
          placeholder="输入价格"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="价格区间最大">
        <el-input
          v-model="ordersQuery.endPrice"
          type="number"
          placeholder="输入价格"
          size="small"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="getOrdersQuery"
          >查询</el-button
        >
        <el-button
          type="warning"
          icon="el-icon-close"
          size="small"
          @click="resetData"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="50"> </el-table-column>
      <el-table-column prop="ordersNumber" label="订单编号"> </el-table-column>
      <el-table-column prop="goodsName" label="商品名称"> </el-table-column>
      <el-table-column prop="goodsNumber" label="订单数量"> </el-table-column>
      <el-table-column prop="ordersPrice" label="订单价格"> </el-table-column>
      <el-table-column prop="status" label="订单状态" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">
            <el-tag type="success">已下单</el-tag>
          </span>
          <span v-if="scope.row.status === 1">
            <el-tag type="success">已付款</el-tag>
          </span>
          <span v-if="scope.row.status === 2">
            <el-tag type="success">已发货</el-tag>
          </span>
          <span v-if="scope.row.status === 3">
            <el-tag type="success">已收货</el-tag>
          </span>
          <span v-if="scope.row.status === 4">
            <el-tag type="success">争议中</el-tag>
          </span>
        </template>
      </el-table-column>
    </el-table>
    <!--  分页显示-->
    <el-pagination
      :current-page="page"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getDate"
    />
  </div>
</template>

<script>
import ordersApi from "@/api/pages/order";
export default {
  name: "orederName",
  data() {
    return {
      list: [],
      page: 1,
      pageSize: 5,
      total: 0,
      ordersQuery: {},
    };
  },

  created() {
    this.getDate();
  },

  methods: {
    getDate(page = 1) {
      this.page = page;
      ordersApi
        .list(this.page, this.pageSize, this.ordersQuery)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },

    resetData() {
      this.ordersQuery = {};
      this.getDate();
    },

    //按照条件查询
    getOrdersQuery() {
      this.getDate();
    },
  },
};
</script>

<style>
</style>
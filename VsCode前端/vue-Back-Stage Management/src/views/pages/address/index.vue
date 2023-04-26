<template>
  <div class="app-container">
    <!--  搜索数据-->
    <el-form :inline="true" :model="addressQuery" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input
          v-model="addressQuery.users.username"
          placeholder="输入用户名"
          size="mini"
        />
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input
          v-model="addressQuery.users.nickname"
          placeholder="输入真实姓名"
          size="small"
        />
      </el-form-item>
      <el-form-item label="收货人">
        <el-input
          v-model="addressQuery.usersName"
          placeholder="输入收货人"
          size="small"
        />
      </el-form-item>
      <!-- <el-form-item label="收货电话">
        <el-input v-model="addressQuery.usersPhone" placeholder="输入收货电话" size="small" />
      </el-form-item> -->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="getAddressQuery"
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
      <div>
        <el-button
          type="success"
          icon="el-icon-circle-plus-outline"
          size="small"
          @click="add"
          >添加</el-button
        >
      </div>
    </el-form>
    <!--表格渲染数据-->
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="users.username" label="用户名" width="85" />
      <el-table-column prop="users.nickname" label="真实姓名" width="90" />
      <el-table-column prop="usersName" label="收货人" width="75" />
      <el-table-column prop="usersPhone" label="收货电话" width="110" />
      <el-table-column prop="addressName" label="收货地址" />
      <el-table-column prop="createTime" label="创建时间" width="150" />
      <!-- <el-table-column prop="postcode" label="邮编" width="70" /> -->
      <el-table-column label="操作" width="220">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="getUpdate(scope.row)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="getDelById(scope.row.addressId)"
            >删除</el-button
          >
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
      @current-change="getData"
    />
    <!-- 添加修改页面 -->
    <el-dialog title="添加/编辑地址" :visible.sync="addressVisible">
      <el-form :model="addressForm">
        <el-form-item label="用户id" :label-width="formLabelWidth" size="small">
          <el-input v-model="addressForm.usersId" type="text" size="small" />
        </el-form-item>
        <el-form-item label="收货人" :label-width="formLabelWidth" size="small">
          <el-input v-model="addressForm.usersName" type="text" size="small" />
        </el-form-item>
        <el-form-item
          label="收货电话"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="addressForm.usersPhone" type="text" size="small" />
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth" size="small">
          <el-input
            v-model="addressForm.addressName"
            type="text"
            size="small"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="addressVisible = false"
          >取 消</el-button
        >
        <el-button type="primary" size="small" @click="addOrUpdate"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import addressApi from "@/api/pages/address";

export default {
  name: "AddressList",
  data() {
    return {
      list: [],
      page: 1, // 当前第几页
      pageSize: 5, // 每页多少条数据
      total: 0,
      listUser: {},
      isDelete: 1,

      addressQuery: {
        users: {
          username: "",
          nickname: "",
        },
      }, // 条件查询对象
      addressVisible: false,
      formLabelWidth: "120px",

      addressForm: {
        addressId: 0,
        addressName: "",
        createTime: "",
        isDelete: 0,
        postcode: "",
        users: {
          nickname: "",
          username: "",
          usersId: "",
        },
        usersId: "",
        usersName: "",
        usersPhone: "",
      },
    };
  },
  created() {
    this.getData(); // 查询实现方法
    console.log(this.addressForm.users);
  },
  methods: {
    // 编辑修改
    getUpdate(row) {
      this.addressForm.usersId = row.usersId;
      this.addressForm.users.nickname = row.users.nickname;
      this.addressForm.addressId = row.addressId;
      this.addressForm.addressName = row.addressName;
      this.addressForm.usersPhone = row.usersPhone;
      this.addressForm.usersName = row.usersName;
      this.addressForm.postcode = row.postcode;
      this.addressVisible = true;
    },

    // 添加修改保存
    addOrUpdate() {
      // eslint-disable-next-line eqeqeq
      if (
        !this.addressForm.addressId == 0 ||
        !this.addressForm.addressId == null
      ) {
        addressApi.update(this.addressForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.addressVisible = true;
          this.addressForm = {};
          this.getData();
          this.addressVisible = false;
        });
      } else {
        addressApi.save(this.addressForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.addressVisible = true;
          this.addressForm = {};
          this.getData();
          this.addressVisible = false;
        });
      }
    },

    // 实现add()增加事件
    add() {
      this.addressVisible = true;
    },

    // 删除
    getDelById(id) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return addressApi.getDelById(id);
        })
        .then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.getData();
        })
        .catch((error) => {
          console.log("catch的error", error);
          if (error === "cancel") {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          }
        });
    },

    // 重置查询条件
    resetData() {
      this.addressQuery = {};
      this.addressQuery.users = {};
      this.getData();
    },

    // 按照条件查询
    getAddressQuery() {
      this.getData();
    },

    // 查询数据
    getData(page = 1) {
      this.page = page;
      addressApi
        .list(this.page, this.pageSize, this.addressQuery)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },
  },
};
</script>

<style>
</style>

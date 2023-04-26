<template>
  <div>
    <el-form :inline="true" :model="usersQuery" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input
          v-model="usersQuery.username"
          placeholder="输入用户名"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input
          v-model="usersQuery.nickname"
          placeholder="输入真实姓名"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="usersQuery.startTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="usersQuery.endTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="getusersQuery"
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
    <!-- 熏染列表 -->
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="50"> </el-table-column>
      <el-table-column prop="username" label="用户名"> </el-table-column>
      <el-table-column prop="nickname" label="真实姓名"> </el-table-column>
      <el-table-column prop="email" label="用户邮箱" width="180">
      </el-table-column>
      <el-table-column prop="sex" label="性别">
        <template slot-scope="scope">
          <span v-if="scope.row.sex === 0">
            <el-tag type="success">男</el-tag>
          </span>
          <span v-if="scope.row.sex === 1">
            <el-tag type="primary">女</el-tag>
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="用户介绍" width="280">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="220">
      </el-table-column>
      <el-table-column prop="remark" label="用户备注"> </el-table-column>
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
            @click="getDelById(scope.row.usersId)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!--  分页-->
    <el-pagination
      :current-page="page"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getData"
    />
    <div>
      <!--添加修改界面-->
      <el-dialog title="添加/编辑用户" :visible.sync="usersVisible">
        <el-form :model="usersForm">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="usersForm.username" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" :label-width="formLabelWidth">
            <el-input
              v-model="usersForm.password"
              type="password"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" :label-width="formLabelWidth">
            <el-input v-model="usersForm.nickname" size="small"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="formLabelWidth">
            <el-input v-model="usersForm.email" size="small"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth">
            <el-radio-group v-model="usersForm.sex" size="small">
              <el-radio label="0">男</el-radio>
              <el-radio label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="用户介绍" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              v-model="usersForm.description"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              v-model="usersForm.remark"
              size="small"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="usersVisible = false" size="small"
            >取 消</el-button
          >
          <el-button type="primary" @click="saveOrUpdate" size="small"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import usersApi from "@/api/pages/users";
export default {
  data() {
    return {
      list: [],
      page: 1, // 当前第几页
      pageSize: 5, // 每页多少条数据
      total: 0,
      usersVisible: false,
      formLabelWidth: "120px",
      usersQuery: {}, //条件查询对象
      usersForm: {
        //添加编辑对象
        usersId: 0,
        username: "",
        password: "",
        nickname: "",
        email: "",
        sex: 0,
        description: "",
        remark: "",
      },
    };
  },
  created() {
    this.getData();
    console.log(this.usersQuery);
  },
  methods: {
    //查询数据
    getData(page = 1) {
      this.page = page;
      usersApi
        .list(this.page, this.pageSize, this.usersQuery)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },
    getusersQuery() {
      this.getData();
    },

    // 重置
    resetData() {
      this.usersQuery = {};
      this.getData();
    },
    // 删除方法
    getDelById(id) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return usersApi.getDelById(id);
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
    //添加
    add() {
      this.usersForm = {};
      this.usersVisible = true;
    },
    // saveOrUpdate() {
    //   //添加操作
    //   usersApi.save(this.usersForm).then((response) => {
    //     this.$message({
    //       message: response.message,
    //       type: "success",
    //     });
    //     this.usersForm = {};
    //     this.usersVisible = false;
    //     this.getData();
    //   });
    // },
    //编辑修改
    getUpdate(row) {
      this.usersForm.usersId = row.usersId;
      this.usersForm.username = row.username;
      this.usersForm.nickname = row.nickname;
      this.usersForm.email = row.email;
      this.usersForm.sex = row.sex;
      this.usersForm.description = row.description;
      this.usersForm.remark = row.remark;
      this.usersVisible = true;
    },
    //添加、编辑提交数据
    saveOrUpdate() {
      if (!this.usersForm.usersId == 0 || !this.usersForm.usersId == null) {
        usersApi.update(this.usersForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.usersForm = {};
          this.usersVisible = false;
          this.getData();
        });
      } else {
        //添加操作
        usersApi.save(this.usersForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.usersForm = {};
          this.usersVisible = false;
          this.getData();
        });
      }
    },
  },
};
</script>

<style>
</style>
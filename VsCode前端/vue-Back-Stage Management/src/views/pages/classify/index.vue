<template>
  <div class="app-container">
    <!--  按照名称条件查询-->
    <el-form :inline="true" :model="classifyQuery" class="demo-form-inline">
      <el-form-item label="商品分类名称">
        <el-input
          v-model="classifyQuery.classifyName"
          placeholder="输入名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="getClassifyQuery"
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
    <el-button
      type="success"
      icon="el-icon-circle-plus-outline"
      size="small"
      @click="add"
      >添加</el-button
    >
    <!--表格渲染数据-->
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <!-- <el-table-column prop="classify_id" label="序列" width="100">
      </el-table-column> -->
      <el-table-column prop="classifyName" label="分类名称" width="100">
      </el-table-column>
      <el-table-column prop="description" label="分类描述" width="380">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"> </el-table-column>
      <el-table-column label="是否删除" prop="isDelete" width="100">
      </el-table-column>
      <el-table-column label="操作">
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
            @click="getDelById(scope.row.classifyId)"
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
      @current-change="getDate"
    />
    <!--  添加修改页面-->
    <div>
      <el-dialog title="添加/编辑商品分类" :visible.sync="classifyVisible">
        <el-form :model="classifyForm">
          <el-form-item
            label="分类名称"
            :label-width="formLabelWidth"
            prop="classifyName"
            size="small"
          >
            <el-input
              v-model="classifyForm.classifyName"
              autocomplete="off"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分类描述"
            :label-width="formLabelWidth"
            prop="description"
          >
            <el-input
              type="textarea"
              v-model="classifyForm.description"
              size="small"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="classifyVisible = false" size="small"
            >取 消</el-button
          >
          <el-button type="primary" @click="classifyFormData" size="small"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import classifyApi from "@/api/pages/classify";
export default {
  name: "classifyList",
  data() {
    return {
      list: [],
      page: 1, //当前第几页
      pageSize: 10, //每页多少条数据
      total: 0,
      classifyQuery: {},
      classifyForm: {
        classifyId: 0,
        isDelete: 0,
        classifyName: "",
        description: "",
      },
      classifyVisible: false, //控制页面是否显示
      formLabelWidth: "120px",
    };
  },
  created() {
    this.getDate();
  },
  methods: {
    //按照条件查询
    getClassifyQuery() {
      this.getDate();
    },

    getUpdate(row) {
      this.classifyForm.classifyId = row.classifyId;
      this.classifyForm.classifyName = row.classifyName;
      this.classifyForm.description = row.description;
      this.classifyVisible = true;
    },

    add() {
      this.classifyVisible = true;
    },

    //删除数据
    getDelById(id) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return classifyApi.getDelById(id);
        })
        .then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.getDate();
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

    //查询所有数据
    getDate(page = 1) {
      this.page = page;
      classifyApi
        .list(this.page, this.pageSize, this.classifyQuery)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },

    classifyFormData() {
      if (
        !this.classifyForm.classifyId == 0 ||
        !this.classifyForm.classifyId == null
      ) {
        //修改方法
        classifyApi.update(this.classifyForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.classifyForm = {};
          this.classifyVisible = false;
          this.getDate();
        });
      } else {
        //添加方法
        classifyApi.save(this.classifyForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.classifyForm = {};
          this.classifyVisible = false;
          this.getDate();
        });
      }
    },

    //重置查询条件
    resetData() {
      this.classifyQuery = {};
      this.getDate();
    },
  },
};
</script>
<style scoped>
</style>

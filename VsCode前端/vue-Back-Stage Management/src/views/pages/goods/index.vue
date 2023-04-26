<template>
  <div class="app-container">
    <!--  搜索数据-->
    <el-form :inline="true" :model="goodsQuery" class="demo-form-inline">
      <el-form-item label="商品名称">
        <el-input
          v-model="goodsQuery.goodsName"
          placeholder="输入名称"
          size="small"
        />
      </el-form-item>
      <el-form-item label="价格区间最小">
        <el-input
          v-model="goodsQuery.startPrice"
          type="number"
          placeholder="输入价格"
          size="small"
        />
      </el-form-item>
      <el-form-item label="价格区间最大">
        <el-input
          v-model="goodsQuery.endPrice"
          type="number"
          placeholder="输入价格"
          size="small"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="goodsQuery.startTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        />
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="goodsQuery.endTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="getGoodsQuery"
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
        <el-button type="success" icon="el-icon-plus" size="small" @click="add"
          >添加</el-button
        >
      </div>
    </el-form>
    <!--表格渲染数据-->
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="goodsName" label="商品名称" />
      <el-table-column prop="price" label="商品价格" width="100" />
      <el-table-column prop="specification" label="商品规格" width="150" />
      <!-- <el-table-column prop="description" label="商品描述" /> -->
      <el-table-column prop="particulars" label="商品详情" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="mini" @click="getOrders(scope.row)"
            >下单</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="goodsUpdate(scope.row)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="getDelById(scope.row.goodsId)"
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
    <!-- 添加修改页面 -->
    <el-dialog title="添加/编辑商品" :visible.sync="goodsFormVisible">
      <el-form :model="goodsForm">
        <el-form-item
          label="商品名称"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="goodsForm.goodsName" type="text" size="small" />
        </el-form-item>
        <el-form-item
          label="商品规格"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input
            v-model="goodsForm.specification"
            type="text"
            size="small"
          />
        </el-form-item>
        <el-form-item
          label="商品描述"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="goodsForm.description" type="text" size="small" />
        </el-form-item>
        <el-form-item
          label="商品详情"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="goodsForm.particulars" type="text" size="small" />
        </el-form-item>
        <el-form-item
          label="商品价格"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="goodsForm.price" type="number" size="small" />
        </el-form-item>
        <el-form-item
          label="商品备注"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-input v-model="goodsForm.remark" type="text" size="small" />
        </el-form-item>
        <el-form-item
          label="商品分类"
          :label-width="formLabelWidth"
          size="small"
        >
          <el-select v-model="goodsForm.classifyId" placeholder="请选择">
            <el-option
              v-for="item in listClassify"
              :key="item.classifyName"
              :label="item.classifyName"
              :value="item.classifyId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="goodsFormVisible = false"
          >取 消</el-button
        >
        <el-button type="primary" size="small" @click="addOrUpdate"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <el-dialog title="商品数量" :visible.sync="ordersVisible">
      <el-form :model="ordersForm">
        <el-form-item label="商品数量" :label-width="formLabelWidth">
          <el-input
            v-model="ordersForm.goodsNumber"
            type="number"
            size="small"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ordersVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="saveOrdersNum" size="small"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <!-- 地址选择 -->
    <el-dialog title="商品数量" :visible.sync="usersVisible">
      <el-form :inline="true" :model="userQuery" class="demo-form-inline">
        <el-form-item label="收货人姓名">
          <el-input
            v-model="userQuery.usersName"
            placeholder="输入收货人姓名"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="收货人电话">
          <el-input
            v-model="userQuery.usersPhone"
            placeholder="输入收货人电话"
            size="small"
          ></el-input>
        </el-form-item>
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
            @click="resetDatas"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <el-table :data="listUsers" style="width: 100%">
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column prop="users.username" label="用户名">
        </el-table-column>
        <el-table-column prop="users.nickname" label="真实姓名">
        </el-table-column>
        <el-table-column prop="usersName" label="收货人"> </el-table-column>
        <el-table-column prop="usersPhone" label="收货电话"> </el-table-column>
        <el-table-column prop="addressName" label="收货地址"> </el-table-column>
        <el-table-column prop="createTime" label="创建时间"> </el-table-column>
        <el-table-column prop="postcode" label="邮编"> </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="getUpdateUsers(scope.row)"
              >选择</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-pagination
      :current-page="pageNo"
      :page-size="pageData"
      :total="totals"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getDate"
    />
  </div>
</template>

<script>
import GoodsApi from "@/api/pages/goods";
import classify from "@/api/pages/classify";
import ordersApi from "@/api/pages/order";
import addressApi from "@/api/pages/address";

export default {
  name: "GoodsList",
  data() {
    return {
      list: [], // 绑定数据的数组
      page: 1, // 当前第几页
      pageSize: 5, // 每页多少条数据
      total: 0, // 总共多少条数据
      goodsQuery: {},
      goodsFormVisible: false, // 控制页面是否显示
      formLabelWidth: "120px",
      listClassify: {},

      goodsForm: {
        goodsId: 0,
        goodsName: "",
        specification: "",
        description: "",
        particulars: "",
        price: 0,
        classifyId: 0,
        remark: "",
        isDelete: 0,
      },

      queryOrders: {
        goodsName: "",
        specification: "",
        description: "",
        particulars: "",
        ordersPrice: 0,
        goodsNumber: 0,
        classifyId: 0,
        usersId: 0,
        usersName: "",
        usersPhone: "",
        addressName: "",
        postcode: "",
        remark: "",
      },
      ordersVisible: false,
      ordersForm: {
        goodsNumber: 0,
      },
      usersVisible: false,
      listUsers: [],
      pageNo: 1,
      pageData: 3,
      totals: 0,
      userQuery: {
        usersName: "",
        usersPhone: "",
      },
    };
  },

  created() {
    this.getDate(); // 查询实现方法
    this.listClassifies(); // 查询分类方法
  },

  methods: {
    // 实现getUpdate(scope.row)修改事件
    goodsUpdate(row) {
      this.goodsForm.goodsId = row.goodsId;
      this.goodsForm.goodsName = row.goodsName;
      this.goodsForm.specification = row.specification;
      this.goodsForm.description = row.description;
      this.goodsForm.price = row.price;
      this.goodsForm.classifyId = row.classifyId;
      this.goodsForm.particulars = row.particulars;
      this.goodsForm.remark = row.remark;
      this.goodsFormVisible = true;
    },

    // 添加修改保存
    addOrUpdate() {
      // eslint-disable-next-line eqeqeq
      if (!this.goodsForm.goodsId == 0 || !this.goodsForm.goodsId == null) {
        GoodsApi.update(this.goodsForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.goodsFormVisible = false;
          this.goodsForm = {};
          this.getDate();
        });
      } else {
        GoodsApi.addGoods(this.goodsForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          this.goodsFormVisible = false;
          this.goodsForm = {};
          this.getDate();
        });
      }
    },

    // 实现add()增加事件
    add() {
      this.goodsFormVisible = true;
      this.listClassifies();
    },

    // 分类列表
    listClassifies() {
      classify.listGoods().then((response) => {
        this.listClassify = response.data;
        console.log(response.data);
      });
    },

    // 重置查询条件
    resetData() {
      this.goodsQuery = {};
      this.getDate();
    },

    // 按照条件查询
    getGoodsQuery() {
      this.getDate();
    },

    // 删除数据
    getDelById(id) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return GoodsApi.getDelById(id);
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

    // 查询所有数据
    getDate(page = 1) {
      this.page = page;
      GoodsApi.list(this.page, this.pageSize, this.goodsQuery).then(
        (response) => {
          this.list = response.data.records;
          this.total = response.data.total;

          // 按照价格升序
          this.list.sort((bef, aft) => {
            return bef.price - aft.price;
          });
        }
      );
    },

    getOrders(row) {
      this.ordersForm = {};
      this.userQuery = {};
      this.queryOrders = {};
      this.queryOrders.goodsName = row.goodsName;
      this.queryOrders.specification = row.specification;
      this.queryOrders.description = row.description;
      this.queryOrders.particulars = row.particulars;
      this.queryOrders.ordersPrice = row.price;
      this.queryOrders.classifyId = row.classifyId;
      this.ordersVisible = true;
    },

    // 实现数量输入并赋值，同时查询地址信息，关闭数量输入窗口，打开地址信息窗口。
    saveOrdersNum() {
      this.queryOrders.goodsNumber = this.ordersForm.goodsNumber;
      this.ordersVisible = false;
      this.listUsersAddress();
      this.ordersForm = {};

      this.usersVisible = true;
    },

    // 查询地址信息方法。
    listUsersAddress(pages = 1) {
      this.pageNo = pages;
      addressApi
        .list(this.pageNo, this.pageData, this.userQuery)
        .then((response) => {
          this.listUsers = response.data.records;
          this.totals = response.data.total;
        });
    },
    // 条件查询实现
    getAddressQuery() {
      this.listUsersAddress();
    },
    // 重置
    resetDatas() {
      this.userQuery.users = {};
      this.userQuery = {};
      this.listUsersAddress();
    },
    // 提交方法实现
    ordersSave() {
      ordersApi.save(this.queryOrders).then((response) => {
        this.$message({
          message: response.message,
          type: "success",
        });
      });
    },
    getUpdateUsers(row) {
      this.queryOrders.usersId = row.users.usersId;
      this.queryOrders.usersName = row.usersName;
      this.queryOrders.usersPhone = row.usersPhone;
      this.queryOrders.addressName = row.addressName;
      this.queryOrders.postcode = row.postcode;
      this.ordersSave();
      this.usersVisible = false;
      this.ordersVisible = false;
      this.ordersForm = {};
      this.userQuery = {};
      this.queryOrders = {};
      this.getDate();
    },
  },
};
</script>

<style>
</style>

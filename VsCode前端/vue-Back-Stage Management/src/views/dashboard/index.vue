<template>
  <el-row>
    <el-col :span="8" style="padding-right: 10px">
      <el-card class="box-card">
        <div class="user">
          <img src="../../assets/images/kn.jpg" alt="" />
          <div class="userinfo">
            <p class="name">Admin</p>
            <p class="access">超级管理员</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登录时间: <span>2023.12.27</span></p>
          <p>上次登录地点: <span>湖北 宜昌</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px; height: 460px">
        <el-table :data="list" style="width: 100%">
          <el-table-column type="index" label="序号" width="50">
          </el-table-column>
          <el-table-column prop="ordersNumber" label="订单编号">
          </el-table-column>
          <el-table-column prop="goodsNumber" label="订单数量">
          </el-table-column>
          <el-table-column prop="ordersPrice" label="订单价格">
          </el-table-column>
          <el-table-column prop="goodsName" label="商品名称"> </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="16" style="padding-left: 10px">
      <div class="num">
        <el-card
          v-for="item in countData"
          :key="item.name"
          :body-style="{ display: 'flex', padding: 0 }"
        >
          <i
            class="icon"
            :class="`el-icon-${item.icon}`"
            :style="{ background: item.color }"
          ></i>
          <div class="detail">
            <p class="price">￥{{ item.value }}</p>
            <p class="desc">{{ item.name }}</p>
          </div>
        </el-card>
      </div>
      <el-card style="height: 280px">
        <div class="main1" style="height: 280px"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 260px">
          <div class="main2" style="height: 260px"></div>
        </el-card>
        <el-card style="height: 260px">
          <div class="main3" style="height: 260px"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import ordersApi from "@/api/pages/order";
import * as echarts from "echarts";
export default {
  name: "orederName",
  data() {
    return {
      list: [],
      page: 1,
      pageSize: 5,
      total: 0,
      ordersQuery: {},
      orderForm: {
        ordersNumber: "",
        ordersPrice: "",
      },
      countData: [
        {
          value: 1234,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          value: 210,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          value: 1234,
          icon: "s-goods",
          color: "#5ab1ef",
        },
        {
          value: 1234,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          value: 210,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          value: 1234,
          icon: "s-goods",
          color: "#5ab1ef",
        },
      ],

      // 商品序号和商品价格柱状图
      option: {
        title: {
          text: "商品价格柱状图",
        },

        // 横坐标获取后台商品序号
        xAxis: {
          data: [],
        },
        yAxis: [
          {
            type: "value",
            axisLine: {
              lineStyle: {
                color: "#17b3a3",
              },
            },
          },
        ],
        color: ["#2ec7c9", "#b6a2de"],

        // 纵坐标获取后台商品价格
        series: [
          {
            name: "商品价格",
            type: "bar",
            axisLine: {
              lineStyle: {
                color: "#17b3a3",
              },
            },
            data: [],
          },
        ],
      },

      // 商品编号和价格折线图
      option2: {
        title: {
          text: "商品状态折线图",
        },
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["商品编号", "商品状态"],
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        toolbox: {
          feature: {
            saveAsImage: {},
          },
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
        },
        yAxis: {
          type: "value",
        },

        series: [
          {
            name: "商品编号",
            type: "line",
            stack: "Total",
            data: [],
          },
          {
            name: "商品状态",
            type: "line",
            stack: "Total",
            data: [],
          },
        ],
      },

      // 饼状图
      option3: {
        title: {
          text: "商品价格饼状图",
          subtext: "Fake Data",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: "50%",
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      },
    };
  },
  mounted() {
    var myChart2 = this.$echarts.init(document.querySelector(".main1"));
    myChart2.setOption(this.option2);
    this.myChart2 = myChart2;

    var myChart = this.$echarts.init(document.querySelector(".main2"));
    myChart.setOption(this.option);
    this.myChart = myChart;

    var myChart3 = this.$echarts.init(document.querySelector(".main3"));
    this.myChart3 = myChart3;
    myChart3.setOption(this.option3);
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
          console.log(this.list);

          // Echarts 绘制动态图表
          for (var i = 0; i < this.list.length; i++) {
            this.option.xAxis.data.push(i);
            this.option.series[0].data.push(this.list[i].ordersPrice);
            this.option2.xAxis.data.push(i);
            this.option2.series[0].data.push(this.list[i].goodsNumber);
            this.option2.series[1].data.push(this.list[i].status);
            this.option3.series[0].data.push({
              value: this.list[i].ordersPrice,
              name: this.list[i].goodsNumber,
            });
          }
          this.myChart.setOption(this.option);
          this.myChart2.setOption(this.option2);
          this.myChart3.setOption(this.option3);
        });
    },
  },
};
</script>

<style lang="less" scoped>
.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  img {
    margin-right: 40px;
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }
  .userinfo {
    .name {
      font-size: 32px;
      margin-bottom: 10px;
    }
    .access {
      color: #999999;
    }
  }
}
.login-info {
  p {
    line-height: 28px;
    font-size: 14px;
    color: #999999;
    span {
      color: #666666;
      margin-left: 60px;
    }
  }
}

.num {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  .icon {
    width: 80px;
    height: 80px;
    font-size: 30px;
    text-align: center;
    line-height: 80px;
    color: #fff;
  }
  .detail {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .el-card {
    width: 32%;
    margin-bottom: 20px;
  }
  .price {
    font-size: 30px;
    margin-bottom: 10px;
    line-height: 30px;
    height: 30px;
  }
  .desc {
    font-size: 14px;
    color: #999;
    text-align: center;
  }
}
.graph {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  .el-card {
    width: 48%;
  }
}
</style>
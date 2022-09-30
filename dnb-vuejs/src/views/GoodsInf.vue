<template>
  <div class="tab-container">
    <template>
      <el-select
        v-model="value"
        @change="loadTable"
        clearable
        placeholder="Select Warehouse"
      >
        <el-option
          v-for="item in options"
          :key="item.warehouseNo"
          :label="item.warehouseNo"
          :value="item.warehouseNo"
        >
        </el-option>
      </el-select>
    </template>

    <el-row>
      <el-col :span="12">
        <el-table :data="tableData" height="603" stripe style="width: 100%">
          <el-table-column prop="goodsId" label="Goods ID" width="180">
          </el-table-column>
          <el-table-column prop="color" label="Goods Color" width="180">
          </el-table-column>
          <el-table-column prop="positionNo" label="Position No" width="180">
          </el-table-column>
          <el-table-column prop="modify" label="Modify" width="180">
            <template slot-scope="scope">
              <!-- 不加的话dropdown会失效 -->
              <el-dropdown>
                <el-button type="primary">
                  Color<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <!-- 此处必须有.native否则无效 -->
                  <el-dropdown-item
                    @click.native="changeGoodsColor(scope.row.goodsId, 'red')"
                  >
                    Red
                  </el-dropdown-item>
                  <el-dropdown-item
                    @click.native="changeGoodsColor(scope.row.goodsId, 'green')"
                  >
                    Green</el-dropdown-item
                  >
                  <el-dropdown-item
                    @click.native="changeGoodsColor(scope.row.goodsId, 'blue')"
                  >
                    Blue</el-dropdown-item
                  >
                  <el-dropdown-item
                    @click.native="
                      changeGoodsColor(scope.row.goodsId, 'yellow')
                    "
                  >
                    Yellow</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
          <div slot="empty" style="text-align: left">
            <el-empty description="Opps! Please select a warehouse ⬆️" />
          </div>
        </el-table>
      </el-col>
      <el-col :span="12">
        <div id="main" style="width: 800px; height: 600px"></div>
      </el-col>
    </el-row>
    <div style="padding: 10px 0">
      <el-pagination
        :page-sizes="[5, 10]"
        :page-size="pageSize"
        :current-page="pageNum"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>


<script>
function download(filename, text, type = "text/plain") {
  // Create an invisible A element
  const a = document.createElement("a");
  a.style.display = "none";
  document.body.appendChild(a);

  // Set the HREF to a Blob representation of the data to be downloaded
  a.href = window.URL.createObjectURL(new Blob([text], { type }));

  // Use download attribute to set set desired file name
  a.setAttribute("download", filename);

  // Trigger the download by simulating click
  a.click();

  // Cleanup
  window.URL.revokeObjectURL(a.href);
  document.body.removeChild(a);
}

import * as echarts from "echarts";

export default {
  data() {
    return {
      goods: {
        goodsId: "",
        color: "",
        positionNo: "",
      },
      options: [],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      value: "",
    };
  },
  mounted() {
    this.getWarehouse();
  },
  methods: {
    loadTable() {
      this.request
        .get(
          "/goods/warehouse/" +
            this.value +
            "/list/page?pageNum=" +
            this.pageNum +
            "&pageSize=" +
            this.pageSize
        )
        .then((res) => {
          // console.log(res)
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
      this.loadChart();
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize;
      this.loadTable();
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum;
      this.loadTable();
    },
    getWarehouse() {
      this.request
        .get(
          "/warehouse/manager/list/page?pageNum=1&pageSize=999&managerId=" +
            sessionStorage.getItem("managerId")
        )
        .then((res) => {
          this.options = res.data.records;
        });
    },
    // CSV
    csv() {
      let csv_content = "";

      this.request
        .get(
          "/worker/manager/list/page?pageNum=1&pageSize=10000&managerId=" +
            sessionStorage.getItem("managerId")
        )
        .then((res) => {
          csv_content += "Worker ID";
          csv_content += ",Worker Name";
          csv_content += ",Warehouse No.";
          csv_content += ",Manager ID\n";
          for (let i = 0; i < res.data.total; ++i) {
            csv_content += res.data.records[i].workerId.toString();
            csv_content += "," + res.data.records[i].workerName;
            csv_content += "," + res.data.records[i].warehouseNo;
            csv_content += "," + res.data.records[i].managerId + "\n";
          }
          console.log("CSV content: ");
          console.log(csv_content);
          download("goods.csv", csv_content);
        });
    },
    changeGoodsColor(goodsId, targetColor) {
      this.request
        .get(
          "/goods/changegoodscolor?goodsId=" +
            goodsId.toString() +
            "&targetColor=" +
            targetColor.toString()
        )
        .then((res) => {
          window.location.reload(true);
        });
    },
    loadChart() {
      var chartDom = document.getElementById("main");
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        color: ["#F56C6C", "#67C23A", "#409EFF", "#E6A23C"],
        series: [
          {
            name: "",
            type: "pie",
            radius: "50%",
            data: [
              { value: 0, name: "Red", label: { color: "#F56C6C" } },
              { value: 0, name: "Green", label: { color: "#67C23A" } },
              { value: 0, name: "Blue", label: { color: "#409EFF" } },
              { value: 0, name: "Yellow", label: { color: "#E6A23C" } },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      this.request
        .get("/echarts/position/" + this.value + "/color")
        .then((res) => {
          // option.series[0].data = res.data
          option.series[0].data[0].value = res.data[0];
          option.series[0].data[1].value = res.data[1];
          option.series[0].data[2].value = res.data[2];
          option.series[0].data[3].value = res.data[3];
          console.log(option.series[0]);
          myChart.setOption(option);
        });
    },
  },
};
</script>

<style scoped>
</style>
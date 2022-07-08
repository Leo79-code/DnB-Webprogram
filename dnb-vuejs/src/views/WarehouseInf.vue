<template>
  <div class="tab-container">
    <template>
      <el-select v-model="value" @change="loadTable" clearable placeholder="Select Warehouse">
        <el-option
            v-for="item in options"
            :key="item.warehouseNo"
            :label="item.warehouseNo"
            :value="item.warehouseNo">
        </el-option>
      </el-select>
    </template>

    <el-row>
      <el-col :span="12">
    <el-table
        :data="tableData"
        height="550"
        stripe
        style="width: 100%">
      <el-table-column
          prop="positionNo"
          label="Position No"
          width="180">
      </el-table-column>
      <el-table-column
          prop="strAvailable"
          label="Available"
          width="180">
      </el-table-column>
      <el-table-column
          prop="warehouseNo"
          label="Warehouse No"
          width="180">
      </el-table-column>
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
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>


<script>
import * as echarts from 'echarts'

export default {
  data() {
    return {
      position: {
        positionNo: "",
        strAvailable: "",
        warehouseNo: "",
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
    this.getWarehouse()
  },
  methods: {
    loadTable() {
      this.request.get("/position/warehouse/" + this.value + "/list/page?pageNum=" + this.pageNum + "&pageSize="
          + this.pageSize).then(res => {
        // console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.loadChart()
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize
      this.loadTable()
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum
      this.loadTable()
    },
    getWarehouse() {
      this.request.get("/warehouse/manager/list/page?pageNum=1&pageSize=999&managerId="
          + sessionStorage.getItem('managerId')).then(res => {
        this.options = res.data.records
      })
    },
    loadChart() {
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name:"",
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {value: 0, name: 'Available'},
              {value: 0, name: 'Occupied'}
            ],

          },
        ]
      };

        this.request.get("/echarts/position/" + this.value + "/occupy").then(res => {
          option.series[0].data[0].value = res.data[0]
          option.series[0].data[1].value = res.data[1]
          // console.log(option.series[0])
          myChart.setOption(option);
        })
      }
    }
}
</script>

<style scoped>

</style>
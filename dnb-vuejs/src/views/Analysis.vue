<template>
  <div>
    <template>
      <el-select v-model="value" @change="drawChart" placeholder="Select Warehouse" style="margin-bottom: 10px">
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
        <div id="line1" style="width: 700px; height: 600px"></div>
      </el-col>
      <el-col :span="12">
        <div id="line2" style="width: 700px; height: 600px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "dataAnalysis",
  data() {
    return {
      options: [],
      value: ''
    }
  },
  mounted() {
    this.getWarehouse()
  },
  methods: {
    drawChart() {
      var chartDom1 = document.getElementById('line1');
      var myChart1 = echarts.init(chartDom1);
      var option1;

      var chartDom2 = document.getElementById('line2');
      var myChart2 = echarts.init(chartDom2);
      var option2;

      option1 = {
        title: {
          text: 'No. ' + this.value + ' Warehouse quarterly deposit records'
        },
        xAxis: {
          type: 'category',
          data: ['Q1', 'Q2', 'Q3', 'Q4']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line'
          }
        ]
      };

      option2 = {
        title: {
          text: 'No. ' +this.value + ' Warehouse quarterly outbound records'
        },
        xAxis: {
          type: 'category',
          data: ['Q1', 'Q2', 'Q3', 'Q4']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line'
          }
        ]
      };

      this.request.get("/echarts/warehouse/" + this.value + "/deposit").then(res => {
        option1.series[0].data = res.data
        myChart1.setOption(option1);
      })

      this.request.get("/echarts/warehouse/" + this.value + "/takeout").then(res => {
        option2.series[0].data = res.data
        myChart2.setOption(option2);
      })
    },
    getWarehouse() {
      this.request.get("/warehouse/manager/list/page?pageNum=1&pageSize=999&managerId="
          + sessionStorage.getItem('managerId')).then(res => {
        this.options = res.data.records
      })
    }
  }

}

</script>

<style scoped>

</style>
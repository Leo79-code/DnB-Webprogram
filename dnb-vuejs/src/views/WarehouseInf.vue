<template>
  <div class="tab-container">
    <template>
      <el-select v-model="value" @change="loadTable(); Disabled = false" clearable placeholder="Select Warehouse">
        <el-option
            v-for="item in options"
            :key="item.warehouseNo"
            :label="item.warehouseNo"
            :value="item.warehouseNo">
        </el-option>
      </el-select>
      <el-button
          :disabled="Disabled"
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click='handleCreate(); getPosition()'
      >Add new Goods
      </el-button>
      <el-button
          :disabled="Disabled"
          class="filter-item"
          style="margin-left: 10px;"
          type="warning"
          icon="el-icon-delete"
          @click='handleDelete()'
      >Delete Goods
      </el-button>
    </template>
    <el-dialog :visible.sync="dialogFormVisible1" style="width: 800px">
      <el-form
          label-position="top"
          label-width="70px"
          style="width: 250px; margin-left:50px;"
      >
        <el-form-item label="Goods ID" prop="goodsId">
          <el-input v-model="goods.goodsId"/>
        </el-form-item>
        <el-form-item label="Goods Color" prop="color">
          <el-select v-model="colorSel" clearable placeholder="Select PositionNo">
            <el-option
                v-for="color in colorOptions"
                :key="color.value"
                :label="color.label"
                :value="color.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Position No" prop="positionNo">
          <el-select v-model="positionSel" clearable placeholder="Select PositionNo">
            <el-option
                v-for="item in positionOptions"
                :key="item.positionNo"
                :label="item.positionNo"
                :value="item.positionNo">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">Quit</el-button>
        <el-button type="primary" @click=" createGoods() ">Confirm</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisible2" style="width: 800px">
      <el-form
          label-position="top"
          label-width="70px"
          style="width: 250px; margin-left:50px;"
      >
        <el-form-item label="Goods ID" prop="toDelGoodsId">
          <el-input v-model="toDelGoodsId"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">Quit</el-button>
        <el-button type="warning" @click=" deleteGoods() ">Delete</el-button>
      </div>
    </el-dialog>
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
              label="Is Empty"
              width="180">
          </el-table-column>
          <el-table-column
              prop="goodsId"
              label="Goods ID"
              width="180">
          </el-table-column>
          <el-table-column
              prop="color"
              label="Color"
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
      Disabled: true,
      goods: {
        goodsId: "",
        color: "",
        positionNo: "",
      },
      toDelGoodsId: "",
      options: [],
      positionOptions: [],
      colorOptions: [{
        value: 'red',
        label: 'Red'
      },
        {
          value: 'green',
          label: 'Green'
        },
        {
          value: 'yellow',
          label: 'Yellow'
        },
        {
          value: 'blue',
          label: 'Blue'
        }],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      value: "",
      positionSel: "",
      colorSel: "",
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
    getPosition() {
      this.request.get("/position/warehouse/" + this.value + "/empty/page?pageNum=1&pageSize=999").then(res => {
        this.positionOptions = res.data.records
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
            name: "",
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
    },
    handleCreate() {
      this.goods = {
        goodsId: "",
        color: "",
        positionNo: "",
        managerId: sessionStorage.getItem('managerId'),
      }
      this.dialogFormVisible1 = true;
    },
    handleDelete() {
      this.toDelGoodsId = ""
      this.dialogFormVisible2 = true;
    },
    //Create Button Confirm
    async createGoods() {
      this.goods.color = this.colorSel
      this.goods.positionNo = this.positionSel

      this.request.post("/goods/add", this.goods).then(res => {
        if (res.state === "SUCCESS") {
          this.$message.success("Created Successfully!")
          this.load()
          this.dialogFormVisible1 = false
        } else {
          this.$message.error("Sorry, your input is wrong.")
        }
      })
    },
    async deleteGoods() {
      this.request.delete("/goods/" + this.toDelGoodsId).then(res => {
        if (res.state === "SUCCESS") {
          this.$message.success("Deleted Successfully!")
          this.load()
        } else {
          this.$message.error("Error")
          this.load()
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
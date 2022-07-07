<template>
  <div class="tab-container">
    <el-button @click="load()">Load</el-button>
    <el-table
        :data="tableData"
        height="250"
        stripe
        style="width: 100%">
      <el-table-column
          prop="warehouseNo"
          label="Warehouse No"
          width="180">
      </el-table-column>
      <el-table-column
          prop="managerId"
          label="Manager ID"
          width="180">
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          :current-page="pageNum"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
    >Create
    </el-button>
    <el-dialog :visible.sync="dialogFormVisible">
      <el-form
          label-position="top"
          label-width="90px"
          style="width: 600px; margin-left:50px;"
      >
        <el-form-item label="Warehouse No" prop="warehouseNo">
          <el-input v-model="warehouse.warehouseNo"/>
        </el-form-item>
        <el-form-item label="Manager ID" prop="managerId">
          <el-input v-model="warehouse.managerId"/>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Quit</el-button>
        <el-button type="primary" @click=" createData() ">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
export default {
  data() {
    return {
      warehouse: {
        warehouseNo: "",
        managerId: "",
      },
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      dialogFormVisible: false,
    };

  },
  methods: {
    created() {
      this.load()
    },
    load() {
      this.request.get("/warehouse/manager/list/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleCreate() {
      this.warehouse = {
        warehouseNo: "",
        managerId: "",
      };
      this.dialogFormVisible = true;
    },
    //Create Button Confirm
    async createData() {
      this.request.post("/warehouse/change", this.warehouse).then(res => {
        if (res.state === "SUCCESS") {
          this.$message.success("Created Successfully!")
        } else {
          this.$message.error("Sorry, your input is wrong.")
        }
      })
    }
  },
}
</script>

<style scoped>

</style>
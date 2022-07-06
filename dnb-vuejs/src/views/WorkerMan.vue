<template>
  <div>
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
    <el-button style="margin-top: 10px;" @click="create()">Create a new warehouse</el-button>
  </div>
</template>


<script>
export default {
  name: "WarehouseMan",
  created() {
    this.load();
  },
  data() {
    return {
      workerId: "",
      workName: "",
      warehouseNo: "",
      managerId:"",
      tableData: [],
    }
  },
  methods: {
    load() {
      this.request.get("/worker", {
        params: {
          warehouseNo: this.warehouseNo,
          managerId: this.managerId,
        }
      }).then(res => {

        this.tableData = res.data
        this.total = res.data.total
      })
    },
    create() {
      this.request.post("/warehouse", this.managerId).then(res => {
        if (!res) {
          this.$message.error("Creation filed")
        } else {
          this.$message.success("New Warehouse Created!")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
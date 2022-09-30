<template>
  <div class="tab-container">
    <el-col :span="3.5">
      <el-input
        placeholder="Search by name"
        v-focus
        v-model="fuzzySearch.name"
        clearable
      >
        <el-button
          slot="append"
          icon="el-icon-search"
          @click="searchWorkerByName()"
        ></el-button>
      </el-input>
    </el-col>

    <el-button
      class="filter-item"
      style="margin-left: 10px"
      type="primary"
      icon="el-icon-edit"
      @click="handleCreate"
      >Create
    </el-button>

    <el-button
      style="margin-left: 10px"
      type="success"
      icon="el-icon-download"
      @click="csv()"
      >Download as CSV file
    </el-button>

    <el-dialog :visible.sync="dialogFormVisible" style="width: 800px">
      <el-form
        label-position="top"
        label-width="70px"
        style="width: 250px; margin-left: 50px"
      >
        <el-form-item label="Worker Name" prop="workerName">
          <el-input v-model="worker.workerName" />
        </el-form-item>
        <el-select
          v-model="value"
          @change
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Quit</el-button>
        <el-button type="primary" @click="createData()">Confirm</el-button>
      </div>
    </el-dialog>
    <el-table :data="tableData" height="603" stripe style="width: 100%">
      <el-table-column prop="workerId" label="Worker ID" width="180">
      </el-table-column>
      <el-table-column prop="workerName" label="Worker Name" width="180">
      </el-table-column>
      <el-table-column prop="warehouseNo" label="Warehouse No" width="180">
      </el-table-column>
      <el-table-column prop="managerId" label="Manager ID" width="180">
      </el-table-column>
      <el-table-column label="" width="90">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.workerId)"
            >Delete
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleUpdate(scope.row.workerId, scope.row.workerName)"
            >Update
          </el-button>
        </template>
      </el-table-column>
      <div slot="empty" style="text-align: left">
        <el-empty description="Loading……" />
      </div>
    </el-table>
    <el-dialog :visible.sync="dialogFormVisible2" style="width: 800px">
      <el-form
        label-position="top"
        label-width="70px"
        style="width: 250px; margin-left: 50px"
      >
        <el-select
          v-model="value2"
          @change
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">Quit</el-button>
        <el-button type="primary" @click="updateWorker()">Confirm</el-button>
      </div>
    </el-dialog>
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
function download(filename, text, type = "text/csv") {
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

import axios from "axios";
export default {
  data() {
    return {
      queryInfo: {
        query: "",
      },
      worker: {
        workerId: "",
        workerName: "",
        warehouseNo: "",
        managerId: "",
      },
      workerUpdate: {
        workerId: "",
        workerName: "",
        warehouseNo: "",
        managerId: "",
      },
      fuzzySearch: {
        name: "",
      },
      tableData: [],
      options: [],
      value: "",
      value2: "",
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      dialogFormVisible2: false,
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request
        .get(
          "/worker/manager/list/page?pageNum=" +
            this.pageNum +
            "&pageSize=" +
            this.pageSize +
            "&managerId=" +
            sessionStorage.getItem("managerId")
        )
        .then((res) => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },
    // 模糊查找 worker
    searchWorkerByName() {
      this.request
        .get(
          "/worker/searchworkerbyname?name=" +
            this.fuzzySearch.name +
            "&pageNum=" +
            this.pageNum.toString() +
            "&pageSize=" +
            "100"
          // this.pageSize.toString()
        )
        .then((res) => {
          this.tableData = res.data.records;
          this.total = res.data.total;
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
          download("worker.csv", csv_content);
        });
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
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum;
      this.load();
    },
    handleCreate() {
      this.worker = {
        workerId: "",
        workerName: "",
        warehouseNo: "",
        managerId: "",
      };
      this.dialogFormVisible = true;
      this.getWarehouse();
    },
    handleDelete(workerId) {
      this.request.delete("/worker/" + workerId).then((res) => {
        if (res.state === "SUCCESS") {
          this.$message.success("Deleted Successfully!");
          this.load();
        } else {
          this.$message.error("Error");
          this.load();
        }
      });
    },
    handleUpdate(workerId, workerName) {
      this.workerUpdate = {
        warehouseNo: "",
        managerId: sessionStorage.getItem("managerId"),
        workerId: workerId,
        workerName: workerName,
      };
      this.dialogFormVisible2 = true;
      this.getWarehouse();
    },
    async createData() {
      this.worker.managerId = sessionStorage.getItem("managerId");
      this.worker.warehouseNo = this.value;
      this.request.post("/worker/change", this.worker).then((res) => {
        if (res.state === "SUCCESS") {
          this.$message.success("Created Successfully!");
          this.load();
          this.dialogFormVisible = false;
        } else {
          this.$message.error("Sorry, your input is wrong.");
        }
      });
    },
    async updateWorker() {
      this.workerUpdate.warehouseNo = this.value2;
      this.request.post("/worker/change", this.workerUpdate).then((res) => {
        if (res.state === "SUCCESS") {
          this.$message.success("Updated Successfully!");
          this.load();
          this.dialogFormVisible = false;
        } else {
          this.$message.error("Sorry, your input is wrong.");
        }
      });
    },
  },
  watch: {
    workerName: {
      handler: function () {
        this.axios
          .post(this.baseUrl + "/web_search", { keyword: this.workerName })
          .then((res) => {
            console.log(res.data.data);
            this.tableData = res.data.data;
          });
        console.log(this.workerName);
      },
    },
  },
};
</script>

<style scoped>
</style>
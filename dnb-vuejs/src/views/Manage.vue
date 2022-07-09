<template>
  <div class="menu-wrap">
    <el-container>
      <el-header>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="1">
            <router-link to="/manage/warehouseman" tag="span" slot="title">Warehouse Management</router-link>
          </el-menu-item>
          <el-menu-item index="2">
            <router-link to="/manage/workerman" tag="span" slot="title">Worker Management</router-link>
          </el-menu-item>
          <el-menu-item index="3">
            <router-link to="/manage/warehouseinf" tag="span" slot="title">Warehouse Information</router-link>
          </el-menu-item>
          <el-menu-item index="4">
            <router-link to="/manage/goodsinf" tag="span" slot="title">Goods Information</router-link>
          </el-menu-item>
          <el-menu-item index="5">
            <router-link to="/manage/analysis" tag="span" slot="title">Data Analysis</router-link>
          </el-menu-item>
          <div>
            <el-dropdown style="width: 50px; cursor: pointer; text-align: right; float: right">
              <div style="display: inline-block">
                <el-avatar icon="el-icon-user-solid" style="margin-top: 10px"></el-avatar>
              </div>
              <el-dropdown-menu slot="dropdown" style="width: 150px; text-align: center">
                <span>Welcome, {{ manager.managerName }}</span><i style="font-size: 1px; margin-left: 5px"></i>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0;">
                  <el-button type="danger" icon="el-icon-circle-close" style="float: none;" round @click="logOut()">Log
                    out
                  </el-button>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0;">
                  <el-button
                      round
                      type="primary"
                      icon="el-icon-edit"
                      @click="handleEdit"
                  >修改个人信息
                  </el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dialog :visible.sync="dialogFormVisible" style="width: 800px; margin-left: 60%">
              <el-form
                  label-position="top"
                  style="width: 350px;"
              >
                <el-form-item label="New Password" prop="managerPassword">
                  <el-input type="password" show-password v-model="manager.managerPassword"/>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Quit</el-button>
                <el-button type="primary" @click="updatePW()">Confirm</el-button>
              </div>
            </el-dialog>
          </div>
        </el-menu>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>

    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      manager: {
        managerName: sessionStorage.getItem('managerName'),
        managerId: sessionStorage.getItem('managerId'),
        managerPassword: "",
      },
      activeIndex: '1',
      dialogFormVisible: false,
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
    },
    logOut() {
      sessionStorage.clear()
      this.$router.push('/')
    },
    handleEdit() {
      this.manager = {
        managerName: sessionStorage.getItem('managerName'),
        managerId: sessionStorage.getItem('managerId'),
        managerPassword: "",
      }
      this.dialogFormVisible = true;
    },
    async updatePW() {
      this.manager.managerId = sessionStorage.getItem('managerId')
      this.request.post("/manager/change", this.manager).then(res => {
        if (res.state === "SUCCESS") {
          this.$message.success("Updated Successfully!");
          this.load();
          this.dialogFormVisible = false;
        } else {
          this.$message.error("Sorry, your input is wrong.")
        }
      })
    }
  }
}
</script>

<style scoped>
.menu-wrap {
  width: 100%;
  height: 100%;
  background-image: url("../assets/background.jpeg");
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}
</style>
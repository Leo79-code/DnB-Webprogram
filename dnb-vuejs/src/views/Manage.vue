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
            <el-avatar :src="avatarURL" style="margin-top: 10px; margin-left: 380px"></el-avatar>
            <el-dropdown trigger="click" style="width: 120px; height: 60px; cursor: pointer; text-align: right; float: right">
              <div style="display: inline-block">
                <span style="margin-left: 5px">Welcome, {{ manager.managerName }}</span><i style="font-size: 34px; margin-left: 5px"></i>
                <i class="el-icon-arrow-down"></i>
           </div>
              <el-dropdown-menu slot="dropdown" style="width: 150px; text-align: center">
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
            <!--修改manager个人信息对话框-->
            <el-dialog :visible.sync="dialogFormVisible" >
              <el-form
                  :rules="editUserFormRules"
                  ref="editUserFormRef"
                  label-position="top"
                  style="width: 350px;"
              >
              <el-form-item label="Id" prop="managerId">
                <el-input v-model="manager.managerId" disabled></el-input>
              </el-form-item>
              <el-form-item label="Name" prop="managerName">
                <el-input v-model="manager.managerName"></el-input>
              </el-form-item>  
              <el-form-item label="New Password" prop="managerPassword">
                <el-input v-model="manager.managerPassword" type="password" show-password></el-input>
              </el-form-item>
              <el-form-item label="Email" prop="managerEmail">
                <el-input v-model="manager.managerEmail"></el-input>
              </el-form-item>
              <el-form-item label="Mobile" prop="managerMobile">
                <el-input v-model="manager.managerMobile"></el-input>
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
        managerPassword: sessionStorage.getItem('managerPassword'),
        managerEmail: sessionStorage.getItem('managerEmail'),
        managerMobile: sessionStorage.getItem('managerMobile'),
      },
      activeIndex: '1',
      dialogFormVisible: false,
      editUserFormRules: {
              managerName: [{required:true,message:'请输入用户名',trigger:'blur'},
              {min:3,max:10,message:'用户名长度在3~10个字符',trigger:'blur'}],
              managerPassword: [{required:true,message:'请输入密码',trigger:'blur'},
              {min:3,max:15,message:'密码长度在3~15个字符',trigger:'blur'}],
              managerEmail: [{required:true,message:'请输入邮箱',trigger:'blur'},{trigger:'blur'}],
              managerMobile: [{required:true,message:'请输入手机号',trigger:'blur'},{trigger:'blur'}]
            },
      avatarURL: "https://gravatar.loli.net/avatar/" + sessionStorage.managerEmailHash,
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
        managerPassword: sessionStorage.getItem('managerPassword'),
        managerEmail: sessionStorage.getItem('managerEmail'),
        managerMobile: sessionStorage.getItem('managerMobile'),
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
    },
    save() {
          this.request.post("/user", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()

              this.$emit('refreshUser')
            } else {
              this.$message.error("保存失败")
            }
          })
        },
        handleAvatarSuccess(res) {
          // avatar_url = "https://gravatar.loli.net/avatar/" + sessionStorage.managerEmailHash;
          this.form.avatar_url = res
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
.el-main {
    height: 728px;
}
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
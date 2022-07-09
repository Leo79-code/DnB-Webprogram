<template>
  <div class="login-wrap">
    <el-form class="login-container">
      <h1 class="title">Warehouse Manage System</h1>
      <el-form-item label="Account">
        <el-input size="medium" prefix-icon="el-icon-user" v-model="manager.managerId"
                  placeholder="Account" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input size="medium" prefix-icon="el-icon-lock" type="password" show-password
                  v-model="manager.managerPassword" placeholder="Password" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="hCaptcha">
        <vue-hcaptcha sitekey="b6ed4f4a-93b1-453f-b411-cdeaa3358ead" @verify="onVerify"></vue-hcaptcha>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" style="width: 100%;" @click="login()" disabled id="submit_btn">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import VueHcaptcha from '@hcaptcha/vue-hcaptcha';
export default {

  name: 'login',
  components: { VueHcaptcha },
  data: function () {
    return {
      manager: {
        managerId: "",
        managerPassword: "",
        token: "",
      }
    }
  },
  methods: {
    onVerify(token, ekey) {
      console.log(`hCaptcha token: ${token}, ekey: ${ekey}`);
      this.manager.token = token;

      const submit_btn = document.getElementById('submit_btn');
      submit_btn.className = "el-button el-button--primary el-button--small";
      submit_btn.disabled = "";
    },
    login() {
      this.request.post("/manager/login", this.manager).then(res => {
        if (res.state === "SUCCESS") {
          // this.$cookie.set('JSESSIONID',res.data)
          sessionStorage.setItem("managerName",res.data.managerName);
          sessionStorage.setItem("managerId",res.data.managerId);
          this.$router.push("/manage")
        } else {
          this.$message.error("Sorry, your account and password did not match.")
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-image: url("../assets/background.jpeg");
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}

.login-container {
  border-radius: 10px;
  margin: 0px auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
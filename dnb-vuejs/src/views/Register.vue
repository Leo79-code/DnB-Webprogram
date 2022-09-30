<template>
  <div id="login-div">
    <el-card shadow="always">
      <div id="register-header">
        <span>Manager Registration</span>
      </div>

      <div id="register-form">
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="25%"
        >
          <el-form-item label="Name" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item prop="email" label="Email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="pass">
            <el-input
              type="password"
              v-model="ruleForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="Confirm Password" prop="checkPass">
            <el-input
              type="password"
              v-model="ruleForm.checkPass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-check"
              type="primary"
              @click="submitForm('ruleForm')"
              >Submit</el-button
            >
            <el-button icon="el-icon-delete" @click="resetForm('ruleForm')"
              >Reset</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Password cannot be empty!"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please input password again!"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("Password mismatch!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        name: "",
        email: "",
        pass: "",
        checkPass: "",
      },
      rules: {
        name: [{ required: true }],
        email: [
          {
            required: true,
            message: "Iuput email address!",
            trigger: "blur",
          },
          {
            type: "email",
            message: "Email address invalid!",
            trigger: ["blur", "change"],
          },
        ],
        pass: [{ required: true, validator: validatePass, trigger: "blur" }],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.request
            .post("/manager/register", {
              name: this.ruleForm.name,
              email: this.ruleForm.email,
              password: this.ruleForm.pass,
            })
            .then((res) => {
              alert("Register completed! Remember your ID for login: " + res.msg);

              window.location.replace("/login");
            });
        } else {
          console.log("Failed to submit!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
#login-div {
  max-width: 95%;
  min-width: 60%;
  width: 960px;

  margin: auto;
  padding-top: 5em;
  padding-bottom: 5em;

  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

#register-header {
  padding-bottom: 1em;
  text-align: center;
  font: bold;
  font-size: 2em;
}

#register-form {
  padding-right: 5em;
}
</style>
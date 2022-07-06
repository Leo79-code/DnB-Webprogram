<template>
  <div>
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
        <el-button icon=" el-icon-circle-close" style="float: right;" @click="logOut()">Log out</el-button>
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
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
    },
    logOut() {
      this.$router.push('/')
    }
  }
}
</script>

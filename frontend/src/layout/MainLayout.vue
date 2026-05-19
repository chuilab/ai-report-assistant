<template>
  <el-container class="main-layout">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '220px'" class="aside">
      <div class="logo">
        <el-icon :size="24" color="#409eff">
          <DataAnalysis />
        </el-icon>
        <span v-show="!isCollapse" class="logo-text">AI 报表助手</span>
      </div>

      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        class="menu"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <template #title>工作台</template>
        </el-menu-item>

        <el-menu-item index="/reports">
          <el-icon><Document /></el-icon>
          <template #title>报表管理</template>
        </el-menu-item>

        <el-menu-item index="/upload">
          <el-icon><Upload /></el-icon>
          <template #title>上传文件</template>
        </el-menu-item>

        <el-menu-item index="/analysis">
          <el-icon><TrendCharts /></el-icon>
          <template #title>数据分析</template>
        </el-menu-item>

        <el-menu-item index="/history">
          <el-icon><Clock /></el-icon>
          <template #title>历史记录</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <!-- 顶部栏 -->
      <el-header class="header">
        <div class="header-left">
          <el-icon
            class="collapse-btn"
            @click="isCollapse = !isCollapse"
          >
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-dropdown>
            <div class="user-info">
              <el-avatar :size="32" :icon="UserFilled" />
              <span class="username">{{ userStore.userInfo?.name || '用户' }}</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人设置</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区 -->
      <el-main class="main">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import {
  DataAnalysis,
  HomeFilled,
  Document,
  Upload,
  TrendCharts,
  Clock,
  Fold,
  Expand,
  UserFilled
} from '@element-plus/icons-vue'

const route = useRoute()
const userStore = useUserStore()

// 侧边栏折叠状态
const isCollapse = ref(false)

// 当前激活菜单
const activeMenu = computed(() => route.path)

// 当前页面标题
const currentTitle = computed(() => (route.meta.title as string) || '')

// 退出登录
function handleLogout() {
  userStore.clearUserInfo()
  window.location.href = '/login'
}
</script>

<style scoped lang="scss">
.main-layout {
  height: 100vh;
}

.aside {
  background: #fff;
  border-right: 1px solid #e4e7ed;
  transition: width 0.3s;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  border-bottom: 1px solid #e4e7ed;

  .logo-text {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    white-space: nowrap;
  }
}

.menu {
  border-right: none;
  height: calc(100vh - 60px);
}

.header {
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;

  .collapse-btn {
    font-size: 20px;
    cursor: pointer;
    color: #606266;

    &:hover {
      color: #409eff;
    }
  }
}

.header-right {
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;

    .username {
      font-size: 14px;
      color: #303133;
    }
  }
}

.main {
  background: #f5f7fa;
  overflow-y: auto;
}

// 路由切换动画
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
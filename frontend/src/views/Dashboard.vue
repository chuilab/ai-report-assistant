<template>
  <div class="dashboard">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">AI 报表助手</h1>
      <p class="page-desc">智能分析您的数据，生成专业报表</p>
    </div>

    <!-- 功能卡片 -->
    <div class="card-grid">
      <div class="feature-card" v-for="item in features" :key="item.title">
        <div class="feature-icon">
          <el-icon :size="32" :color="item.color">
            <component :is="item.icon" />
          </el-icon>
        </div>
        <h3 class="feature-title">{{ item.title }}</h3>
        <p class="feature-desc">{{ item.desc }}</p>
      </div>
    </div>

    <!-- 测试区域 -->
    <div class="card test-section">
      <h2 class="card-title">接口测试</h2>
      <div class="test-content">
        <p class="test-desc">点击按钮测试后端接口连接状态</p>
        <el-button
          type="primary"
          :loading="loading"
          @click="handleTest"
        >
          测试接口
        </el-button>
        <div v-if="testResult" class="test-result">
          <el-alert
            :title="testResult.success ? '连接成功' : '连接失败'"
            :type="testResult.success ? 'success' : 'error'"
            :description="testResult.message"
            show-icon
          />
        </div>
      </div>
    </div>

    <!-- 快速操作 -->
    <div class="card quick-actions">
      <h2 class="card-title">快速操作</h2>
      <div class="action-grid">
        <el-button @click="$router.push('/upload')">
          <el-icon><Upload /></el-icon>
          上传 Excel
        </el-button>
        <el-button @click="$router.push('/reports')">
          <el-icon><Document /></el-icon>
          查看报表
        </el-button>
        <el-button @click="$router.push('/analysis')">
          <el-icon><TrendCharts /></el-icon>
          数据分析
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, markRaw } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Upload,
  Document,
  TrendCharts,
  Clock,
  DataAnalysis,
  ChatDotRound
} from '@element-plus/icons-vue'
import { testApi } from '@/api/test'

// 功能列表
const features = reactive([
  {
    title: 'Excel 上传',
    desc: '支持多种格式 Excel 文件上传，自动解析数据结构',
    icon: markRaw(Upload),
    color: '#409eff'
  },
  {
    title: 'AI 分析',
    desc: '智能分析数据趋势，自动生成洞察报告',
    icon: markRaw(ChatDotRound),
    color: '#67c23a'
  },
  {
    title: '图表展示',
    desc: '丰富的可视化图表，直观呈现数据价值',
    icon: markRaw(TrendCharts),
    color: '#e6a23c'
  },
  {
    title: '历史记录',
    desc: '完整保存分析历史，随时回溯查看',
    icon: markRaw(Clock),
    color: '#909399'
  }
])

// 测试状态
const loading = ref(false)
const testResult = ref<{
  success: boolean
  message: string
} | null>(null)

// 测试接口
async function handleTest() {
  loading.value = true
  testResult.value = null

  try {
    const res = await testApi()
    testResult.value = {
      success: true,
      message: `服务器响应: ${res.message} (时间: ${new Date(res.timestamp).toLocaleString()})`
    }
    ElMessage.success('接口连接成功')
  } catch (error: any) {
    testResult.value = {
      success: false,
      message: error.message || '接口连接失败，请检查后端服务是否启动'
    }
    ElMessage.error('接口连接失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.dashboard {
  padding: 20px;
}

.page-header {
  margin-bottom: 24px;

  .page-title {
    font-size: 28px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 8px;
  }

  .page-desc {
    font-size: 14px;
    color: #909399;
  }
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 20px;
  margin-bottom: 24px;
}

.feature-card {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.04);
  transition: transform 0.2s, box-shadow 0.2s;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.08);
  }

  .feature-icon {
    width: 56px;
    height: 56px;
    border-radius: 12px;
    background: #f5f7fa;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 16px;
  }

  .feature-title {
    font-size: 16px;
    font-weight: 500;
    color: #303133;
    margin-bottom: 8px;
  }

  .feature-desc {
    font-size: 13px;
    color: #909399;
    line-height: 1.6;
  }
}

.card {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.04);
  margin-bottom: 20px;

  .card-title {
    font-size: 16px;
    font-weight: 500;
    color: #303133;
    margin-bottom: 16px;
    padding-bottom: 12px;
    border-bottom: 1px solid #ebeef5;
  }
}

.test-section {
  .test-content {
    display: flex;
    flex-direction: column;
    gap: 16px;

    .test-desc {
      font-size: 14px;
      color: #606266;
    }

    .test-result {
      max-width: 500px;
    }
  }
}

.quick-actions {
  .action-grid {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
  }
}
</style>
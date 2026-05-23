<template>
  <div class="dashboard">
    <div class="page-header">
      <h1 class="page-title">工作台</h1>
      <p class="page-desc">销售数据概览与分析</p>
    </div>

    <el-space direction="vertical" :size="20" fill>
      <!-- 快速操作 -->
      <el-card shadow="never">
        <div class="quick-actions">
          <el-button type="primary" @click="$router.push('/upload')">
            <el-icon><Upload /></el-icon>
            上传 Excel 数据
          </el-button>
          <span class="action-hint" v-if="!hasData">请先上传 Excel 文件以查看数据分析</span>
          <span class="action-hint" v-else>已加载 {{ salesData.length }} 条数据</span>
        </div>
      </el-card>

      <!-- 销售趋势图 -->
      <el-card v-if="hasData" shadow="never">
        <template #header>
          <span class="card-header-title">销售趋势图</span>
        </template>
        <SalesChart :data="salesData" />
      </el-card>

      <el-divider v-if="hasData" />

      <!-- AI 数据分析 -->
      <el-card v-if="aiAnalysis" shadow="never">
        <template #header>
          <div class="card-header-row">
            <span class="card-header-title">AI 数据分析</span>
            <el-tag type="success" size="small">AI 生成</el-tag>
          </div>
        </template>
        <div class="ai-content" v-html="renderedAnalysis"></div>
      </el-card>

      <el-card v-if="!aiAnalysis && hasData" shadow="never">
        <template #header>
          <span class="card-header-title">AI 数据分析</span>
        </template>
        <div class="card-placeholder">
          <p>点击按钮，让 AI 智能分析您的销售数据</p>
          <el-button
            type="primary"
            :loading="analyzing"
            @click="handleAnalyze"
          >
            {{ analyzing ? 'AI 分析中...' : '开始 AI 分析' }}
          </el-button>
        </div>
      </el-card>

      <el-divider v-if="aiAnalysis" />

      <!-- AI 自动日报 -->
      <el-card v-if="dailyReportText" shadow="never">
        <template #header>
          <div class="card-header-row">
            <span class="card-header-title">AI 自动日报</span>
            <el-tag type="warning" size="small">AI 生成</el-tag>
          </div>
        </template>
        <div class="ai-content" v-html="renderedDaily"></div>
      </el-card>

      <el-card v-if="!dailyReportText && aiAnalysis" shadow="never">
        <template #header>
          <span class="card-header-title">AI 自动日报</span>
        </template>
        <div class="card-placeholder">
          <p>基于数据分析结果，一键生成销售日报</p>
          <el-button
            type="success"
            :loading="generating"
            @click="handleGenerateDaily"
          >
            {{ generating ? '生成中...' : '生成 AI 日报' }}
          </el-button>
        </div>
      </el-card>

      <!-- 空状态 -->
      <el-card v-if="!hasData" shadow="never">
        <div class="empty-state">
          <el-icon :size="64" color="#c0c4cc"><TrendCharts /></el-icon>
          <p class="empty-title">暂无数据</p>
          <p class="empty-desc">上传 Excel 文件后，此处将展示销售趋势图、AI 分析和自动日报</p>
        </div>
      </el-card>
    </el-space>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload, TrendCharts } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import SalesChart from '@/components/chart/SalesChart.vue'
import { analyzeReport, dailyReport } from '@/api/report'

interface SalesRecord {
  date: string
  sales: number
  orders: number
  users?: number
}

const salesData = ref<SalesRecord[]>([])
const hasData = ref(false)
const analyzing = ref(false)
const generating = ref(false)
const aiAnalysis = ref('')
const dailyReportText = ref('')

// 尝试从 sessionStorage 恢复数据
const cached = sessionStorage.getItem('dashboard_sales_data')
if (cached) {
  try {
    const parsed = JSON.parse(cached)
    if (parsed.length > 0) {
      salesData.value = parsed
      hasData.value = true
    }
  } catch { /* ignore */ }
}

function renderMarkdown(text: string): string {
  return text
    .replace(/### (.+)/g, '<h3>$1</h3>')
    .replace(/## (.+)/g, '<h2>$1</h2>')
    .replace(/# (.+)/g, '<h2>$1</h2>')
    .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
    .replace(/- (.+)/g, '<li>$1</li>')
    .replace(/(\d+)\. (.+)/g, '<li>$1. $2</li>')
    .replace(/\n\n/g, '</p><p>')
    .replace(/\n/g, '<br>')
    .replace(/<li>/g, '<ul><li>')
    .replace(/<\/li>/g, '</li></ul>')
}

const renderedAnalysis = ref('')
const renderedDaily = ref('')

async function handleAnalyze() {
  if (salesData.value.length === 0) return
  analyzing.value = true
  try {
    const res = await analyzeReport(salesData.value)
    aiAnalysis.value = res.summary
    renderedAnalysis.value = renderMarkdown(res.summary)
    ElMessage.success('AI 分析完成')
  } catch (err: any) {
    ElMessage.error(err.message || 'AI 分析失败')
  } finally {
    analyzing.value = false
  }
}

async function handleGenerateDaily() {
  if (salesData.value.length === 0) return
  generating.value = true
  try {
    const res = await dailyReport(salesData.value)
    dailyReportText.value = res.summary
    renderedDaily.value = renderMarkdown(res.summary)
    ElMessage.success('日报生成完成')
  } catch (err: any) {
    ElMessage.error(err.message || '日报生成失败')
  } finally {
    generating.value = false
  }
}

// 暴露方法供外部调用（ExcelUpload 跳转后传数据）
function loadData(data: SalesRecord[]) {
  salesData.value = data
  hasData.value = data.length > 0
  aiAnalysis.value = ''
  dailyReportText.value = ''
  renderedAnalysis.value = ''
  renderedDaily.value = ''
  sessionStorage.setItem('dashboard_sales_data', JSON.stringify(data))
}

defineExpose({ loadData })
</script>

<style scoped lang="scss">
.dashboard {
  padding: 20px;
  max-width: 1200px;
}

.page-header {
  margin-bottom: 24px;

  .page-title {
    font-size: 24px;
    font-weight: 600;
    color: #303133;
    margin: 0 0 6px;
  }

  .page-desc {
    font-size: 14px;
    color: #909399;
    margin: 0;
  }
}

.quick-actions {
  display: flex;
  align-items: center;
  gap: 16px;

  .action-hint {
    font-size: 13px;
    color: #909399;
  }
}

.card-header-title {
  font-size: 15px;
  font-weight: 500;
  color: #303133;
}

.card-header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.card-placeholder {
  text-align: center;
  padding: 32px 0;
  color: #909399;

  p {
    margin: 0 0 16px;
    font-size: 14px;
  }
}

.ai-content {
  line-height: 1.8;
  color: #303133;
  font-size: 14px;

  :deep(h2) {
    font-size: 16px;
    margin: 16px 0 8px;
    color: #303133;
  }

  :deep(h3) {
    font-size: 15px;
    margin: 12px 0 6px;
    color: #606266;
  }

  :deep(p) {
    margin: 6px 0;
  }

  :deep(ul), :deep(ol) {
    padding-left: 20px;
    margin: 6px 0;
  }

  :deep(li) {
    margin: 4px 0;
  }

  :deep(strong) {
    color: #303133;
  }
}

.empty-state {
  text-align: center;
  padding: 60px 0;

  .empty-title {
    font-size: 16px;
    color: #909399;
    margin: 16px 0 8px;
  }

  .empty-desc {
    font-size: 13px;
    color: #c0c4cc;
    margin: 0;
  }
}
</style>

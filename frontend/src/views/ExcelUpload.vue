<template>
  <div class="excel-upload">
    <el-card>
      <template #header>
        <span>Excel 文件上传</span>
      </template>

      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :limit="1"
        accept=".xlsx,.xls"
        drag
        :file-list="fileList"
        :on-exceed="handleExceed"
        :on-change="handleFileChange"
        :on-remove="handleRemove"
      >
        <el-icon class="upload-icon"><UploadFilled /></el-icon>
        <div class="upload-text">将 Excel 文件拖到此处，或点击选择</div>
        <template #tip>
          <div class="upload-tip">仅支持 .xlsx / .xls 格式，单次上传一个文件</div>
        </template>
      </el-upload>

      <el-button
        type="primary"
        :loading="uploading"
        :disabled="fileList.length === 0"
        class="upload-btn"
        @click="handleUpload"
      >
        {{ uploading ? '上传中...' : '开始上传' }}
      </el-button>

      <el-alert
        v-if="successMsg"
        :title="successMsg"
        type="success"
        :closable="true"
        show-icon
        class="result-msg"
        @close="successMsg = ''"
      >
        <template #default>
          <el-button type="primary" size="small" @click="router.push('/dashboard')">
            查看工作台
          </el-button>
        </template>
      </el-alert>

      <el-alert
        v-if="errorMsg"
        :title="errorMsg"
        type="error"
        :closable="true"
        show-icon
        class="result-msg"
        @close="errorMsg = ''"
      />
    </el-card>

    <el-card v-if="parsedRows.length > 0" class="data-card">
      <template #header>
        <div class="data-card-header">
          <span>解析结果（共 {{ parsedRows.length }} 条）</span>
          <el-button
            type="success"
            :loading="analyzing"
            @click="handleAnalyze"
          >
            {{ analyzing ? 'AI分析中...' : 'AI 智能分析' }}
          </el-button>
        </div>
      </template>
      <el-table :data="parsedRows" stripe border style="width: 100%">
        <el-table-column prop="date" label="日期" width="140" />
        <el-table-column prop="sales" label="销售额" width="140" />
        <el-table-column prop="orders" label="订单量" width="120" />
        <el-table-column prop="users" label="用户数" width="120" />
      </el-table>
    </el-card>

    <el-card v-if="aiResult" class="ai-card">
      <template #header>
        <span>AI 分析结果</span>
      </template>
      <div class="ai-result-content">{{ aiResult }}</div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { UploadFilled } from '@element-plus/icons-vue'
import type { UploadInstance, UploadFile, UploadUserFile } from 'element-plus'
import { useRouter } from 'vue-router'
import { uploadExcel } from '@/api/excel'
import { analyzeReport } from '@/api/report'

const router = useRouter()

const uploadRef = ref<UploadInstance>()
const fileList = ref<UploadUserFile[]>([])
const uploading = ref(false)
const analyzing = ref(false)
const successMsg = ref('')
const errorMsg = ref('')
const parsedRows = ref<any[]>([])
const aiResult = ref('')

function handleFileChange(file: UploadFile) {
  fileList.value = [file]
}

function handleRemove() {
  fileList.value = []
}

function handleExceed() {
  errorMsg.value = '仅允许上传一个文件，请先移除已选文件'
  successMsg.value = ''
}

async function handleUpload() {
  if (fileList.value.length === 0) return

  const file = fileList.value[0].raw
  if (!file) {
    errorMsg.value = '文件读取失败'
    return
  }

  uploading.value = true
  successMsg.value = ''
  errorMsg.value = ''
  parsedRows.value = []

  try {
    const res = await uploadExcel(file)
    const data = res.data.data
    successMsg.value = `上传成功，共读取 ${data.total} 条数据`
    parsedRows.value = data.rows || []
    sessionStorage.setItem('dashboard_sales_data', JSON.stringify(data.rows || []))
    fileList.value = []
  } catch (err: any) {
    const msg = err?.response?.data?.message || err?.message || '上传失败'
    errorMsg.value = msg
  } finally {
    uploading.value = false
  }
}

async function handleAnalyze() {
  if (parsedRows.value.length === 0) return

  analyzing.value = true
  aiResult.value = ''
  errorMsg.value = ''

  try {
    const res = await analyzeReport(parsedRows.value)
    aiResult.value = res.summary
  } catch (err: any) {
    const msg = err?.response?.data?.message || err?.message || 'AI分析失败'
    errorMsg.value = msg
  } finally {
    analyzing.value = false
  }
}
</script>

<style scoped lang="scss">
.excel-upload {
  max-width: 600px;

  .upload-icon {
    font-size: 48px;
    color: #c0c4cc;
  }

  .upload-text {
    margin-top: 12px;
    color: #606266;
    font-size: 14px;
  }

  .upload-tip {
    color: #909399;
    font-size: 12px;
    margin-top: 8px;
  }

  .upload-btn {
    margin-top: 16px;
    width: 100%;
  }

  .result-msg {
    margin-top: 16px;
  }
}

.data-card {
  max-width: 900px;
  margin-top: 20px;
}

.data-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.ai-card {
  max-width: 900px;
  margin-top: 20px;

  .ai-result-content {
    white-space: pre-wrap;
    line-height: 1.8;
    color: #303133;
  }
}
</style>

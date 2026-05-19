import request from './index'

// API 响应类型定义
export interface TestResponse {
  message: string
  timestamp: number
  status: string
}

// 测试接口
export function testApi(): Promise<TestResponse> {
  return request.get<TestResponse>('/test')
}

// 示例：用户登录接口（后续扩展）
export interface LoginParams {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  userInfo: {
    id: string
    name: string
    email: string
  }
}

export function login(params: LoginParams): Promise<LoginResponse> {
  return request.post<LoginResponse>('/auth/login', params)
}

// 示例：上传 Excel 接口（后续扩展）
export interface UploadResponse {
  fileId: string
  fileName: string
  fileSize: number
}

export function uploadExcel(file: File): Promise<UploadResponse> {
  const formData = new FormData()
  formData.append('file', file)
  return request.post<UploadResponse>('/upload/excel', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
import request from './index'

export interface AnalyzeResponse {
  summary: string
}

export function analyzeReport(data: Record<string, any>[]) {
  return request.post<AnalyzeResponse>('/report/analyze', { data })
}

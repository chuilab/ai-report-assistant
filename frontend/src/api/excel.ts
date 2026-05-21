import axios from 'axios'

const BASE_URL = '/api'

export function uploadExcel(file: File) {
  const formData = new FormData()
  formData.append('file', file)
  return axios.post(`${BASE_URL}/excel/upload`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

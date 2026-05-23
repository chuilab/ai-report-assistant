<template>
  <div class="sales-chart">
    <v-chart :option="option" autoresize />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { BarChart, LineChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components'

use([
  CanvasRenderer,
  BarChart,
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
])

interface SalesRecord {
  date: string
  sales: number
  orders?: number
}

const props = defineProps<{
  data: SalesRecord[]
}>()

const option = computed(() => ({
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' }
  },
  legend: {
    data: ['销售额', '订单量'],
    bottom: 0
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '12%',
    top: '10%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: props.data.map((r) => r.date),
    axisLabel: { rotate: 30 }
  },
  yAxis: [
    {
      type: 'value',
      name: '销售额（元）',
      axisLabel: {
        formatter: (v: number) => (v >= 10000 ? (v / 10000).toFixed(1) + '万' : v)
      }
    },
    {
      type: 'value',
      name: '订单量',
      axisLabel: {
        formatter: (v: number) => v.toString()
      }
    }
  ],
  series: [
    {
      name: '销售额',
      type: 'bar',
      data: props.data.map((r) => r.sales),
      itemStyle: {
        color: '#409eff',
        borderRadius: [4, 4, 0, 0]
      },
      barMaxWidth: 36
    },
    {
      name: '订单量',
      type: 'line',
      yAxisIndex: 1,
      data: props.data.map((r) => r.orders || 0),
      smooth: true,
      itemStyle: { color: '#67c23a' },
      lineStyle: { width: 2 },
      symbol: 'circle',
      symbolSize: 6
    }
  ]
}))
</script>

<style scoped>
.sales-chart {
  width: 100%;
  height: 360px;
}
</style>

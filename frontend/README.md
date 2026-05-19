# AI 报表助手前端

基于 Vue3 + Vite + TypeScript + Element Plus 的智能报表工具前端项目。

## 技术栈

- Vue 3.4
- Vite 5
- TypeScript 5
- Element Plus 2.6
- Axios 1.6
- Pinia 2.1
- Vue Router 4.3

## 目录结构

```
src
├── api                 # API 请求模块
│   ├── index.ts        # Axios 封装
│   └── test.ts         # 示例 API
├── assets              # 静态资源（图片、字体等）
├── components          # 公共组件
├── layout              # 布局组件
│   └── MainLayout.vue  # 主布局
├── router              # 路由配置
│   └── index.ts        # 路由定义
├── stores              # Pinia 状态管理
│   └── user.ts         # 用户状态
├── styles              # 全局样式
│   └── index.scss      # 主样式文件
├── utils               # 工具函数
├── views               # 页面视图
│   └── Dashboard.vue   # 工作台页面
├── App.vue             # 根组件
├── main.ts             # 入口文件
└── env.d.ts            # 类型定义
```

## 快速开始

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产版本
npm run preview
```

## 功能模块

- Excel 上传
- AI 分析结果展示
- 图表展示
- 历史记录
- 用户登录
- 报表管理
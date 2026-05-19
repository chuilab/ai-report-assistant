# 第一阶段：构建阶段
FROM node:18-alpine AS build
WORKDIR /app

# 复制package.json和package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm ci --only=production

# 复制源代码
COPY . .

# 构建应用
RUN npm run build

# 第二阶段：运行阶段
FROM nginx:alpine

# 复制构建好的文件到nginx目录
COPY --from=build /app/dist /usr/share/nginx/html

# 复制自定义nginx配置（可选）
# COPY nginx.conf /etc/nginx/nginx.conf

# 暴露端口
EXPOSE 80

# 启动nginx
CMD ["nginx", "-g", "daemon off;"]
# AI Report Assistant

AI智能报表助手后端服务

## 项目简介

这是一个基于 Spring Boot 3 的 AI 智能报表工具后端，用于自动化生成日报、周报等报表，并提供数据分析能力。

## 技术栈

- Java 17
- Spring Boot 3.2.1
- MyBatis Plus 3.5.5
- MySQL 8.0
- Redis
- Lombok
- Hutool 5.8.24
- Apache POI 5.2.5
- Knife4j 4.4.0

## 项目结构

```
src/main/java/com/aireport/
├── common/                 # 公共模块
│   ├── constant/          # 常量定义
│   ├── exception/         # 异常处理
│   └── result/            # 统一返回结果
├── config/                # 配置类
│   ├── RedisConfig        # Redis配置
│   └── MybatisPlusConfig  # MyBatis Plus配置
├── controller/            # 控制器层
├── service/               # 业务逻辑层
├── mapper/                # 数据访问层
├── entity/                # 实体类
├── dto/                   # 数据传输对象
├── vo/                    # 视图对象
├── ai/                    # AI服务模块
│   └── AiClient           # AI客户端（OpenAI/DeepSeek）
├── excel/                 # Excel处理模块
│   └── ExcelHandler       # Excel读写处理
└── report/                # 报表生成模块
    └── ReportGenerator    # 报表生成器
```

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 2. 数据库初始化

```bash
# 执行数据库初始化脚本
mysql -u root -p < src/main/resources/schema.sql
```

### 3. 配置修改

修改 `application.yml` 中的数据库和Redis连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_report
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password: # 如果有密码则填写
```

### 4. 启动项目

```bash
# 编译项目
mvn clean install

# 启动应用
mvn spring-boot:run
```

### 5. 测试接口

访问测试接口：
```
GET http://localhost:8080/api/test
```

返回：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "AI Report Assistant Running",
  "timestamp": 1234567890
}
```

### 6. API文档

访问 Swagger API 文档：
```
http://localhost:8080/api/doc.html
```

## 目录结构设计理念

### 1. 为什么这样设计？

**分层清晰**：
- `common` - 所有业务共享的基础组件，避免重复代码
- `config` - 配置集中管理，便于维护
- `controller/service/mapper/entity` - 标准三层架构，职责明确
- `dto/vo` - 入参和出参分离，避免实体直接暴露

**业务模块化**：
- `ai` - AI服务独立模块，便于替换不同AI提供商
- `excel` - Excel处理独立模块，易于扩展格式支持
- `report` - 报表生成独立模块，便于新增报表类型

**单体架构优势**：
- 简单易维护，适合个人副业
- 无微服务复杂度，降低运维成本
- 易于调试和快速迭代
- 未来可按需拆分

### 2. 后续如何扩展AI模块？

**扩展步骤**：

1. 在 `ai` 包下新增不同的AI客户端：
```java
// 例如新增Claude客户端
@Component
public class ClaudeClient {
    // Claude API调用实现
}
```

2. 创建AI服务统一接口：
```java
public interface AiService {
    String analyze(String prompt);
    String generateReport(String data);
}
```

3. 使用策略模式选择不同AI服务：
```java
@Service
public class AiServiceFactory {
    public AiService getService(String provider) {
        // 根据配置返回不同客户端
    }
}
```

4. 在application.yml中配置多个AI服务：
```yaml
ai:
  providers:
    - name: openai
      enabled: true
    - name: deepseek
      enabled: true
```

### 3. 后续如何扩展Excel模块？

**扩展方向**：

1. **新增数据导入功能**：
```java
@PostMapping("/import")
public Result<List<DataVO>> importExcel(@RequestParam MultipartFile file) {
    List<DataVO> data = excelHandler.readExcelToObject(file, DataVO.class);
    // 数据处理和存储
    return Result.success(data);
}
```

2. **新增模板导出功能**：
```java
@GetMapping("/template")
public void downloadTemplate(HttpServletResponse response) {
    List<Map<String, Object>> templateData = createTemplate();
    excelHandler.writeExcel(templateData, headers, response.getOutputStream());
}
```

3. **支持大数据量处理**：
- 使用 EasyExcel 替代 POI 处理大文件
- 分批读取和写入，避免OOM
- 异步处理 + 进度追踪

4. **支持更多格式**：
- CSV导入导出
- PDF报表生成
- Word文档生成

### 4. 后续如何接入OpenAI/DeepSeek API？

**OpenAI接入步骤**：

1. 配置API Key：
```yaml
ai:
  openai:
    api-key: sk-xxxxx  # 从OpenAI获取
    base-url: https://api.openai.com/v1
    model: gpt-4
```

2. 创建OpenAI服务：
```java
@Service
public class OpenAiService {
    @Autowired
    private AiClient aiClient;

    public String analyzeData(String dataContent) {
        String prompt = "请分析以下数据：" + dataContent;
        return aiClient.callOpenAI(prompt);
    }

    public String generateDailyReport(List<String> tasks) {
        String prompt = "根据以下任务生成日报：" + tasks;
        return aiClient.callOpenAI(prompt);
    }
}
```

3. 在Controller中调用：
```java
@RestController
@RequestMapping("/ai")
public class AiController {
    @Autowired
    private OpenAiService openAiService;

    @PostMapping("/analyze")
    public Result<String> analyze(@RequestBody AnalyzeRequest request) {
        String result = openAiService.analyzeData(request.getData());
        return Result.success(result);
    }
}
```

**DeepSeek接入（类似）**：

```yaml
ai:
  deepseek:
    api-key: sk-xxxxx  # 从DeepSeek获取
    base-url: https://api.deepseek.com/v1
    model: deepseek-chat
```

**优势对比**：

| 服务 | 优势 | 适用场景 |
|------|------|---------|
| OpenAI GPT-4 | 能力最强 | 复杂分析、创意写作 |
| DeepSeek | 成本更低、中文好 | 日报生成、数据解读 |

## API示例

### 统一返回格式

所有接口返回统一格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": 1234567890
}
```

### 状态码说明

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未授权 |
| 500 | 系统异常 |
| 600 | 文件相关错误 |
| 700 | AI服务异常 |

## 开发建议

1. **代码风格**：简洁为主，避免过度设计
2. **命名规范**：遵循驼峰命名，类名用业务含义
3. **注释原则**：只在必要处添加注释，代码应自解释
4. **异常处理**：使用BusinessException抛出业务异常
5. **日志规范**：关键操作必须记录日志

## 未来功能规划

- [ ] Excel上传和解析
- [ ] AI数据分析
- [ ] 自动生成日报/周报
- [ ] 图表分析
- [ ] 历史记录查询
- [ ] 用户权限管理
- [ ] 定时任务生成报表

## Docker Compose 部署

项目提供了 `docker-compose.yml` 配置文件，可以一键启动中间件服务（MySQL、Redis、Nginx）用于前后端联调。

### 1. 启动中间件服务

```bash
# 在 backend 目录下运行
docker-compose up -d
```

### 2. 服务说明

启动的服务：

| 服务 | 端口 | 说明 |
|------|------|------|
| MySQL | 3306 | 数据库服务，数据库名：`ai_report`，root密码：`123456` |
| Redis | 6379 | 缓存服务，无密码 |
| Nginx | 80 | 反向代理服务，用于前后端联调 |

### 3. 数据持久化

所有数据将持久化到 `D:\dockers` 目录下的子文件夹：

- `D:\dockers\mysql\data` – MySQL 数据
- `D:\dockers\mysql\conf` – MySQL 配置
- `D:\dockers\mysql\logs` – MySQL 日志
- `D:\dockers\redis\data` – Redis 数据
- `D:\dockers\redis\conf` – Redis 配置
- `D:\dockers\nginx\html` – Nginx 静态文件
- `D:\dockers\nginx\logs` – Nginx 日志
- `D:\dockers\nginx\ssl` – SSL 证书（预留）

### 4. Nginx 配置

Nginx 配置了反向代理规则：

- 前端开发服务器：代理到 `http://host.docker.internal:5173`（Vite 默认端口）
- 后端 API：代理到 `http://host.docker.internal:8080`（Spring Boot 默认端口）
- Swagger 文档：代理到后端服务

### 5. 使用说明

1. **启动中间件**：
   ```bash
   docker-compose up -d
   ```

2. **启动后端应用**：
   ```bash
   mvn spring-boot:run
   # 或直接运行 AiReportApplication.java
   ```

3. **启动前端开发服务器**：
   ```bash
   cd ../frontend
   npm install
   npm run dev
   ```

4. **访问应用**：
   - 前端页面：http://localhost
   - 后端 API：http://localhost/api
   - Swagger 文档：http://localhost/swagger-ui
   - MySQL：localhost:3306（用户：root，密码：123456）
   - Redis：localhost:6379

### 6. 停止服务

```bash
# 停止并删除容器
docker-compose down

# 停止容器但保留数据
docker-compose stop
```

### 7. 注意事项

1. 确保 Docker Desktop 已安装并运行
2. 首次运行前，请确保 `D:\dockers` 目录存在或有写入权限
3. 前端开发服务器默认运行在 5173 端口，后端运行在 8080 端口
4. 使用 `host.docker.internal` 在 Docker 容器中访问宿主机服务

## 许可证

MIT License

## 联系方式

作者：chuilab
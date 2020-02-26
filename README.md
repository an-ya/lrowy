# lrowy
[Anya的小站](https://www.lrowy.com)
# 配置
在src/main/resources目录下添加配置文件application.properties
```properties
# port
server.port=80

# date
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Asia/Shanghai

# session
server.servlet.session.timeout=604800

# upload path
web.upload-path=D:/lrowy.com/images/

# thymeleaf
spring.thymeleaf.prefix=classpath:/templates
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.servlet.content-type=text/html
spring.thymeleaf.cache=false

# resources cache
spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/js/**,/css/**,/images/**

# datasource
spring.datasource.username=数据库连接用户名
spring.datasource.password=数据库连接密码
spring.datasource.url=jdbc:mysql://数据库ip:数据库port/数据库名?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true

# mybatis
mybatis.mapper-locations=classpath:com/lrowy/mapper/*Mapper.xml
mybatis.configuration.call-setters-on-nulls=true

# JavaMailSender
spring.mail.host=smtp.163.com或smtp.qq.com
spring.mail.port=25或465
spring.mail.username=邮箱的账号
spring.mail.password=邮箱的授权码
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.default-encoding=UTF-8

# httpclient
# 最大连接数
http.maxTotal = 100
# 并发数
http.defaultMaxPerRoute = 20
# 创建连接的最长时间
http.connectTimeout=1000
# 从连接池中获取到连接的最长时间
http.connectionRequestTimeout=500
# 数据传输的最长时间
http.socketTimeout=10000
# 提交请求前测试连接是否可用
http.staleConnectionCheckEnabled=true

# github oauth
<<<<<<< HEAD
github.oauth.clientId=github oauth的appid
github.oauth.clientSecret=github oauth秘钥

# google reCaptcha secret
google.reCaptcha.secret=google reCaptcha秘钥
=======
github.oauth.clientId=github oauth appid
github.oauth.clientSecret=github oauth 密钥

# token
token.secret=token密钥
>>>>>>> ce2aa1966429b2fb0e9dca16f7e6fa3bc85939d0
```

# <img src="./doc/thenx.png" width="80" height="80"> Thenx Generator

------
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.thenx.projects/thenx-generator-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.thenx.projects/thenx-generator-boot)

Thenx Generator 是基于 [MyBatis Generator (MBG) ](https://github.com/mybatis/generator)的一款更为便捷的通用数据接口生成平台项目。相比于传统的 MyBatis Generator 项目更是提高了三倍以上的开发效率，并通过扩展插件的方式来提高平台项目的可移植性、可维护性、可解耦性等优势，使得开发者能更为专注的编写业务代码。

您可以直接引入 Thenx Generator 依赖来完成数据接口的生成，仅仅只需要配置您所使用的数据库相关信息、类路径、Mapper 路径即可。

我们的主要目标是：

- 为所有 Java 生态开发者提供更为高效的数据接口的生成。
- 通过屏蔽非业务代码的实现来提高业务开发者的专注开发效率。
- 杜绝无注释化的任意接口类、方法、XML。

------

## 部署和使用

可通过独立引入 Jar 包的形式引入 Thenx Generator依赖，但我们建议通过 Maven 或者 Gradle 来引入 Thenx Generator 依赖。

如下是基于 Maven 的依赖引入：

```xml
<dependency>
  <groupId>org.thenx.projects</groupId>
  <artifactId>thenx-generator-boot</artifactId>
  <version>1.0.0</version>
</dependency>
```

依赖引入后可在 **resources** 目录下建立 **generator.properties** 配置文件来让 Thenx Generator 运行起来，这里我们分成 **多模块项目** 和 **单体项目** 来进行配置，可各取所需。

### 多模块项目配置

对于多模块项目，我们只需要分别在 **entity**、**dao**、**mapper** 三层 **package** 路径中添加模块前缀即可，如下，需要将生成的代码导出到 **thenx-generator-tests** 模块:

```properties
# 1. db info
thenx.driver= com.mysql.cj.jdbc.Driver
thenx.url= jdbc:mysql://localhost:63306/generator_db?useUnicode=true&characterEncoding=utf8
thenx.username= generator_user
thenx.password= 123456789

# 2. jdbc -connector libs/*.jar
thenx.target.connector= libs/mysql-connector-java-8.0.15.jar

# 3. table name
thenx.target.table= generator_tests

# 4. entity - 将 entity 代码导出至 thenx-generator-tests 模块
thenx.entity.project= thenx-generator-tests/src/main/java
thenx.entity.package= org.thenx.entity

# 5. dao - 将 dao 代码导出至 thenx-generator-tests 模块
thenx.dao.project= thenx-generator-tests/src/main/java
thenx.dao.package= org.thenx.dao

# 6. mapper - 将 mapper 代码代码导出至 thenx-generator-tests 模块
thenx.mapper.project= thenx-generator-tests/src/main/resources
thenx.mapper.package= mapper
```

### 单体项目配置

对于单体项目，我们相比于多模块项目，仅仅只是去掉模块前缀，仅此而已，配置代码如下：

```properties
# 1. db info
thenx.driver= com.mysql.cj.jdbc.Driver
thenx.url= jdbc:mysql://localhost:63306/generator_db?useUnicode=true&characterEncoding=utf8
thenx.username= generator_user
thenx.password= 123456789

# 2. jdbc -connector libs/*.jar
thenx.target.connector= libs/mysql-connector-java-8.0.15.jar

# 3. table name
thenx.target.table= generator_tests

# 4. entity
thenx.entity.project= src/main/java
thenx.entity.package= org.thenx.entity

# 5. dao
thenx.dao.project= src/main/java
thenx.dao.package= org.thenx.dao

# 6. mapper
thenx.mapper.project= src/main/resources
thenx.mapper.package= mapper
```

### 小事项

如果您是基于 Spring Boot 系列的框架式开发，别忘记在启动类上加上 DAO 的注解扫描哦：

```java
/**
 * @author Thenx Generator
 * 
 * 不要忘记加上 @MapperScan
 */
@MapperScan({"org.thenx.projects.dao.**"})
@SpringBootApplication
public class ThenxGeneratorVerifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThenxGeneratorVerifyApplication.class, args);
    }

}
```


------

## 寻求帮助

如果您在使用 Thenx Generator 的时候碰到了任何问题，我们可以随时提供帮助 !

- 通过 [提问题](https://github.com/thenx-projects/thenx-generator/issues) 的方式来告诉我们您所遇到的问题。
- 通过邮件的方式来告诉我们您所遇到的问题 (opensource@thenx.org)

## 反馈问题

如果您在使用 Thenx Generator 的过程中发现了任何 BUG 或者新的功能建议，可直接通过 [提交 ISSUES](https://github.com/thenx-projects/thenx-generator/issues) 的方式来告诉我们 !

## 从源码构建项目

您可以基于 Thenx Generator 项目来扩展您的二次开发或者您不想使用当前稳定版本的 Thenx Generator，可以使用 Maven 来构建当前 main 分支的最新项目。值得注意的是，本项目基于 OpenJDK 11，我们所使用的是 **Amazon corretto-11** 版本，理论上来说只要是 JDK11 都可以运行和编译本项目。

我们仅仅需要执行如下指令可克隆下来 main 分支的最新代码：

```shell
$ git clone --depth=1 https://github.com/thenx-projects/thenx-generator.git
```

## 模块说明

Thenx Generator 有几个重要模块，如下是一个快速概览：

### sql

这个模块只要存放的是便于贡献者和开发者测试项目所需的 SQL 片段，直接导入您的 MySQL 数据库中即可。

### thenx-generator-boot

thenx-generator-boot 模块是整个 Thenx Generator 项目的启动入口，其中 GeneratorBoot 类是主要启动类，也是引入 Thenx Generator 依赖后所需要调用的方法 (generator 方法)。

### thenx-generator-config

thenx-generator-config 模块是用于配置代码的生成规则、生成路径，并实现 **CommentGenerator** 方法来完善简单的 Entity、DAO 层的生成规则。

### thenx-generator-plugins

thenx-generator-plugins 模块用于提高 MBG 的整体功能和性能，通过扩展 **PluginAdapter** 接口来增强和完善 Entity、DAO、Mapper 三层，并在反射的基础上完善注释信息。

### thenx-generator-tests

thenx-generator-tests 模块主要用于整体项目的测试模块，包括所生成的 Entity、DAO、Mapper 所存放的路径，也能从侧面证明 Thenx Generator 在多模块项目中的实际应用可行性。

------

## License

Thenx Generator is Open Source software released under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html).

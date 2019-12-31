## 简介
该框架面向所有的java程序员，尤其是使用ssm进行开发的开发者们。该框架提供基础代码一键生成的功能，可以大大减少代码的抒写提高开发质量和效率。
## laughing-framework解决的问题
大部分开发者们会用`MyBatis Generator`来自动生成MyBatis的 mapper、dao、entity 的框架，让我们省去规律性最强的一部分最基础的代码编写。但是也有一定的不足比如:     

1:只能生成最底层的数据层代码,而且生成的代码有很多无用的注释。     
2:步骤繁琐，需要引用mysql-connector.jar,配置数据库连接，需要写xml等等。      
3:不方便拓展, 生成的只能是最基础的crud代码，比如batch操作都不支持，而且生成代码格式固定。    

## laughing-framework功能
laughing-framework为了弥补`MyBatis Generator`的一些问题，对功能进行了拓展。通过注解模式,从业务角度切入,定义需要展示的字段属性，定义需要建表的字段属性，只需要建立一个实体类，一键生成所有基础代码。     
1:支持mybatis和ibatis两种风格代码     
2:支持自动生成 mapper、dao、entity数据层代码，支持注解生成，批量操作生成     
3:支持自动生成service,controller层代码      
4:支持自动生成dao,service层的单元测试     
5:支持自动生成前端代码     
6:支持自动生成建表的sql语句     
7:支持生成代码自定义模板化配置，高拓展性。    
............
## 快速开始
  我们以正常的一个业务需求进行切入吧。需要背景如下:
 需求很简单，需要对该学校学生(StudentInfo)的信息进行管理。     
学生信息     

属性名 | 类型 | 默认值 | 描述
---- | ----- | ------ | ------ 
name | varchar(64) | "" | 姓名
age | int | 0 | 年龄
phone | varchar(32) | "" | 联系方式
id_card | varchar(64) | "" | 身份证
stu_id | varchar(64) | "" | 学号
specialty | varchar(64) | "" | 专业
address | varchar(255) | "" | 地址 

## 引入依赖(不要使用-SNAPSHOT)




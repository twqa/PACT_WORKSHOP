# 环境需求

- java --version >= 1.8
- python --version >=2.7
- gradle

## nice to have
- intelliJ


# 功能介绍

## ContractTestDemoServices
  - JDGrabber.py 从京东抓数据
  - TBGrabber.py 从淘宝抓数据
  
## brand-server
  - 把淘宝和京东上取到的数据按照特定的brand进行价格排序，取到两个数据源中最低价数据
  
## model-server
  - 把淘宝和京东上取到的数据按照特定的model进行价格排序，取到两个数据源中最低价数据

## webapp
  - Application: 数据服务，把brand-server或者model-server取到的两个数据进行再排序，最终只返回一个最低价数据
  - WebApplication: 网站服务，启动网站用。

# 使用说明

打开命令行工具

##启动python抓数据服务
  -进入python服务文件夹
```
  cd ContractTestDemoServices/
```
  -启动TBGrabber.py
```
  python TBGrabber.py
```
  -新建命令行窗口，进入python文件夹后启动JDGrabber.py
```
  python JDGrabber.py
```
  -通过浏览器访问链接，若返回JSON数据则确认服务已启动
```
  localhost:6001/products
  localhost:6002/products
```

##启动brand-server
  -进入brand-server文件夹
```
  cd brand-server/
```
  -启动brand服务
```
  ./gradlew bootRun
```
  -通过浏览器访问链接，若返回JSON数据则确认服务已启动
```
  localhost:8000/api/brand=神舟
```

##启动modle-server
  -进入modle-server文件夹
```
  cd modle-server/
```
  -启动modle服务
```
  ./gradlew bootRun
```
  -通过浏览器访问链接，若返回JSON数据则确认服务已启动
```
  localhost:8010/api/mod=MAC
```

##启动webapp
  -进入webapp文件夹
```
  cd webapp/
```
  -启动application服务
```
  ./gradlew bootRun
```
  -启动web网站
```
  ./gradlew runweb
```
  -通过浏览器访问链接
```
  localhost:7000
```

## help-tips
  - java.net.ConnectException: Connection refused
      查看应用端口号是否被占用
      lsof -i tcp:［port]

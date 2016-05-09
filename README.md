# 功能

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
  见文件夹内

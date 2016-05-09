## 目录及简介
    main
      |__java
          |__com
              |__server #服务，用来排序和返回最终排序结果 
                  |__model
                  |__Application.java  #服务主程序，改端口在这里。
              |__web
                  |__WebApplication.java #网页主程序，改端口在这里。
      |__resources
          |__templates
              |__hello.html #网页文件
          |__application.yml #配置文件，Applicaion需要的resource的地址都在这儿
          
## 访问：
1. 启动Application.java 然后可以通过HTTP.POST
http://localhost:7001/brand?brand=神舟
http://localhost:7001/model?mod=MACBOOK

2. 启动WebAPPlication.java 然后可以打开网站
http://localhost:7000

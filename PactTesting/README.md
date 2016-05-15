这个例子主要目的是跑通contract 测试,调用的服务是:http://localhost:8000/local/brand=神舟
真正的测试只有靠朱平同学完成了,呵呵

运行测试前,请开启brand-server服务
验证方式:浏览器中访问:http://localhost:8000/local/brand=神舟,能得到正确结果

运行测试步骤
gradle idea --下载所有的依赖
gradle test --会在target/pact下生成pact文件,内容应该如下:
{
    "provider": {
        "name": "local_getDetailsByBrand_provider"
    },
    "consumer": {
        "name": "getDetailsByBrand_consumer"
    },
    "interactions": [
        {
            "providerState": "test state",
            "description": "consumer test by pact for getDetailsByBrand",
            "request": {
                "method": "GET",
                "path": "/local/brand=\u795e\u821f",
                "body": null
            },
            "response": {
                "status": 200,
                "headers": {
                    "Content-Type": "text/plain;charset=UTF-8"
                },
                "body": "{\"Data\":[{\"provider\": \"\u4eac\u4e1c\",\"brand\": \"\u795e\u821f\",\"model\": \"\u6218\u795e K650D-I5 D2\",\"price\": 3109.0},{\"provider\": \"\u6dd8\u5b9d\",\"brand\": \"\u795e\u821f\",\"model\": \" \u6218\u795eK610D-i7 D2\",\"price\": 3499.0}]}"
            }
        }
    ],
    "metadata": {
        "pact-specification": {
            "version": "2.0.0"
        },
        "pact-jvm": {
            "version": "3.2.7"
        }
    }
}
gradle pactVerify  --provider 测试,应该返回如下结果

:compileJava
Note: /Users/tlqiao/project/pact/PactTesting/src/main/java/com/thoughtworks/service/ConsumerClient.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
:processResources UP-TO-DATE
:classes
:compileTestJava
Note: /Users/tlqiao/project/pact/PactTesting/src/test/java/com/thoughtworks/pact/consumer/getDetailsByBrandTest.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
:processTestResources UP-TO-DATE
:testClasses
:pactVerify_local_getDetailsByBrand_provider

Verifying a pact between getDetailsByBrand_consumer and local_getDetailsByBrand_provider
  [Using file /Users/tlqiao/project/pact/PactTesting/target/pacts/getDetailsByBrand_consumer-local_getDetailsByBrand_provider.json]
  Given test state
         WARNING: State Change ignored as there is no stateChange URL
  consumer test by pact for getDetailsByBrand
    returns a response which
      has status code 200 (OK)
      includes headers
        "Content-Type" with value "text/plain;charset=UTF-8" (OK)
      has a matching body (OK)
:pactVerify

BUILD SUCCESSFUL




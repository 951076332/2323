### **application** 空间

```
spring.application.name = msb-mall-pay
server.port = 9998
server.servlet.context-path = /pay-center
eureka.client.serviceUrl.defaultZone = http://192.168.9.68:8761/eureka/
eureka.instance.prefer-ip-address = true

rocketmq.name-server = 192.168.9.68:9876
rocketmq.producer.group = msb-db-pay-center
rocketmq.producer.sendMessageTimeout = 300000

spring.data.cassandra.keyspace-name = msb_mall_pay
spring.data.cassandra.contact-points = 192.168.9.68
spring.data.cassandra.port = 9042
spring.data.cassandra.schema-action = CREATE_IF_NOT_EXISTS

spring.data.mongodb.uri = mongodb://msb_mall_pay:msb_mall_pay@192.168.9.68:40000,192.168.9.68:50000,192.168.9.68:60000/msb_mall_pay
spring.data.mongodb.transactionEnabled = true


spring.redis.host = 192.168.9.68
spring.redis.port = 6379
spring.redis.password = MSB_@2021!

msb.jwt.tokenHeader = Authorization
msb.jwt.refreshTokenHeader = R-Authorization
msb.jwt.secret = !!@MSB(Auth}*^31)&~~~%
msb.jwt.publicKeyStr = MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNuKPv6R0IB9b23T+LCBGbc7t996SUF6CpMva66RM5KZRFBgpAVgbdovfIkwfBYJXiwTbwCvIWPu3JlLL9BtNaNVadSn9ItJsH888mjXMolWEouCkFwfqs0+sG+k61qJkXfT5XzEi8Dif3wMNDiKSGGOifBHB34RXkusw4QcSQMwIDAQAB
msb.jwt.expiration = 604800
msb.jwt.refreshExpiration = 604800
msb.jwt.tokenHead = Bearer
msb.jwt.client = true
msb.jwt.selfDecryptToken = true
knife4j.enable = true
knife4j.markdowns = classpath:markdown2x/*
knife4j.basic.enable = false
knife4j.basic.username = zhangsan
knife4j.basic.password = 123456

logging.config = classpath:logback-config.xml
logging.level.root = INFO

order.timeOutMinute = 5

#阿里支付参数
alipay.app_id = 2021000116683310
alipay.alipay_public_key = MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgHC6w5CLQytTLKQvGxVKR6B8ApVfyms0Ve0DJHV4YzXY7loHkuHAb4Mr0GBqpjJ897fYr/bnKOyyBnOAzbUW2cZG9aCQZ0LOxomjzKW3Mnw55aF/RE2w16xvVXNFZZdT4uk17BvNJ6JV57H1pakFkJP7I7WYgqrn+I3aunEdOJRRAPGacz00b6YmvX7IoMzfo3FMzuvAhOCQyR/FxI5RGCxLhX0jr8VX5hB8/fpMIPYfDCiVwqEqNWtT65VLQn+pb88orJxXAAbyYsSuUbP+RnqdlWiZ0ElcyqCdghYenPAWYY658APPBWBWjH7+3S21CInxVMVXHOS+A+L64H/gHQIDAQAB
alipay.merchant_private_key = MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPJV5zLX8l8fhRI/qqOXjmmPx/vr+Wws4ytvm8JLJQb5ST9lwc9SpGfg7+sG7v3Sczhinbei/6trIfFxxZ5NtHIDpWUEDCWmvM3F3DB2JPhgol+mMXeNKpdyhGTDV12kNqnhY9is/y4Rra5GmGDkqS2b8bDEsqbJn61NWd/HS8Nir3SWlxzP5J9h1knXXB4AUaoLbOSEe+S/E1bCsvJlpDwxsFQx0QWstmhsALwXQizltohUamFs8vn8y1n86pabuRYjZWy9bmBW0fRhhMXrYtIF993wnpNI2Qw4ECowV8BcMlJjbHfYoeB97Vjv5qUat8x2zWybAgujSLYN+XIRUVAgMBAAECggEAKOEnJAVuNkvv+lwoDczz9gbitSioVkmz0U/n6g8gzU/QQqEslSh+tjrx1VJVnDKwK0q5UXCHmzXpkG9tLU7qQFm3z0aD9/0SpCKatEO7k7JX25iyC9X5c2QQIGNLMSjv79bNyy2FTXtzy+dHK32RVnbQ2Myd+rOhFb0KdF/Qhp8T3cLq89bS01tKBD5OVHQvKZF6mHovqiy9s49ovoXdVZiVR7C4m8jDNtpBs+5u79U/3q6dm7/2ohtwRzBFIwvwDrmR7f79wPn2qfkmlUfyWgsXSoXMYVe6IUiROr+DobYFUGH+Hp7lQzPEw4l+ofQig12hoqoMCOXF5bdJ3PfgUQKBgQDPYrcSaT5C2FXEmdR5Z7iehpgrtUrAqRKWRngXIEgEo3+h9mm5W6CG4yPQdUEp8fNs1g8SDrtKXdg22VeM0xB/J1DZCMFQy7pMlp1FjiBkYRNCfASEos3Hdr0QxraJjpLGmXqGZPQAPIRrFqPVlC3vIHhXcZxZgJhgggdWCpeTFwKBgQCws5w7sbRWhitaRphdpeV6MjLVetxCr/IRAzyRfM4WOLiguIpFY2fwXt+CPgdx/ZZFkOVSmJgplddPWtMq+g+o7XcQ7pweCjY7WAXFsDPR2he1lBXYERwnM94Gs/PyeQ2QEovr512NIo13CbwFt/SDMVuLWfci08GM+M9y1vokswKBgQCRRlCCWDEiOFOUnkIGLc9ZVWXBsITGA2oe1QyI/rcUcMgW1/vlubbw5qqO+6SDUpc4VZdHCgTPKoAspAG9h5kVyZ/L+WMaSyydPCC9ubsdNhd8yk2L8FQAd0AoQVtbbwGIl7kJ7iwzAjGsprZsBhpLWc6TSuXAmOGlMVEcR904TwKBgGHFH78P1n7Bgh9TTF8UueJhACuK7XCgtcmgBtVhPw6gl9emBCvcktg3RmhkNe5mp80aHSkUca0g0Z+A27K95ghMTEM8I3mjoNo5HAiENEf66n93zpZc8TznwAPpciNJw61h9bWzsNm0sp2Y70YiwNIOb16tisbkcAW2beJW/NVtAoGAQnYYILI0qk1QeaU2ixa4wgSMBWY+gSzO5KY5KV/NBy7ouKz4Qst3NxIEcqDc6HzVukL5J4/wjiwHSuF3kxxkHPaRyw1PEBhsFR2W4/fQmKzbOMyoIvPyt+bBSS8ffNtnBFA9ogaCsEUDCpby4bek8ncOnGtqQ6I/f/q3EKc2wIM=
alipay.notify_url = http://39.99.205.213:2000/pay-center/pay/aliPay/notify
alipay.return_url = http://39.99.205.213:82/#/payDone
alipay.sign_type = RSA2
alipay.charset = utf-8
alipay.gateway_url = https://openapi.alipaydev.com/gateway.do


```

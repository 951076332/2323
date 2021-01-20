FROM java:8
VOLUME /usr/app/project/
ADD msb-product-web.jar product-portal-web.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/project/product-portal-web.jar"]
# 指定维护者的名字
MAINTAINER msb-pro
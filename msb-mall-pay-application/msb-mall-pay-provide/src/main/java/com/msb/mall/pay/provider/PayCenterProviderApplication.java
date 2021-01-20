package com.msb.mall.pay.provider;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 知识分享门户端启动入口
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月01日 14时07分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.msb.mall"})
@ServletComponentScan(basePackages = {"com.msb.mall.pay.provider.filter"})
@EnableAsync
@EnableFeignClients(basePackages = "com.msb.mall.pay.feign.api")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EntityScan(basePackages = "com.msb.mall.pay.entity")
@EnableApolloConfig("application.yml")
public class PayCenterProviderApplication {
    public static void main(String[] args) throws UnknownHostException {

        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ConfigurableApplicationContext application= SpringApplication.run(PayCenterProviderApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t"+
                        "Doc: \thttp://{}:{}/doc.html\n"+
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }

}

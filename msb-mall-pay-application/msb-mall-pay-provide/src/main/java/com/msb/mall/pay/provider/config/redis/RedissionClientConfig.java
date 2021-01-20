package com.msb.mall.pay.provider.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class RedissionClientConfig {

    @Value("${spring.redis.cluster.nodes:'192.168.110.97:6379'}")
    private String cluster;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    @ConditionalOnExpression("'${spring.profiles.active}' .equals( 'prod')")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(cluster)
                .setPassword(password);
        RedissonClient client = Redisson.create(config);
        return client;
    }


    @Bean
    @ConditionalOnExpression("'${spring.profiles.active}' .equals( 'prod')")
    public RedissonClient redissonClusterClient() {
        String[] nodes = cluster.split(",");
        for(int i=0; i<nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(nodes)
                .setPassword(password);
        RedissonClient client = Redisson.create(config);
        return client;
    }
}

package com.msb.mall.pay.provider.config.db.mongodb;

import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//
///**
// * 全局事务配置类
// *
// * @author 马士兵 · 项目架构部
// * @version V1.0
// * @contact zeroming@163.com
// * @date: 2020年05月27日 16时30分
// * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
// * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
// */
//@Aspect
//@Configuration
//@EnableTransactionManagement
//public class TransactionConfig {
//
//    @Bean
////    @ConditionalOnProperty(name="spring.data.mongodb.transactionEnabled",havingValue = "true")
//    MongoTransactionManager transactionManager(MongoDbFactory factory){
//        return new MongoTransactionManager(factory);
//    }
//
//    @Bean(name = "mongoConverter")
//    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
//        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
//        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
//        try {
//            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
//        } catch (NoSuchBeanDefinitionException ignore) {
//        }
//
//        // 不保存 _class 字段
//        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//        return mappingConverter;
//    }
//}

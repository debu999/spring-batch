//package org.doogle.springbatch.config;
//
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.bson.types.ObjectId;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//@Configuration
//public class MongoConfiguration implements RepositoryRestConfigurer {
//
//    @Override
//    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
//        Jackson2ObjectMapperBuilder.json()
//                .modulesToInstall(new SimpleModule().addSerializer(ObjectId.class, new ToStringSerializer()))
//                .configure(objectMapper);
//    }
//}
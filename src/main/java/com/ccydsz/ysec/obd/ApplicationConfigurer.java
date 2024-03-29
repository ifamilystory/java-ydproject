package com.ccydsz.ysec.obd;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ccydsz.ysec.obd.model.ex.EXServerModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration //注意别忘了
public class ApplicationConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApplicationInterceptor()).addPathPatterns("/user/*","/test/*");
        super.addInterceptors(registry);
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        super.configureMessageConverters(converters);
        /*
         * 1、需要先定义一个 convert 转换消息对象；
         * 2、添加 fastJson 的配置信息，比如: 是否要格式化返回的Json数据；
         * 3、在 Convert 中添加配置信息;
         * 4、将 convert 添加到 converts 中;
         */

        //1、需要先定义一个 convert 转换消息对象；
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加 fastJson 的配置信息，比如: 是否要格式化返回的Json数据；
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //3、在 Convert 中添加配置信息;
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //4、将 convert 添加到 converts 中;
        converters.add(fastConverter);
        SerializeConfig.getGlobalInstance().configEnumAsJavaBean(EXServerModel.CodeEnum.class);

    }

}

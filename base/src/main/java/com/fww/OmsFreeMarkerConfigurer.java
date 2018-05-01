package com.fww;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;


/**
 * 自定义OmsFreeMarkerConfigurer，继承FreeMarkerConfigurer
 * 实现ApplicationContextAware用于获取applicationContext
 * @author Nicholas
 *
 */
@Component
public class OmsFreeMarkerConfigurer extends FreeMarkerConfigurer implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	/**
	 * 重写父类FreeMarkerConfigurer的afterPropertiesSet()方法
	 * 将自定义的freemarker方法放入私有属性configuration的sharedVariables中
	 */
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(CustomMethod.class);
        Configuration configuration = this.getConfiguration();
        for (String key : map.keySet()) {
            configuration.setSharedVariable(key, map.get(key));
        }
    }
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

package com.goode.loader.groovy;

import com.alibaba.fastjson.JSON;
import com.goode.loader.base.AbstractCalcHandler;
import com.goode.loader.base.Constants;
import com.goode.loader.message.RabbitMessage;
import groovy.lang.GroovyClassLoader;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 动态编译数据库算法的工厂类
 * 1、项目启动时，一次性编译加载所有算法
 * 2、界面上修改算法时，发送mq消息，通知实时编译加载（分布式多台机器中，每台都会收到该消息）
 *
 * @author 唐光德
 * @Date 2018/06/06 17:38
 */
@Log4j2
public class GroovyFactory {

    /**
     * groovy class loader
     */
    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    private static GroovyFactory groovyFactory = new GroovyFactory();

    public static GroovyFactory getInstance() {
        return groovyFactory;
    }

    /**
     * rabbitmq连接工厂
     */
    @Autowired
    private CachingConnectionFactory factory;

    /**
     * inject action of spring
     *
     * @param instance
     */
    private void injectService(Object instance) {
        if (instance == null) {
            return;
        }

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object fieldBean = null;
            // with bean-id, bean could be found by both @Resource and @Autowired, or bean could only be found by @Autowired
            if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
                try {
                    Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
                    if (resource.name() != null && resource.name().length() > 0) {
                        fieldBean = SpringUtil.getApplicationContext().getBean(resource.name());
                    } else {
                        fieldBean = SpringUtil.getApplicationContext().getBean(field.getName());
                    }
                } catch (Exception e) {
                }
                if (fieldBean == null) {
                    fieldBean = SpringUtil.getApplicationContext().getBean(field.getType());
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if (qualifier != null && qualifier.value() != null && qualifier.value().length() > 0) {
                    fieldBean = SpringUtil.getApplicationContext().getBean(qualifier.value());
                } else {
                    fieldBean = SpringUtil.getApplicationContext().getBean(field.getType());
                }
            }

            if (fieldBean != null) {
                field.setAccessible(true);
                try {
                    field.set(instance, fieldBean);
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * load new instance, prototype
     *
     * @param codeSource
     * @return
     * @throws Exception
     */
    public AbstractCalcHandler loadNewCalcHandlerInstance(String codeSource) throws Exception {
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = groovyClassLoader.parseClass(codeSource);
            if (clazz != null) {
                Object instance = clazz.newInstance();
                if (instance != null) {
                    if (instance instanceof AbstractCalcHandler) {
                        this.injectService(instance);
                        return (AbstractCalcHandler) instance;
                    } else {
                        throw new IllegalArgumentException(">>>>>>>>>>> loadNewInstance error, " + "cannot convert from instance[" + instance.getClass() + "] to ICalcHandler");
                    }
                }
            }
        }
        throw new IllegalArgumentException(">>>>>>>>>>> loadNewInstance error, instance is null");
    }


    /**
     * 监听rabbitmq动态队列，保证每台机器都会订阅到该路由（注解方式@RabbitListener只支持静态常量的队列）
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer execMessageContainer() {
        //设置监听者“容器”
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        //设置队列名
        if (log.isInfoEnabled()) {
            log.info("===获取到动态队列名称为：" + Constants.DYN_QUEUE_NAME);
        }
        container.setQueueNames(Constants.DYN_QUEUE_NAME);
        //设置监听者数量，即消费线程数
        container.setConcurrentConsumers(1);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            if (null != body) {
                try {
                    String msg = new String(body, "UTF-8");
                    if (log.isInfoEnabled()) {
                        log.info("^.^收到mq消息：" + msg);
                    }
                    handleRabbitListenerMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return container;
    }

    /**
     * 处理消息
     *
     * @param jsonMsg
     */
    private void handleRabbitListenerMessage(String jsonMsg) {
        if (jsonMsg == null) {
            return;
        }
        RabbitMessage rabbitMessage = JSON.parseObject(jsonMsg, RabbitMessage.class);
        if (rabbitMessage == null || rabbitMessage.getBusinessType() == null || rabbitMessage.getData() == null) {
            return;
        }
        //1、接收处理发布费用计算模型消息，新增算法到JVM缓存
        if (rabbitMessage.getBusinessType() == 1) {
            String c = rabbitMessage.getData().toString();
            try {
                GroovyCache.costCalcModeMap.put("id-key", GroovyFactory.getInstance().loadNewCalcHandlerInstance(c));
            } catch (Exception e) {
                e.printStackTrace();
                log.error(">>.<<发布费用计算模型时，将费用计算模型算法动态加载到jvm缓存发生异常，费用计算模型编码=", e);
            }
        }

    }

}

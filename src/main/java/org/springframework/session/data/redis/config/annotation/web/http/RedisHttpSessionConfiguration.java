
package org.springframework.session.data.redis.config.annotation.web.http;

/**
 * @ClassName springsession
 * @Description TODO
 * @Author huangzhuorong
 * @Date 2019/1/9 12:00
 * @Version 1.0
 **/

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureNotifyKeyspaceEventsAction;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
public class RedisHttpSessionConfiguration extends SpringHttpSessionConfiguration implements EmbeddedValueResolverAware, ImportAware {
    private Integer maxInactiveIntervalInSeconds = 1800;
    private ConfigureRedisAction configureRedisAction = new ConfigureNotifyKeyspaceEventsAction();
    private String redisNamespace = "";
    private RedisFlushMode redisFlushMode;
    private RedisSerializer<Object> defaultRedisSerializer;
    private Executor redisTaskExecutor;
    private Executor redisSubscriptionExecutor;
    private StringValueResolver embeddedValueResolver;

    public RedisHttpSessionConfiguration() {
        this.redisFlushMode = RedisFlushMode.ON_SAVE;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory redisConnectionFactory, RedisOperationsSessionRepository messageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        if (this.redisTaskExecutor != null) {
            container.setTaskExecutor(this.redisTaskExecutor);
        }

        if (this.redisSubscriptionExecutor != null) {
            container.setSubscriptionExecutor(this.redisSubscriptionExecutor);
        }

        container.addMessageListener(messageListener, Arrays.asList(new PatternTopic("__keyevent@*:del"), new PatternTopic("__keyevent@*:expired")));
        container.addMessageListener(messageListener, Arrays.asList(new PatternTopic(messageListener.getSessionCreatedChannelPrefix() + "*")));
        return container;
    }

    @Bean
    public RedisTemplate<Object, Object> sessionRedisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        if (this.defaultRedisSerializer != null) {
            template.setDefaultSerializer(this.defaultRedisSerializer);
        }

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisOperationsSessionRepository sessionRepository(@Qualifier("sessionRedisTemplate") RedisOperations<Object, Object> sessionRedisTemplate, ApplicationEventPublisher applicationEventPublisher) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
        sessionRepository.setDefaultMaxInactiveInterval(this.maxInactiveIntervalInSeconds);
        if (this.defaultRedisSerializer != null) {
            sessionRepository.setDefaultSerializer(this.defaultRedisSerializer);
        }

        String redisNamespace = this.getRedisNamespace();
        if (StringUtils.hasText(redisNamespace)) {
            sessionRepository.setRedisKeyNamespace(redisNamespace);
        }

        sessionRepository.setRedisFlushMode(this.redisFlushMode);
        return sessionRepository;
    }

    public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
        this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
    }

    public void setRedisNamespace(String namespace) {
        this.redisNamespace = namespace;
    }

    public void setRedisFlushMode(RedisFlushMode redisFlushMode) {
        Assert.notNull(redisFlushMode, "redisFlushMode cannot be null");
        this.redisFlushMode = redisFlushMode;
    }

    private String getRedisNamespace() {
        return StringUtils.hasText(this.redisNamespace) ? this.redisNamespace : System.getProperty("spring.session.redis.namespace", "");
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnableRedisHttpSession.class.getName());
        AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
        this.maxInactiveIntervalInSeconds = (Integer)enableAttrs.getNumber("maxInactiveIntervalInSeconds");
        String redisNamespaceValue = enableAttrs.getString("redisNamespace");
        if (StringUtils.hasText(redisNamespaceValue)) {
            this.redisNamespace = this.embeddedValueResolver.resolveStringValue(redisNamespaceValue);
        }

        this.redisFlushMode = (RedisFlushMode)enableAttrs.getEnum("redisFlushMode");
    }

    @Bean
    public InitializingBean enableRedisKeyspaceNotificationsInitializer(JedisConnectionFactory redisConnectionFactory) {
        return new EnableRedisKeyspaceNotificationsInitializer(redisConnectionFactory, this.configureRedisAction);
    }

    @Autowired(
            required = false
    )
    public void setConfigureRedisAction(ConfigureRedisAction configureRedisAction) {
        this.configureRedisAction = configureRedisAction;
    }

    @Autowired(
            required = false
    )
    @Qualifier("springSessionDefaultRedisSerializer")
    public void setDefaultRedisSerializer(RedisSerializer<Object> defaultRedisSerializer) {
        this.defaultRedisSerializer = defaultRedisSerializer;
    }

    @Autowired(
            required = false
    )
    @Qualifier("springSessionRedisTaskExecutor")
    public void setRedisTaskExecutor(Executor redisTaskExecutor) {
        this.redisTaskExecutor = redisTaskExecutor;
    }

    @Autowired(
            required = false
    )
    @Qualifier("springSessionRedisSubscriptionExecutor")
    public void setRedisSubscriptionExecutor(Executor redisSubscriptionExecutor) {
        this.redisSubscriptionExecutor = redisSubscriptionExecutor;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolver = resolver;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    static class EnableRedisKeyspaceNotificationsInitializer implements InitializingBean {
        private final JedisConnectionFactory connectionFactory;
        private ConfigureRedisAction configure;

        EnableRedisKeyspaceNotificationsInitializer(JedisConnectionFactory connectionFactory, ConfigureRedisAction configure) {
            this.connectionFactory = connectionFactory;
            this.configure = configure;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (this.configure != ConfigureRedisAction.NO_OP) {
                RedisConnection connection = this.connectionFactory.getConnection();

                try {
                    this.configure.configure(connection);
                } finally {
                    try {
                        connection.close();
                    } catch (Exception var8) {
                        LogFactory.getLog(this.getClass()).error("Error closing RedisConnection", var8);
                    }

                }

            }
        }
    }
}

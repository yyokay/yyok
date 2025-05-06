C:\Users\MECHREVO\AppData\Roaming\JetBrains\IntelliJIdea2023.3





org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'routerFunctionMapping' defined in class path resource [org/springframework/web/servlet/config/annotation/DelegatingWebMvcConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.web.servlet.function.support.RouterFunctionMapping]: Factory method 'routerFunctionMapping' threw exception; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.fasterxml.jackson.datatype.jsr310.JavaTimeModule]: Constructor threw exception; nested exception is java.lang.NoClassDefFoundError: com/fasterxml/jackson/datatype/jsr310/ser/ZoneIdSerializer
at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:658)
at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:638)
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:955)
at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:145)
at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754)
at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:434)
at org.springframework.boot.SpringApplication.run(SpringApplication.java:338)
at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343)
at org.springframework.boot.SpringApplication.run(SpringApplication.java:1332)
at com.yyok.YYOApplicationBootstrap.main(YYOApplicationBootstrap.java:40)
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.web.servlet.function.support.RouterFunctionMapping]: Factory method 'routerFunctionMapping' threw exception; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.fasterxml.jackson.datatype.jsr310.JavaTimeModule]: Constructor threw exception; nested exception is java.lang.NoClassDefFoundError: com/fasterxml/jackson/datatype/jsr310/ser/ZoneIdSerializer
at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185)
at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653)
... 19 common frames omitted
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.fasterxml.jackson.datatype.jsr310.JavaTimeModule]: Constructor threw exception; nested exception is java.lang.NoClassDefFoundError: com/fasterxml/jackson/datatype/jsr310/ser/ZoneIdSerializer
at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:224)
at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:146)
at org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.registerWellKnownModulesIfAvailable(Jackson2ObjectMapperBuilder.java:859)
at org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.configure(Jackson2ObjectMapperBuilder.java:718)
at org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.build(Jackson2ObjectMapperBuilder.java:701)
at org.springframework.http.converter.json.MappingJackson2HttpMessageConverter.<init>(MappingJackson2HttpMessageConverter.java:59)
at org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter.<init>(AllEncompassingFormHttpMessageConverter.java:94)
at org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.addDefaultHttpMessageConverters(WebMvcConfigurationSupport.java:914)
at org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.getMessageConverters(WebMvcConfigurationSupport.java:867)
at org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.routerFunctionMapping(WebMvcConfigurationSupport.java:568)
at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
at java.lang.reflect.Method.invoke(Method.java:498)
at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154)
... 20 common frames omitted
Caused by: java.lang.NoClassDefFoundError: com/fasterxml/jackson/datatype/jsr310/ser/ZoneIdSerializer
at com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.<init>(JavaTimeModule.java:158)
at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:211)
... 34 common frames omitted

解决方案


-----------------------------------------------------------------------------------------------------------


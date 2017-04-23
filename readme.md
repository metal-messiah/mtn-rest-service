# MTN REST Service
This is the core MTN REST service project.

## API Documentation
API documentation is achieved through Postman. The following link should import the current state of the collection into your Postman instance:

https://www.getpostman.com/collections/1426e247cae861736d62

Any updates to the collection will be published back to this same link, so all you should have to do is click the link to get the updates.

This collection uses environment variables. You'll want to set up Environments in Postman for Local, Dev, and Prod, and will need to provide "host", "idToken", and "accessToken" variables. Due to the nature of JWT, you'll have to update these tokens for each testing session. I've added some help for that on the main web service page once you sign in.

## Correlation ID Header
A client may provide a "mtn-correlation-id" header, which will be used internally by the REST service and appended to most log statements related to that particular request. This allows quick location of relevant messages for debugging.

If the header is not provided by the client, it will be appended to the reqest internally by the REST service before processing the request, and will be returned in the response.

An example of this header in action in the server logs, tying several log statements together:
```
2017-04-22 09:33:28.199  INFO 10608 --- [nio-8080-exec-1] com.mtn.util.MtnLogger                   : REQUEST  - af59ea58-b457-4f8a-b713-a8d5cb1fb1af - http://localhost:8080/api/user
2017-04-22 09:33:28.227 ERROR 10608 --- [nio-8080-exec-1] c.mtn.controller.GlobalExceptionHandler  : UNEXPECTED EXCEPTION - af59ea58-b457-4f8a-b713-a8d5cb1fb1af

java.lang.RuntimeException: null
	at com.mtn.service.UserProfileService.findAll(UserProfileService.java:26) ~[classes/:na]
	at com.mtn.controller.UserProfileController.findAll(UserProfileController.java:32) ~[classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_20]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_20]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_20]
	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_20]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205) ~[spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:133) ~[spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:116) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:827) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:738) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:85) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:963) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:897) ~[spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970) [spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:861) [spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:687) [javax.servlet-api-3.1.0.jar:3.1.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846) [spring-webmvc-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:790) [javax.servlet-api-3.1.0.jar:3.1.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52) [tomcat-embed-websocket-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at com.mtn.correlation.RequestLoggingFilter.doFilter(RequestLoggingFilter.java:19) [classes/:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at com.mtn.correlation.CorrelationIdFilter.doFilter(CorrelationIdFilter.java:39) [classes/:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:105) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:81) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:197) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) [spring-web-4.3.7.RELEASE.jar:4.3.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:474) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:349) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:783) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:798) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1434) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_20]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_20]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-8.5.11.jar:8.5.11]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_20]

2017-04-22 09:33:28.231 ERROR 10608 --- [nio-8080-exec-1] com.mtn.util.MtnLogger                   : RESPONSE - af59ea58-b457-4f8a-b713-a8d5cb1fb1af - 500 - http://localhost:8080/api/user

```

This header, together with the GlobalExceptionHandler and RequestLoggingFilter, aim to reduce the amount of boilerplate code that must be written in the service and controller layer for logging as much as possible.

## Model Validation
Model validation is done in the service layer by the data services, after extending ValidatingDataService and implementing the required methods. This allows easy interaction between services and models, and keeps the domain models themselves clean, and without having to implement or extend other interfaces or classes to provide a consistent validation framework.
 
## Domain Model to View Model Conversion
Domain models are not directly exposed to the client, but instead should be passed through a Converter and converted to a View Model. This allows flexibility in what data is returned to the client in different circumstances, and again keeps domain models clean of any changes that would have been done to accommodate the client. This keeps a nice separation of concerns, puts all conversion logic into a single consistent framework, and again, keeps the domain models nice and clean.
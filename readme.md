# MTN REST Service
This is the core MTN REST service project.

## API Documentation
API documentation is achieved through Postman. The following link should import the current state of the collection into your Postman instance:

https://www.getpostman.com/collections/1426e247cae861736d62

Any updates to the collection will be published back to this same link, so all you should have to do is click the link to get the updates.

This collection uses environment variables. You'll want to set up Environments in Postman for Local, Dev, and Prod, and will need to provide "host", "idToken", and "accessToken" variables. Due to the nature of JWT, you'll have to update these tokens for each testing session. I've added some help for that on the main web service page once you sign in.

## Getting Started

### First-Time Installation
Follow these steps to get the app up and running locally:
	1. Install NodeJS
	2. Check out the project from Bitbucket
	3. Open project in IntelliJ
	4. Open IntelliJ's Terminal (which should open to the project root by default)
	5. Run `mvn clean install` (Must be done once, but then only needs to be done if the Maven dependencies are changed, which should be infrequent. You can also create a Run Configuration in IntelliJ to run this from a single click)
	6. `cd web` to enter the web directory
	7. Run `npm install -g gulp` - Installs Gulp globally (only needs to be done once)
	8. Run `npm install -g bower` - Installs Bower globally (only needs to be done once)
	9. Run `npm install` - Installs all the Node (mostly Gulp) dependencies (Must be done once, but then only needs to be done when the Gulp dependencies are changed, which should be very infrequent)
	10. Run `bower install` - Installs all the front-end dependencies (Must be done once, but then only needs to be done if front-end dependencies are changed, which should also be very infrequent)
	11. Run `gulp build` - Builds and compiles all the front-end files (Must be done at least once, then again when front-end files have changed. It would be good practice to run this before you start the application proper each time. Or, you can alternatively  run `gulp dev` from the Terminal alongside the application, or set up a Gulp Run Configuration to execute the "dev" task. Do this if you're actively developing front-end files, which will watch for changes to front-end files and automatically rebuild them and refresh your browser)
	12. Create an empty Postgres database on your local Postgres server
	13. Navigate in the project to web/src/main/resources, and copy the application-allen.properties file, replacing "allen" with your own name. 
	14. Replace the connection information in your new properties file with the connection information for your new database
	15. Create a Spring Boot Run Configuration called "Local" or some other fancy name
		* Set the Main Class to `com.mtn.Application`
		* Set the VM Options to `-Dspring.profiles.active=allen`, where "allen" is replaced with your own name, or the name you used in your new `application-<name>.properties` file
		* Set the Working Directory to the `web` folder
		* JRE must be a Java 1.8 or higher JDK
	16. Run the new Spring Boot configuration to start the application

Flyway should run all necessary database migration scripts on application startup. If you encounter an error during the Flyway migration, chances are your connection string information is incorrect. 

NOTE: spring.datasource.username needs to be a Superuser to enable the PostGis Extension.

### Running Without the Admin UI
Typically, if you are only working on back-end files, or you only need access to the API, and not the Admin UI, you can skip the Gulp process, and simply do:
1. Run `mvn clean install`
2. Run/Debug your Spring Boot Run Configuration

### Running With the Admin UI
If you need the Admin UI, do:
1. Run `mvn clean install`
2. Run `gulp build` (or `gulp dev` if you're actively developing the Admin UI)
3. Run/Debug your Spring Boot Run Configuration

### Admin UI Administrator User
The temporary admin account, which you'll need to use the Admin UI to create your own users, roles, and groups, is:
* system.administrator@mtnra.com:TK421whyarentyouatyourpost?

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

## Query Specifications (Dynamic WHERE Clause)
Dynamic WHERE clauses can be constructed using Spring Data Specifications. A Specification creates a structure of Predicates that are translated into the WHERE clause of a query, and is used with the default JpaRepository findOne, findAll, etc, methods. These Specifications do introduce a fair bit of boilerplate and duplicate code, but provide the functionality necessary for preventing access to "protected" records, deleted records, and for the client-specific query restrictions discussed as future requirements of this service.

For example:
```
    private static Specification<UserProfile> isNotDeleted() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
```

Which can be join with another Specification to create a composite Specification, as follows:
```
    public static Specification<UserProfile> queryWhereNotSystemAdministratorAndNotDeleted() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(isNotSystemAdministrator().toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
```

And used in any service as such:
```
    public Page<UserProfile> findAllUsingSpecs(Pageable page) {
        return userProfileRepository.findAll(UserProfileSpecifications.queryWhereNotSystemAdministratorAndNotDeleted(), page);
    }
```
# MTN REST Service
This is the MTN REST service project. It includes three modules: core, qa, and web.

Core includes domain, view, and simplified view models as well as migration scripts and application.properties which 
manage the database connection, Auth0, and other third party properties

## API Documentation
API documentation is achieved through Postman. The following link should import the current state of the collection 
into your Postman instance:

https://www.getpostman.com/collections/f990c571cc1333ee93f0

Any updates to the collection will be published back to this same link, so all you should have to do is click the link 
to get the updates. This may create a duplicate Postman collection, so you can delete your old one. This may need to 
be rethought if there is every collaboration on this project.

This collection uses environment variables. You'll want to set up Environments in Postman for dev and test 
and will need to provide "host" and "authorization" variables. The authorization variable will need to be updated
about every 12 hours as the tokens expire. The value for the authorization can be copied from a request in the dev 
console including the word "Bearer ".

## Getting Started

### Set up Development Environment
Follow these steps to get the app up and running locally:

1.  Check out the project from Bitbucket
2.  Open project in IntelliJ
3.  Open IntelliJ's Terminal (which should open to the project root by default)
4.  Run `mvn clean install` (Must be done once, but then only needs to be done if the Maven dependencies are changed, 
    which should be infrequent. You can also create a Run Configuration in IntelliJ to run this from a single click)
5.  Create an empty MySQL database on your local MySQL server
6.  Create environment variables
    *  OPENSHIFT_MYSQL_DB_HOST - ex. localhost
    *  OPENSHIFT_MYSQL_DB_PORT - ex. 3306
    *  OPENSHIFT_APP_NAME - Name of your DB, ex. mtn_dev
    *  OPENSHIFT_MYSQL_DB_USERNAME
    *  OPENSHIFT_MYSQL_DB_PASSWORD
    *  PLANNED_GROCERY_CLIENT_ID
    *  PLANNED_GROCERY_CLIENT_SECRET
    *  AUTH0_API_AUDIENCE
    *  AUTH0_ISSUER
    *  AUTH0_CLIENT_ID
    *  AUTH0_CLIENT_SECRET
    *  AUTH0_DOMAIN
7.  Create a Spring Boot Run Configuration
	* Set the Main Class to `com.mtn.Application`
	* Set the Working Directory to the `web` folder
	* JRE must be a Java 1.8 or higher JDK
8.  Run the new Spring Boot configuration to start the application

Flyway should run all necessary database migration scripts on application startup. If you encounter an error during the 
Flyway migration, chances are your connection string information is incorrect, or there is an error in one of your 
migration scripts. If the latter is true, you must resolve the issue manually. Be sure to update the schema_version 
table appropriately, otherwise it will continue to fail.

### Running Without IntelliJ config
1. Run `mvn clean install`
2. Run `java -jar web\target\web-1.0.0.jar` from the command line

## Correlation ID Header
A client may provide a "mtn-correlation-id" header, which will be used internally by the REST service and appended to 
most log statements related to that particular request. This allows quick location of relevant messages for debugging.

If the header is not provided by the client, it will be appended to the request internally by the REST service before 
processing the request, and will be returned in the response.

An example of this header in action in the server logs, tying several log statements together:
```
2017-04-22 09:33:28.199  INFO 10608 --- [nio-8080-exec-1] com.mtn.util.MtnLogger                   : REQUEST  - af59ea58-b457-4f8a-b713-a8d5cb1fb1af - http://localhost:8080/api/user
2017-04-22 09:33:28.227 ERROR 10608 --- [nio-8080-exec-1] c.mtn.controller.GlobalExceptionHandler  : UNEXPECTED EXCEPTION - af59ea58-b457-4f8a-b713-a8d5cb1fb1af

java.lang.RuntimeException: null
	at com.mtn.service.UserProfileService.findAll(UserProfileService.java:26) ~[classes/:na]
	at com.mtn.controller.UserProfileController.findAll(UserProfileController.java:32) ~[classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_20]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_20]
	...
2017-04-22 09:33:28.231 ERROR 10608 --- [nio-8080-exec-1] com.mtn.util.MtnLogger                   : RESPONSE - af59ea58-b457-4f8a-b713-a8d5cb1fb1af - 500 - http://localhost:8080/api/user

```

This header, together with the GlobalExceptionHandler and RequestLoggingFilter, aim to reduce the amount of boilerplate 
code that must be written in the service and controller layer for logging as much as possible.

## Model Validation
Model validation is done in the service layer by the data services, after extending ValidatingDataService and 
implementing the required methods. This allows easy interaction between services and models, and keeps the domain 
models themselves clean, and without having to implement or extend other interfaces or classes to provide a 
consistent validation framework.
 
## Domain Model to View Model Conversion
Domain models are not directly exposed to the client, but instead should be passed through a Converter and converted to 
a View Model. This allows flexibility in what data is returned to the client in different circumstances, and again 
keeps domain models clean of any changes that would have been done to accommodate the client. This keeps a nice 
separation of concerns, puts all conversion logic into a single consistent framework, and again, keeps the domain 
models nice and clean.

IMPORTANT

* Controllers shall only return Views (wrapped in ResponseEntities). They do not return full entities
* Controllers shall only receive Views. Do not tread views sent from clients as full entities, otherwise you risk overwriting data the user didn't intend to overwrite.

## Query Specifications (Dynamic WHERE Clause)
Dynamic WHERE clauses can be constructed using Spring Data Specifications. A Specification creates a structure of 
Predicates that are translated into the WHERE clause of a query, and is used with the default JpaRepository findOne, 
findAll, etc, methods. These Specifications do introduce a fair bit of boilerplate and duplicate code, but provide the 
functionality necessary for preventing access to "protected" records, deleted records, and for the client-specific 
query restrictions discussed as future requirements of this service.

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

And used in any service as such:
```
    public Page<UserProfile> findAllUsingSpecs(Pageable page) {
        return userProfileRepository.findAll(UserProfileSpecifications.isNotDeleted(), page);
    }
```
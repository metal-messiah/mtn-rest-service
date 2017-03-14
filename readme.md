# MTN REST Service
This is the core MTN REST service project.

## API Documentation
API documentation is achieved through Postman. The following link should import the current state of the collection into your Postman instance:

https://www.getpostman.com/collections/1426e247cae861736d62

Any updates to the collection will be published back to this same link, so all you should have to do is click the link to get the updates.

This collection uses environment variables. You'll want to set up Environments in Postman for Local, Dev, and Prod, and will need to provide "host", "idToken", and "accessToken" variables. Due to the nature of JWT, you'll have to update these tokens for each testing session. I've added some help for that on the main web service page once you sign in.
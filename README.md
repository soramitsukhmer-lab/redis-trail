# RedisTrail

 An effective audit trail solution can be crucial to an organization's security and data integrity as it can help find the who, what, when, and where. This helps organizations keep track of changes and investigate potential mistakes or viloations of policy. And, RedisTrail is built as an audit trail library using Redis Stack to allow any application to easily integrate for monitoring their data throughout their lifecycle, which is interfaced as Pub/Sub. It provides storage for the time-series data so that it can be queried easily and effectively through time.

[Insert app screenshots](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#uploading-assets)

# Overview video (Optional)

Here's a short video that explains the project and how it uses Redis:

[Insert your own video here, and remove the one below]

[![Embed your YouTube video](https://i.ytimg.com/vi/vyxdC1qK4NE/maxresdefault.jpg)](https://www.youtube.com/watch?v=vyxdC1qK4NE)

## Technical Stack

- Frontend - 
- Backend - _Spring Boot *_, _Redis_ 

## How it works

To illustrate how the library is used, we have built an application using Spring Boot framework. This application is mainly for product inventory, where we can create new product and update it. And, we integrate this application with our RedisTrail,  all the changes made on to the product will be stored in the RedisTrail via pub/sub, in which the application is a publisher, and the RedisTrail is the subscriber. The data is stored with a date time stamp, which indicates when the change was made. To simply put, we can query the change history of any product via the defined key (Product ID), the key can be defined in the application. Let say the application wants to track the user history via Username, the application can tell RedisTrail that it wants to use Username as the key to store the changes, and that is how the user history is being tracked within the system.

We will take product as example in the demo, below is the schema of the Product of the application:

### Database Schema

#### Product
```kotlin
public class Product : BaseEntity
{
 
}
```
Whenever a user makes any changes to the product data such as price, name, quantity, etc, those information will be updated immediately to RedisTrail via publish message event

### Initialization

The demo data is prepared using two operations: Create and Update.

**Create Product:**


**Update Product:**

Redis is maily used as the pub/sub for the communication between the main application and RedisTrail, and the event store for storing the events happened on the Product.

### How the data is stored:

Refer to [this example](https://github.com/redis-developer/basic-analytics-dashboard-redis-bitmaps-nodejs#how-the-data-is-stored) for a more detailed example of what you need for this section.

### How the data is accessed:

Refer to [this example](https://github.com/redis-developer/basic-analytics-dashboard-redis-bitmaps-nodejs#how-the-data-is-accessed) for a more detailed example of what you need for this section.


## How to run it locally?

[Make sure you test this with a fresh clone of your repo, these instructions will be used to judge your app.]

### Prerequisites

[Fill out with any prerequisites (e.g. Node, Docker, etc.). Specify minimum versions]

### Local installation

[Insert instructions for local installation]

## Deployment

To make deploys work, you need to create free account on [Redis Cloud](https://redis.info/try-free-dev-to)

### Google Cloud Run

[Insert Run on Google button](https://cloud.google.com/blog/products/serverless/introducing-cloud-run-button-click-to-deploy-your-git-repos-to-google-cloud)

### Heroku

[Insert Deploy on Heroku button](https://devcenter.heroku.com/articles/heroku-button)

### Netlify

[Insert Deploy on Netlify button](https://www.netlify.com/blog/2016/11/29/introducing-the-deploy-to-netlify-button/)

### Vercel

[Insert Deploy on Vercel button](https://vercel.com/docs/deploy-button)

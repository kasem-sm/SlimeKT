```http
  GET api/article/get/random
```
Fetches a random article from user's subscription to be shown as Daily Read. If the user is not authenticated or has not subscribed to any topics, any random article would be fetched. It is the responsibility of the client to make sure that repeated article is not 
shown in Daily Read.

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "title": "Kotlin Flows vs RX. Too debatable topic, let's dicuss them.",
        "description": "Flow is the Kotlin type that can be used to model streams of data. \nJust like LiveData and RxJava streams, Flow lets you implement the observer pattern: a software design pattern that consists of an object (a.k.a. observable, producer, source, emitter) that maintains a list of its dependents, called observers (subscribers, collectors, receivers, etc.), and notifies them automatically of any state changes. This terminology is used interchangeably throughout this article depending on the context, but those terms always refer to the same two entities.",
        "featuredImage": "https://miro.medium.com/max/1400/1*Ab9qskEuQtdigd7SYcx5CA.jpeg",
        "author": "Unknown",
        "topic": "Android",
        "timestamp": 1640667227843,
        "id": 1606672784
    }
}
```
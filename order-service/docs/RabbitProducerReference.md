# RabbitMQ Producer Quick Reference

This document provides a concise overview of core concepts and settings for **RabbitMQ Producers** using the Java client (`com.rabbitmq.client`).

## 1. Core Producer Flow

1.  **Connect:**
    * Use `ConnectionFactory` to configure connection details (host, port, user, pass, vhost).
    * Create a `Connection`.
    * Create a `Channel` from the `Connection`. (Use try-with-resources for cleanup).
2.  **Ensure Exchange Exists:**
    * Declare the target exchange using `channel.exchangeDeclare(name, type, durable)`. This is idempotent.
    * Producers typically declare *exchanges*, while consumers often declare *queues* and *bindings*.
3.  **Publish Message:**
    * Use `channel.basicPublish(exchange, routingKey, props, body)`.
    * `body` must be a `byte[]`.
    * `props` (`AMQP.BasicProperties`) are optional but crucial for settings.

## 2. Exchanges & Routing

* Producers publish to **Exchanges**, not directly to queues.
* The **Exchange Type** and **Routing Key** determine message routing:
    * **Direct:** Routes to queues bound with an exact matching routing key.
    * **Topic:** Routes based on wildcard matches (`*`, `#`) between the message routing key and queue binding patterns.
    * **Fanout:** Routes to *all* bound queues, ignoring the routing key.
    * **Headers:** Routes based on message header attributes (set in `AMQP.BasicProperties`).

## 3. Key Message Properties (`AMQP.BasicProperties`)

Set these when calling `basicPublish` using `AMQP.BasicProperties.Builder()` or `MessageProperties` constants:

* **Persistence (`deliveryMode = 2`):**
    * Makes messages survive broker restarts *if* routed to a *durable* queue.
    * Use `MessageProperties.PERSISTENT_TEXT_PLAIN` or set manually.
* **Expiration (`expiration = "ms"`)**
    * Per-message Time-To-Live (milliseconds as a String). Message discarded/DLQ'd if it exceeds this time in the queue.
* **Headers (`headers = Map<String, Object>`)**
    * Custom key-value metadata. Used by Headers exchanges and for application info.
* **Priority (`priority = int`)**
    * Suggests delivery order (higher value = higher priority). Requires target queue to be a priority queue (`x-max-priority`).

## 4. Reliable Publishing: Publisher Confirms

* **Crucial** for ensuring the broker received and processed your message.
* **Enable:** `channel.confirmSelect()`.
* **Mechanisms:**
    * **Synchronous:** `channel.waitForConfirmsOrDie(timeout)` - Slow, blocks per message/batch.
    * **Asynchronous:** `channel.addConfirmListener(ackCallback, nackCallback)` - High performance, recommended. Requires tracking message sequence numbers (`channel.getNextPublishSeqNo()`). Handle ACKs and NACKs in callbacks.

## 5. Quick Troubleshooting Checklist (Producer Side)

* **Connection:** Correct host, port, vhost, credentials? Network accessible? User has `write` permissions?
* **Publishing:** Correct exchange name & type? Exchange exists? Correct routing key for exchange type?
* **Confirms (NACKs):** Broker resource limits (memory/disk alarms)? Internal broker error (check logs)?
* **Performance:** Avoid sync confirms for high throughput. Reuse connections/channels (channels usually not thread-safe).

*This overview is intentionally brief. Refer to official RabbitMQ documentation and `com.rabbitmq.client` Javadocs for detailed explanations and advanced features.*
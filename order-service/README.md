# RabbitMQ: The Iron Throne of Messaging

Welcome, Lords and Ladies of the Seven Queues, to the README of our RabbitMQ module! Herein lies the wisdom you seek to master the art of messaging. We shall conquer the realm of asynchronous communication with this guide.

## Table of Contents
1. **What Is This Sorcery?**
2. **How Does Messaging Work? (Or, Why Raven Networks Are Obsolete)**
3. **Installing the Module**
4. **Basic Usage**
5. **Advanced Strategies**
6. **Troubleshooting (When winter is comming)**

---

## 1. What Is This Sorcery?
RabbitMQ is your loyal Hand of the King in the world of messaging. It is a message broker that enables your applications to send and receive messages (dispatches from the battlefield) asynchronously. With RabbitMQ, you can:

- Scale your services across kingdoms.
- Ensure reliable delivery of messages (unlike ravens who sometimes "forget" their way).
- Build flexible architectures worthy of ruling the Seven Queues.

## 2. How Does Messaging Work? (Or, Why Raven Networks Are Obsolete)

### A Tale of Houses (Architecture)
In the RabbitMQ realm, messages are the lifeblood of the kingdom. Here’s how it works:

- **Producers (The Smiths)**: Forge the messages and send them to the exchange.
- **Exchanges (The Council)**: Decides where the messages go. There are four kinds of councils:
    - **Direct**: Straight to the designated queue. Like Arya with a list.
    - **Topic**: Delegates messages based on rules. A bit like Varys’ network of whispers.
    - **Fanout**: Broadcasts messages to all queues. Think Daenerys when she’s mad.
    - **Headers**: Routes based on custom headers. The schemers of the game.
- **Queues (Strongholds)**: Store messages until a consumer (your knight) retrieves them.
- **Consumers (Knights)**: They process the messages. Loyal and tireless.

### The Oath (Acknowledgments)
Every message deserves respect. Consumers swear fealty by acknowledging receipt of messages, ensuring that no dispatch is ever lost in the chaos of battle.

---

## 3. Installing the Module (Forge Your Sword)

Before riding into battle, you must prepare:

1. Run the module using the docker-compose from the root of the project:
   ```bash
   docker-compose -f ./deployment/docker/infra.yml up -d
   ```

2. Ensure you have Docker installed and running:

3. Check your RabbitMQ management portal at [http://localhost:15672](http://localhost:15672). Default username/password: `guest/guest`. 

---

## 4. Basic Usage

You can check the implementation within this module, but here’s a quick overview:

1. **Create an Exchange**: 
    - Choose the type of exchange (direct, topic, fanout, headers).

2. **Create a Queue**:
    - Declare a queue.
    - Bind it to an exchange.
    - Set up bindings with routing keys (for direct and topic exchanges).

3. **Produce a Message**: 
    - Send a message to the exchange.
    - Specify the routing key (for direct and topic exchanges).
    - The message will be routed to the queue based on the exchange type.

4. **Consume a Message**:
    - Create a consumer that listens to the queue.
    - Process the message when it arrives.
    - Acknowledge the message after processing.

## 5. Advanced Strategies (Win the Great Messaging War)

### Dead Letter Queues 
Not all messages make it to their rightful destination. Dead Letter Queues act as the Wall, where rejected messages are exiled. Configure them to monitor failed deliveries.

### Priority Queues 
Some messages are more important than others. Establish a priority system to ensure high-priority messages are processed first .

### Retry Mechanisms
When a message fails, retry strategies ensure it gets another chance. Configure retry intervals to keep your kingdom from descending into chaos.

---

## 6. Troubleshooting (When the White Walkers Attack)

Here are some common issues and how to defeat them:

- **Issue: Messages Aren't Being Delivered**
    - Ensure your exchange and queue bindings are correct. Even Daenerys couldn’t rule without allies.

- **Issue: Messages Are Stuck in the Queue**
    - Check if your consumer knights are active. Lazy knights never win battles.

- **Issue: Connection Errors**
    - Verify that your RabbitMQ server is running. A kingdom without a throne is no kingdom at all.

---


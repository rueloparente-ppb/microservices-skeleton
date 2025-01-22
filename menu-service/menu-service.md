# Menu-service


## Backend Stack

### Spring Web - For our REST controller implementation
Provides essential tools to create RESTful APIs, including controllers, request mappings, and content negotiation.

### Validation - To handle input validation
Used to validate incoming request data, such as ensuring required fields are present, values meet constraints (e.g., length, format), and business logic integrity. 

### Spring Data JPA - For ORM and database interaction
Abstracts database operations with powerful query capabilities using JPA (Java Persistence API). Simplifies CRUD operations, pagination, and query execution with repository interfaces.

### PostgreSQL - The relational database
Provides reliable and scalable storage for structured data. 

### Flyway - For database migration and version control
Manages database schema changes in a controlled and versioned manner. Helps keep database migrations consistent across environments using scripts.

---

### Spring Boot Actuator - For exposing application health and metrics
Offers built-in endpoints to monitor application health, performance, and environment configurations. Useful for integrating with external monitoring systems.

### Prometheus - For metrics collection and monitoring
Gathers and stores metrics from the application (via Actuator) and other components. Allows querying and alerting for resource usage, performance, and uptime.

---

### Testcontainers - For testing with real dependencies
Enables integration tests with real database, message broker, or other service containers using Docker. Avoids mock dependencies and improves test reliability.

### RestIO - For testing out rest controllers
Enables easy testing of our restfull endpoints. Like MockMVC but better.

### Spring Boot DevTools - For hot-reloading during development
Automatically reloads application changes without restarting the server, because nobody got time for that.

### Spring Configuration Processor - For better IDE support in configuration
Generates metadata for Spring configuration properties, providing autocomplete suggestions and validation for custom configuration files.

---

## Architecture:
### Clean implementation

> **Our model is sacred—or, dare we say, the One Model to rule them all. But then... there are the others.**
>
> The Others never respect our domain. They come, like a horde of orcs, wielding their circular dependencies,
> leaving behind chaos, despair, and a tangled web of spaghetti code that even Shelob would envy.
>
> This is why we’ve fortified our realm with safeguards to shield ourselves from their meddling—our very own Rivendell of clean code.
> Here, we strive to keep our sanctuary intact (mostly… Sauron’s reach is long).

> **The DIP implementation, this one will go without LOR references**
> 
> At the core of clean architecture lies the Dependency Inversion Principle (DIP). Due to the philosophical nature of our discipline, 
> there are countless interpretations of what Robert C. Martin truly meant. From my perspective, 
> DIP is primarily intended to manage the interaction between an outer layer and an inner layer of an application, 
> ensuring dependencies point in the right direction.
> 
>A common example of this is the repository pattern. Repositories often rely on various technologies, making their implementations inherently 
> more unstable than the service layer that consumes them. To address this, we define an interface in the inner layers of our application. 
> This interface acts as an abstraction, allowing the outer layers to provide concrete implementations while keeping the core stable and technology-agnostic.
### Packaging is not layering

> **A Serious Note from the Council of Elders**
>
> Many a time, weary travelers confuse the concept of packages with that of layering. 
> But let it be known: these are two distinct realms, as different as the Shire and Mordor. 
> To avoid falling into this shadowy pit of misunderstanding, let us illuminate the path forward.
>
> The choices we made for our packaging weren’t random, oh no! These decisions were forged in the fires of Java class modifiers, 
> guided by the ancient and philosophical discipline of software engineering. Protecting the heart of our application lies the services layer—our vigilant Watchtower. 
> All logic must dwell beneath this level, for that is the natural order of things. 
> 
> It is why we created the domain package (though, in hindsight, core might have been a better name… whoops, even the wise cannot see all ends). 
> Only the service classes and exceptions are granted the status of public, ensuring that all who seek to interact with entities and repositories must pass 
> through the gates of the service layer. **One does not simply walk into direct repository access.**
>
> Now, this noble design has made the implementation of the DIP with repositories a bit… well, muckier. Like Frodo trudging through the Dead Marshes, 
> we had to place the repository implementations within the same package. And if we decide to implement caching things might get dirty.
> 
> For the time being, this compromise holds. But like Gandalf always pondering, we may need to revisit and refine this approach in the future. 
> Until then, we hold fast, keeping the darkness of chaos and circular dependencies at bay.

### The painting

![Layer Overview](../docs/imgs/ArchitectureV1.svg)


## The deployment

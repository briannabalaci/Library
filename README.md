Books Library

This is the backend for an application which allows users to manage books, book reading status and share them in a community. The library is secured, only logged in users can use the functionalities.
Technologies Used
•	Backend: Java Spring Boot
•	Containerization: Docker, Docker Compose
•	Load Balancer: NGINX
•	Database: PostgreSQL
•	Event Streaming: Apache Kafka
•	Security: JWT Authentication

Micro Services
1.	Authentication Microservice
The Authentication Microservice handles user authentication, registration, and JWT-based security. It ensures secure user management and token generation for authentication and authorization.

Key Features
•	User registration and account creation.
•	Secure login with password hashing and validation.
•	JWT token generation for authenticated access.
•	Public key exposure via JWKS for JWT verification.

REST API Endpoints
•	POST /register – Registers a new user in the system.
•	POST /login – Authenticates a user and returns a JWT token.
•	GET /jwks – Provides the JSON Web Key Set (JWKS) containing the public key used for JWT signature verification.

Core Components
•	UserController – Manages authentication and registration endpoints.
•	UserService – Handles business logic for authentication and user management.
•	TokenService – Generates and validates JWT tokens using RSA key pairs.
•	SecurityConfig – Configures security, CORS, and password encoding.
•	UserRepository – Manages persistence of user entities.

Security & Authentication
•	User authentication is managed via an OAuth Authorization Server, which issues JWT tokens.
•	RSA key pair is used for asymmetric signing, with the public key retrievable via /jwks.
•	Passwords are securely hashed using BCrypt.
This is the corresponding UML diagram generated with Intellij's diagrams plugin:
![auth](https://github.com/user-attachments/assets/e9442cad-b239-40fa-a990-38c451ac537e)

 
2. Book Microservice
The Book Service is responsible for managing books and their assignments to users. It provides REST API endpoints for book management and integrates authentication and logging mechanisms via Kafka.
Key Features
•	Book Management: Create, update, delete, and retrieve books.
•	Assignment System: Assign books to users and retrieve assigned books.
•	Security & Authentication: Uses OAuth2 with JWT for securing endpoints.
•	Logging & Monitoring: Logs method executions to Kafka.

REST API Endpoints
Book Management
•	GET /all – Retrieves all books.
•	GET /{id} – Retrieves a book by its ID.
•	POST /save – Creates a new book.
•	PUT /update – Updates a book's details.
•	PUT /update-status – Updates a book's status.
•	DELETE /delete/{id} – Deletes a book.

Book Assignment
•	POST /assign/{bookId} – Assigns a book to a user.
•	POST /all-assigned-to – Retrieves all books assigned to a specific user.
User Management
•	GET /user/{username} – Retrieves user details.
•	GET /user – Retrieves all users.
Security & Authentication
•	Uses OAuth2 Resource Server with JWT authentication.
•	Enforces method-level security with @PreAuthorize("isAuthenticated()").
•	Allows unrestricted access to WebSocket endpoints (/sba-websocket*, /topic*).
Logging & Kafka Integration
•	Aspect-Oriented Logging: @LogToKafka annotation logs method calls.
•	KafkaClient: Publishes logs to a Kafka topic.
•	Retry Mechanism: Ensures log delivery via RetryTemplate.
This is the corresponding UML diagram generated with Intellij's diagrams plugin:
 ![package3](https://github.com/user-attachments/assets/9a00a38e-a792-4340-a07f-e3519ae37927)


3.	Logger Microservice
The Logger Microservice is responsible for centralizing logs from other microservices. It listens to log messages published to a Kafka topic and records them for monitoring and debugging purposes.
How It Works
•	The microservice is set up as a Kafka consumer, meaning it listens for messages on a specific Kafka topic.
•	It receives log messages from other microservices and logs them using SLF4J.
•	This allows logs from multiple instances of different microservices to be aggregated in a single place.
Key Component
•	LoggerListener:
•	A Kafka consumer that listens to the log-message topic.
•	When a new log message arrives, it is processed and logged.
•	The Kafka topic name is configured dynamically using application properties (kafka-topic).
This microservice ensures that logs are captured efficiently and can be accessed for monitoring, debugging, and auditing.


Nginx as API Gateway and Load Balancer
In this microservices architecture, Nginx is used as an API Gateway and Load Balancer. It acts as a single entry point for all incoming requests, routing them to the appropriate microservices (authentication, book, logger). This setup ensures scalability, security, and efficient request distribution.
1. API Gateway Role
Nginx serves as an API Gateway, meaning it:
•	Exposes a unified entry point (http://localhost:4000).
•	Routes API requests to the correct backend services (authentication, book, logger).
•	Handles CORS, rate limiting, and security policies.
•	Hides internal microservices from the client, improving security.
2. Load Balancing
Load balancing helps distribute traffic efficiently across multiple instances of a service, preventing any single instance from being overwhelmed.
•	The book microservice is deployed with 2 replicas (deploy.replicas: 2), meaning there are two running instances.
•	Nginx will distribute incoming requests across these instances.
•	This improves system availability and performance.

3. Reverse Proxy
Nginx functions as a reverse proxy, meaning it:
•	Receives client requests.
•	Determines which microservice should handle the request.
•	Forwards the request and returns the response to the client.


 ![System](https://github.com/user-attachments/assets/10e41db9-c31b-4bd8-a042-862ce94e2d58)






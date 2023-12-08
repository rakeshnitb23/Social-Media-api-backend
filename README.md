# Social Media API Backend

[![Build and Test](https://github.com/yourusername/social-media-api-backend/actions/workflows/build.yml/badge.svg)](https://github.com/yourusername/social-media-api-backend/actions/workflows/build.yml)
[![Docker Publish](https://github.com/yourusername/social-media-api-backend/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/yourusername/social-media-api-backend/actions/workflows/docker-publish.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A production-ready RESTful API for a social media platform built with Spring Boot, designed to handle 100k+ users with optimized database queries and scalable architecture.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Database Design](#-database-design)
- [API Endpoints](#-api-endpoints)
- [Getting Started](#-getting-started)
- [Development](#-development)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Scaling Considerations](#-scaling-considerations)
- [Monitoring](#-monitoring)
- [Contributing](#-contributing)
- [License](#-license)

## 🔥 Features

- **User Management:** Registration, authentication with JWT tokens
- **Content Creation:** Create, read, update, and delete posts
- **Social Interactions:** Like posts, follow users
- **Feed Generation:** Personalized feed based on followed users
- **Admin Dashboard:** System statistics and metrics
- **Production-Ready:** Containerized, monitored, and scalable

## 🛠 Tech Stack

- **Framework:** Spring Boot 3.x
- **Language:** Java 17
- **Database:** PostgreSQL
- **ORM:** Hibernate/JPA
- **Authentication:** JWT with Spring Security
- **Migrations:** Flyway
- **API Documentation:** SpringDoc OpenAPI
- **Testing:** JUnit 5, Testcontainers
- **Containerization:** Docker, Docker Compose
- **CI/CD:** GitHub Actions
- **Monitoring:** Spring Actuator
- **Deployment:** Kubernetes manifests included

## 📁 Project Structure

```
social-media-api-backend/
├── .github/
│   └── workflows/            # GitHub Actions workflows
│       ├── build.yml         # CI workflow
│       └── docker-publish.yml # CD workflow
├── k8s/                      # Kubernetes manifests
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── ingress.yaml
│   ├── hpa.yaml
│   ├── postgres-secret.yaml
│   └── jwt-secret.yaml
├── src/
│   ├── main/
│   │   ├── java/com/socialmedia/api/
│   │   │   ├── controller/   # REST controllers
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── exception/    # Custom exceptions and handler
│   │   │   ├── model/        # JPA entities
│   │   │   ├── repository/   # JPA repositories
│   │   │   ├── security/     # Security config and JWT
│   │   │   ├── service/      # Business logic
│   │   │   └── SocialMediaApiApplication.java
│   │   └── resources/
│   │       ├── application.yml          # Common config
│   │       ├── application-dev.yml      # Dev profile
│   │       ├── application-prod.yml     # Prod profile
│   │       └── db/migration/            # Flyway migrations
│   │           ├── V1__create_tables.sql
│   │           └── V2__add_indexes.sql
│   └── test/                 # Test code
│       ├── java/
│       └── resources/
├── .gitignore
├── docker-compose.yml        # Local development setup
├── Dockerfile                # Production Docker image
├── pom.xml                   # Maven dependencies
├── setup-mvnw.sh             # Script to setup Maven wrapper
└── README.md                 # Project documentation
```

## 🗃 Database Design

### Database Schema

- **Users Table:** Stores user information and authentication details
- **Posts Table:** Stores post content with user references
- **Likes Table:** Tracks user likes on posts (many-to-many relationship)
- **Comments Table:** Stores comments on posts
- **Follows Table:** Tracks user-follows-user relationships

### Key Database Optimizations

- **Normalization:** Tables are designed to 3NF standards
- **Indexing:** Strategic indexes on foreign keys and commonly queried fields
- **Relationships:** Properly defined with referential integrity constraints
- **Query Optimization:** Efficient JPQL queries for feed generation

## 🔌 API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and get JWT token

### Posts

- `POST /api/posts` - Create a new post
- `GET /api/posts/{id}` - Get post by ID
- `GET /api/posts/user/{userId}` - Get posts by user
- `GET /api/posts/feed` - Get personalized feed (paginated)
- `DELETE /api/posts/{id}` - Delete a post

### Likes

- `POST /api/likes/{postId}` - Like a post
- `DELETE /api/likes/{postId}` - Unlike a post
- `GET /api/likes/{postId}/count` - Get like count for a post
- `GET /api/likes/{postId}/status` - Check if current user liked a post

### Admin

- `GET /api/admin/stats` - Get system statistics (admin only)

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- Docker and Docker Compose
- PostgreSQL (if running without Docker)

### Local Development Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/social-media-api-backend.git
   cd social-media-api-backend
   ```

2. Set up Maven wrapper:
   ```bash
   chmod +x setup-mvnw.sh
   ./setup-mvnw.sh
   ```

3. Start the application with Docker Compose:
   ```bash
   docker-compose up
   ```

4. Access the API at http://localhost:8080/api

### Manual Setup (Without Docker)

1. Set up a PostgreSQL database:
   ```bash
   createdb socialmedia
   ```

2. Configure database connection in `application-dev.yml`

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## 💻 Development

### Adding New Features

1. Create entity classes in `model/`
2. Create repositories in `repository/`
3. Add business logic in `service/`
4. Expose API endpoints in `controller/`
5. Add database migrations in `db/migration/`

### Code Style

The project follows Google Java Style Guide. Configure your IDE accordingly.

## 🧪 Testing

### Running Tests

```bash
./mvnw test
```

### Integration Tests with Testcontainers

Integration tests use Testcontainers to spin up a PostgreSQL container, ensuring tests run against a real database.

## 📦 Deployment

### Building Docker Image

```bash
./mvnw clean package
docker build -t social-media-api:latest .
```

### Kubernetes Deployment

1. Create Kubernetes secrets:
   ```bash
   kubectl apply -f k8s/postgres-secret.yaml
   kubectl apply -f k8s/jwt-secret.yaml
   ```

2. Deploy the application:
   ```bash
   kubectl apply -f k8s/deployment.yaml
   kubectl apply -f k8s/service.yaml
   kubectl apply -f k8s/ingress.yaml
   kubectl apply -f k8s/hpa.yaml
   ```

## 📈 Scaling Considerations

### Horizontal Scaling

- **Stateless Design:** The application is designed to be stateless, using JWT for authentication
- **Database Scaling:** 
  - Read replicas for handling high-read traffic
  - Connection pooling with HikariCP
  - Query optimization with indexes

### Caching Strategies

- Implement Redis caching for frequently accessed data (e.g., feeds, popular posts)
- Entity-level caching with Hibernate second-level cache

### Load Testing

Use JMeter to simulate high load:
- Test with 1000 concurrent users
- Measure response times for critical endpoints
- Identify bottlenecks

## 📊 Monitoring

### Health Checks

- `/actuator/health` - System health information
- `/actuator/info` - Application information

### Metrics

- `/actuator/metrics` - JVM and application metrics
- `/actuator/prometheus` - Prometheus-compatible metrics

## 👥 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

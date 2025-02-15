# Dockerizing Grocery Booking Microservices

## Prerequisites
- Docker
- Docker Compose
- Maven

## Build and Run Instructions

### 1. Build the Project
```bash
# Navigate to the project root
cd grocery-booking-microservices

# Build each service
mvn clean package -DskipTests
```

### 2. Build Docker Images
```bash
# Build images for all services
docker-compose build
```

### 3. Run the Application
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f
```

### 4. Stop the Application
```bash
# Stop and remove containers
docker-compose down
```

## Service Details
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Grocery Service**: http://localhost:8081
- **Order Service**: http://localhost:8082

## Troubleshooting
- Ensure all services are built before running
- Check Docker logs for any startup issues
- Verify network connectivity between services

## Notes
- The application uses a bridge network
- Services are configured with environment-specific profiles
- Depends_on ensures proper service startup order

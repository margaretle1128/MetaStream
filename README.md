# MetaStream

MetaStream is a media metadata management platform designed to handle large-scale TV and streaming metadata efficiently. It provides features for creating, updating, and searching programs, while leveraging modern technologies like Spring Boot, Next.js, Kafka, Redis, and MongoDB for scalable and high-performance operations.

## Features
- **Program Management**: Create, update, and manage TV and streaming metadata, including program types, titles, and schedules.
- **Real-Time Updates**: Utilizes Kafka for efficient message handling between microservices, allowing real-time data updates.
- **Fast Data Access**: Implements Redis caching for frequently accessed data, reducing the load on the MongoDB database and improving response times.
- **User Interface**: A responsive and scalable front-end built with Next.js and Tailwind CSS, ensuring a smooth user experience across devices.

## Technologies Used
- **Backend**: Java, Spring Boot, RESTful APIs
- **Frontend**: Next.js, Tailwind CSS
- **Database**: MongoDB
- **Messaging**: Kafka
- **Caching**: Redis
- **Deployment**: Docker
- **Version Control**: Git
- **Build Tool**: Maven

## Prerequisites
To run this project locally, ensure you have the following tools installed:
- Java 17+
- Node.js 16+
- Docker
- Kafka
- Redis
- MongoDB

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/metastream.git
cd metastream
```

### 2. Set up `application.properties` for each microservice
Each microservice will need its own `application.properties` file located under `src/main/resources`. **Do not hard-code sensitive data like URIs directly in these files.**

Example `application.properties` template for each microservice:

```properties
spring.application.name=<microservice-name>
spring.data.mongodb.uri=<your-mongodb-uri>
spring.data.mongodb.database=programData
server.port=<specific-port-for-microservice>

spring.redis.host=localhost
spring.redis.port=6379

spring.kafka.bootstrap-servers=kafka:9092
spring.kafka.consumer.group-id=<consumer-group-id>
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

Each microservice can define its own `spring.application.name`, `server.port`, and `spring.kafka.consumer.group-id`. Replace the placeholders with actual values based on your setup.

### 3. Run the backend services

#### Using Docker
To build and start all the required services, including Kafka, Redis, MongoDB, and the backend microservices, run the following command:

```bash
docker-compose up --build
```

### 4. Run the frontend (Next.js)
Navigate to the frontend directory and start the development server:
```bash
cd frontend
npm install
npm run dev
```

### 5. Access the application
Once both the backend and frontend are running, you can access the platform by navigating to `http://localhost:3000` in your browser.

## Usage

### Creating a Program
Navigate to the "Create Program" page to create new program metadata.

### Searching for Programs
Use the search feature to find programs based on their titles or IDs.

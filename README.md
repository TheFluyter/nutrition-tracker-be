# Nutrition Tracker Backend

A Spring Boot REST API for managing nutrition data and product information. This backend provides endpoints to retrieve, create, update, and delete products with detailed nutritional information.

## 🚀 Features

- **Product Management** - CRUD operations for nutrition products
- **Database Integration** - PostgreSQL with Flyway migrations
- **Feature-Based Architecture** - Modular, scalable code organization
- **RESTful API** - Clean, well-documented endpoints
- **Search & Filtering** - Find products by name or calorie range
- **Exception Handling** - Global error handling with proper HTTP status codes
- **CORS Support** - Ready for frontend integration

## 🏗️ Architecture

### Feature-Based Structure
```
src/main/java/com/thefluyter/nutrtiontrackerbe/
├── NutrtionTrackerBeApplication.java          # Main Spring Boot application
├── features/                                  # Feature-based modules
│   └── product/                               # Product management feature
│       ├── controller/                        # REST controllers
│       │   └── ProductController.java
│       ├── model/                             # JPA entities
│       │   └── Product.java  
│       ├── service/                           # Business logic
│       │   └── ProductService.java  
│       └── repository/                        # Data access layer
│           └── ProductRepository.java  
└── shared/                                    # Cross-cutting concerns
    ├── config/                                # Configuration classes
    │   └── WebConfig.java  
    ├── exception/                             # Exception handling
    │   ├── GlobalExceptionHandler.java  
    │   └── ResourceNotFoundException.java  
    └── util/                                  # Utility classes
        └── ResponseUtil.java
```

## 🛠️ Technology Stack

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **Flyway** - Database migrations
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd nutrtion-tracker-be
```

### 2. Database Setup
1. Install and start PostgreSQL
2. Create a database named `nutrition`
3. Update `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nutrition
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📊 Database Schema

### Products Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGSERIAL | Primary key |
| name | VARCHAR(100) | Product name |
| description | VARCHAR(500) | Product description |
| calories | DECIMAL(10,2) | Calories per serving |
| protein | DECIMAL(10,2) | Protein in grams |
| carbohydrates | DECIMAL(10,2) | Carbohydrates in grams |
| fat | DECIMAL(10,2) | Fat in grams |
| fiber | DECIMAL(10,2) | Fiber in grams |
| sugar | DECIMAL(10,2) | Sugar in grams |
| sodium | DECIMAL(10,2) | Sodium in milligrams |
| vitamin_c | DECIMAL(10,2) | Vitamin C in milligrams |
| potassium | DECIMAL(10,2) | Potassium in milligrams |
| created_at | TIMESTAMP | Creation timestamp |
| updated_at | TIMESTAMP | Last update timestamp |

## 🔌 API Endpoints

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?name={name}` | Search products by name |
| GET | `/api/products/calories?min={min}&max={max}` | Filter by calorie range |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Example Requests

#### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

#### Create Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Orange",
    "description": "A citrus fruit rich in vitamin C",
    "nutritionFacts": {
      "calories": 47.0,
      "protein": 0.9,
      "carbohydrates": 11.8,
      "fat": 0.1,
      "fiber": 2.4,
      "sugar": 9.4,
      "sodium": 0.0,
      "vitaminC": 53.2,
      "potassium": 181.0
    }
  }'
```

#### Search Products
```bash
curl -X GET "http://localhost:8080/api/products/search?name=banana"
```

#### Filter by Calories
```bash
curl -X GET "http://localhost:8080/api/products/calories?min=50&max=100"
```

## 📝 Sample Data

The application comes with pre-populated sample data:

### Banana
- **Calories**: 89
- **Protein**: 1.1g
- **Carbohydrates**: 22.8g
- **Fat**: 0.3g
- **Fiber**: 2.6g
- **Sugar**: 12.2g
- **Sodium**: 1.0mg
- **Vitamin C**: 8.7mg
- **Potassium**: 358.0mg

### Apple
- **Calories**: 52
- **Protein**: 0.3g
- **Carbohydrates**: 13.8g
- **Fat**: 0.2g
- **Fiber**: 2.4g
- **Sugar**: 10.4g
- **Sodium**: 1.0mg
- **Vitamin C**: 4.6mg
- **Potassium**: 107.0mg

## 🔧 Configuration

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn jacoco:report
```

## 📦 Building for Production

### Create JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/nutrtion-tracker-be-0.0.1-SNAPSHOT.jar
```

## 🚀 Deployment

### Docker (Optional)
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/nutrtion-tracker-be-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Rick the Fluyter

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL community for the robust database
- Flyway team for database migration tools

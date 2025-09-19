# Nutrition Tracker Backend - Feature-Based Architecture

## Project Structure

```
src/main/java/com/thefluyter/nutrtiontrackerbe/
├── NutrtionTrackerBeApplication.java          # Main Spring Boot application
├── features/                                  # Feature-based modules
│   └── product/                              # Product management feature
│       ├── controller/                       # REST controllers
│       │   └── ProductController.java
│       ├── model/                           # Domain models/entities
│       │   └── Product.java
│       ├── service/                         # Business logic
│       │   └── ProductService.java
│       └── repository/                      # Data access layer (future)
│           └── (to be added)
└── shared/                                  # Cross-cutting concerns
    ├── config/                              # Configuration classes
    │   └── WebConfig.java
    ├── exception/                           # Exception handling
    │   ├── GlobalExceptionHandler.java
    │   └── ResourceNotFoundException.java
    └── util/                                # Utility classes
        └── ResponseUtil.java
```

## Feature-Based Benefits

### 1. **Modularity**
- Each feature is self-contained with its own controller, model, service, and repository
- Easy to add new features without affecting existing ones
- Clear separation of concerns

### 2. **Scalability**
- Teams can work on different features independently
- Features can be developed, tested, and deployed separately
- Easy to identify and modify feature-specific code

### 3. **Maintainability**
- Related code is grouped together
- Easy to understand the structure of each feature
- Reduced coupling between different parts of the application

## Current Features

### Product Management
- **Controller**: `ProductController` - Handles HTTP requests for products
- **Model**: `Product` - Domain model with nutrition facts
- **Service**: `ProductService` - Business logic and mock data
- **Endpoints**:
  - `GET /api/products` - Get all products
  - `GET /api/products/{id}` - Get product by ID

## Future Features (Planned)

### Ingredient Management
- `features/ingredient/` - Manage ingredients with nutrition data

### User Management
- `features/user/` - User authentication and profiles

### Meal Planning
- `features/meal/` - Meal planning and tracking

### Nutrition Analysis
- `features/nutrition/` - Nutrition calculations and analysis

## Shared Components

### Configuration
- `WebConfig` - CORS and web configuration

### Exception Handling
- `GlobalExceptionHandler` - Centralized exception handling
- `ResourceNotFoundException` - Custom exception for missing resources

### Utilities
- `ResponseUtil` - Common response helpers

## Adding New Features

1. Create feature package: `features/{feature-name}/`
2. Add sub-packages: `controller/`, `model/`, `service/`, `repository/`
3. Implement the feature following the same pattern
4. Add shared components to `shared/` if needed across features

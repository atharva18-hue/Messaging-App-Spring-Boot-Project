# Messaging App - Spring Boot Project

A secure messaging application built with Spring Boot, featuring user authentication, message management, and a modern web interface.

## 🚀 Features

- **User Authentication & Authorization**
  - JWT-based authentication system
  - User registration and login
  - Role-based access control (Admin/User)
  - Secure logout functionality

- **Messaging System**
  - Send and receive messages between users
  - Message inbox with read/unread status
  - Message threading and organization
  - Real-time message delivery

- **User Management**
  - User profile management
  - Admin dashboard for user administration
  - User search and listing capabilities
  - Profile updates and modifications

- **Modern Web Interface**
  - Responsive HTML/CSS design
  - Interactive JavaScript functionality
  - Clean and intuitive user experience
  - Cross-browser compatibility

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.3.1, Java 22
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: JWT (JSON Web Tokens)
- **Frontend**: HTML5, CSS3, JavaScripts
- **Build Tool**: Maven
- **Validation**: Bean Validation API
- **Utilities**: Lombok, ModelMapper

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 22** (JDK 22)
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git**

## 🗄️ Database Setup

1. **Install PostgreSQL** on your system
2. **Create a new database**:
   ```sql
   CREATE DATABASE burak;
   ```
3. **Create a user** (optional, you can use existing user):
   ```sql
   CREATE USER burak WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE burak TO burak;
   ```

## ⚙️ Configuration

1. **Clone the repository**:
   ```bash
   git clone <your-repository-url>
   cd hw2
   ```

2. **Update database configuration** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/burak
   spring.datasource.username=burak
   spring.datasource.password=your_password
   ```

3. **Customize application settings** (optional):
   ```properties
   # Server port (default: 8080)
   server.port=8080
   
   # JWT secret key
   jwt.secret=your_jwt_secret_key
   
   # Hibernate DDL mode
   spring.jpa.hibernate.ddl-auto=update
   ```

## 🚀 Running the Application

### Method 1: Using Maven Wrapper
```bash
# Make sure you're in the project root directory
./mvnw spring-boot:run
```

### Method 2: Using Maven
```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Method 3: Using IDE
1. Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Run the `Hw2Application.java` main class

## 🌐 Accessing the Application

Once the application is running, you can access it at:

- **Main Application**: http://localhost:8080
- **Login Page**: http://localhost:8080/login.html
- **User Dashboard**: http://localhost:8080/user-dashboard.html
- **Admin Dashboard**: http://localhost:8080/admin-dashboard.html

## 📱 API Endpoints

### Authentication
- `POST /users/login` - User login
- `GET /users/logout` - User logout

### User Management
- `GET /users` - List all users (Admin only)
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user (Admin only)

### Messaging
- `GET /messages` - Get user messages
- `POST /messages` - Send new message
- `PUT /messages/{id}/read` - Mark message as read

## 👥 User Roles

### Regular User
- Send and receive messages
- View personal inbox
- Update profile information
- Access user dashboard

### Admin User
- All regular user privileges
- Manage all users in the system
- Access admin dashboard
- Delete users and messages

## 🔐 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Validation**: Strong password requirements
- **Input Validation**: Comprehensive input sanitization
- **Role-based Access Control**: Secure endpoint access
- **Session Management**: Proper token lifecycle management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Aurhor
- Atharva Chavhan
- Gmail: atharvachavhan18@gmail.com
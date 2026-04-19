# Dog API Project

A Java console application that interacts with the Dog API to fetch information about dog breeds, sub-breeds, and images, and provides functionality to save this data to a local MySQL database.

## Features

- **Dog API Integration**: Fetch all breeds, sub-breeds, and images from the Dog API.
- **Local Database Operations**: View, add, update, and delete breed, sub-breed, and image data in a local MySQL database.
- **Duplicate Prevention**: Save data from the API to the local database while avoiding duplicates.
- **Console-Based Interface**: User-friendly menu-driven interface for all operations.

## Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- MySQL Server 8.0 or higher
- Internet connection (for API calls)

## Installation and Setup

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd dogApiProject2
   ```

2. **Set up the database**:
   - Install and start MySQL Server.
   - Create a database named `dogdb`:
     ```sql
     CREATE DATABASE dogdb;
     ```
   - Update the database credentials in `src/main/resources/db.properties` if necessary:
     ```
     db.url=jdbc:mysql://localhost:3306/dogdb
     db.username=your_username
     db.password=your_password
     ```

3. **Build the project**:
   ```bash
   mvn clean compile
   ```

## Usage

1. **Run the application**:

   ```bash
   mvn exec:java -Dexec.mainClass="org.example.dogApi.Main"
   ```

2. **Main Menu Options**:
   - **Access Dog API**: Fetch data directly from the Dog API.
     - List all breeds
     - List all sub-breeds
     - List sub-breeds of a specific breed
     - Get random images
     - Get images by breed or sub-breed
   - **Local DB Operations**: Manage data in the local MySQL database.
     - View, add, update, delete breeds, sub-breeds, and images
   - **Save to Local DB**: Transfer data from the API to the database without duplicates.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/example/dogApi/
│   │       ├── Main.java                    # Main application class
│   │       ├── dao/                         # Data Access Objects
│   │       │   ├── BreedDAO.java
│   │       │   ├── ImageDAO.java
│   │       │   └── SubBreedDAO.java
│   │       ├── model/                       # Data models
│   │       │   ├── Breed.java
│   │       │   ├── Image.java
│   │       │   └── SubBreed.java
│   │       ├── service/                     # Business logic services
│   │       │   ├── DogApiService.java
│   │       │   ├── LocalDbService.java
│   │       │   └── SaveToLocalDbService.java
│   │       └── util/                        # Utilities
│   │           └── DBConnection.java
│   └── resources/
│       └── db.properties                    # Database configuration
└── test/
    └── java/                                # Unit tests
```

## Dependencies

- **MySQL Connector/J**: For database connectivity
- **JSON**: For parsing API responses

## API Reference

This application uses the [Dog API](https://dog.ceo/dog-api/) for fetching dog breed information.

## Testing

Run tests using Maven:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

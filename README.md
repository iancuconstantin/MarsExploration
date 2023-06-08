<h1 align="center">Welcome to Mars Exploration 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

> The Mars Colonization Rover Project aims to upgrade the Mars exploration rover to become a colonization rover. It will gather resources, construct command centers, and deploy additional rovers. The goal is to establish a self-sustaining settlement capable of extracting resources and preparing for human colonization.

## Author

👤 **Bulat Constantin; **

* LinkedIn: [@Iancu Constantin](https://linkedin.com/in/iancu-constantin-8b1458142)

👤 **Claudiu M. Tudor; **

* LinkedIn: [@Claudiu M. Tudor](https://linkedin.com/in/claudiu-mihai-tudor-9548ba274)

### Technologies ###

Following technologies were used in developing this app:

1. ***Java***: The core programming language used to develop the app, providing robust and efficient functionality.
2. ***Maven***: The build automation tool and dependency management system used to streamline the project's build process and handle external libraries and dependencies.
3. ***PostgresSQL***: A flexible and reliable open-source database system known for its advanced SQL capabilities and scalability.
4. ***JFrame/JPanel***: The Java Swing framework used to create the graphical user interface (GUI) for the simulation visualization. JFrame provides the main application window, while JPanel is used to create custom components and handle the rendering of the simulation elements.



### ✨ Screenshots ###
#### App running ####
![App running](https://i.imgur.com/a01VYCR.gif)
#### Map Example ####
![Map](https://i.imgur.com/O0jBNom.png)

## Installation
1. Clone the repository to your local machine.
2. Open the project in your preferred Integrated Development Environment IDE.
3. In the `Application` class, make sure to update the following database connection details:

```java
Database database = new Database(
        "jdbc:postgresql://localhost:5432/mars_exploration",
        "postgres",
        "postgres");
```
Replace the connection URL `jdbc:postgresql://localhost:5432/mars_exploration`, username `postgres`, and password `postgres` with the appropriate values for your PostgreSQL database.

4. In the same class, update the following simulation details:


```java
SimulationInput input = new SimulationInput(
        "src/main/resources/sprint3-3.map",
        new Coordinate(19, 9),
        1000,
        "src/main/resources/exploration-1111.log");
```

* `src/main/resources/sprint3-3.map`: This should be the file path of the map file you want to use for the simulation. Replace it with the correct file path.

* `new Coordinate(19, 9)`: Update the coordinates 19 and 9 with the desired starting coordinate for the simulation. This represents the initial position of the simulation.

* `1000`: Replace 1000 with the desired maximum number of simulation steps. This determines the limit for the simulation to prevent it from running indefinitely.

* `src/main/resources/exploration-1111.log`: Update the file path `src/main/resources/exploration-1111.log` with the desired file path to save the simulation log. Make sure the specified path is valid and accessible.

By updating these values, you can customize the simulation input parameters to match your specific requirements.

5. Navigate to the folder containing the `pom.xml` file in your project directory.
6. Build the project using the following command to package it into a JAR file:

```mvn package```

7. After the build is successful, navigate to the `\target` folder in your project directory:

```cd target```

Ensure that you have the latest PostgreSQL JDBC driver (e.g., `postgresql-42.6.0.jar`) and the generated JAR file (`mars-exploration-1.0-SNAPSHOT.jar`) available on your system.

8. Next, set up the `class_path` environment variable to include the paths to the PostgreSQL JDBC driver and the application JAR file.

9. . Run the application using the `java -jar` command followed by the name of the JAR file.

``` 
java -cp "path\to\postgres_driver\postgresql-42.6.0.jar;C:\local-directory\mars-exploration-simulator\target\mars-exploration-1.0-SNAPSHOT.jar" com.codecool.marsexploration.Application
```


## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

This project is [MIT](https://github.com/iancuconstantin/apiWeather/blob/main/LICENSE) licensed.

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
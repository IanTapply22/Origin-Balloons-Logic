# Origin Balloons Logic - Multi-Part Balloons

**Description:** An example Java project that demonstrates the usage of inverse kinematics and splines, specifically implementing logic for Origin Realms balloons using the Processing library.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Dependencies](#dependencies)

## Installation

To run this project, ensure that you have Java and Maven installed on your system. Then, follow these steps:

1. Clone the repository to your local machine.
```shell
git clone https://github.com/IanTapply22/Origin-Balloons-Logic.git
```

2. Navigate to the project directory.
```shell
cd Origin-Balloons-Logic
```

3. Build the project using Maven.
```shell
mvn clean package
```

## Usage

1. Once the project is built, run the main class OR execute the JAR file using the following command.
```shell
java -jar target/Origin-Balloons-Logic.jar
```

2. The application will launch, displaying the Origin Realms balloons simulation.

## Features

This Java project showcases the following features:

- Inverse kinematics: The "snakes" (soon to be balloon nodes) movements are calculated using inverse kinematics algorithms, allowing them to react realistically to mouse movement.
- Splines: The project implements splines to create smooth and natural-looking movement.
- Origin Realms balloons logic: The application demonstrates the logic specific to Origin Realms multi-part balloons, providing insights into their behavior and physics.

## Dependencies

This project utilizes the following dependencies:

- Java: [Version 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Maven: [Version 3.9.2](https://maven.apache.org/download.cgi)
- Processing library: [Version 3.5.4](https://github.com/processing/processing)
- Lombok: [Version 1.18.26](https://projectlombok.org/)

Make sure to have these dependencies installed via the pom.xml before building and running the project.

## Contributors

[Parker TenBroeck](https://github.com/ParkerTenBroeck) - Helped with implementing the math for restricting degrees of freedom for joints.

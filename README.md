<p align="center">
 <img width="100px" src="https://avatars.githubusercontent.com/u/60867791?s=200&v=4" align="center" alt="GitHub Readme Stats" />
 <h2 align="center">redis-time</h2>
 <p align="center">The Redis Audit Trail Application Built by Sora Warriors</p>
</p>
  <p align="center">
    <a href="http://www.apache.org/licenses/LICENSE-2.0.html">
      <img alt="LICENSE" src="http://img.shields.io/:license-apache-blue.svg?color=61c265" />
    </a>
    <a href="">
      <img alt="Application Port" src="https://img.shields.io/badge/port-8080-brightgreen?color=orange" />
    </a>
    <a href="https://github.com/soramitsukhmer/ciftp-payment-service/wiki">
      <img src="https://img.shields.io/badge/API%20Doc-redis--time%2Fwiki-brightgreen?colorA=61c265&colorB=4CAF50"/>
    </a>
    <a href="https://github.com/soramitsukhmer/redist-time/releases/latest">
      <img src="https://img.shields.io/badge/Releases-Pre--releases-brightgreen?colorA=61c265&colorB=4CAF50"/>
    </a>
  </p>
</p>

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Gradle 6.7](https://maven.apache.org)

## Running the application locally

To start redis stack using docker
```shell script
docker-compose up -d
```

To run application, there are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.soramitsukhmer.redistime.RedisTimeApplication` class from your IDE.


*Note* : If you have any problem with connecting local oracle, please use VM argument
```
./gradlew bootRun -Duser.country=en -Duser.language=en
```


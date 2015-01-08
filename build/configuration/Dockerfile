FROM build

ENV http_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80
ENV https_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80

WORKDIR /code

RUN wget https://github.com/billgql/docker-build/archive/master.zip
RUN unzip master.zip

RUN ["cp", "-rf", "docker-build-master/configuration/src", "/code/src" ]
RUN ["cp", "-rf", "docker-build-master/configuration/pom.xml", "/code/pom.xml"]
RUN ["mvn", "clean", "install", "package"]
EXPOSE 9998

CMD ["java", "-jar", "/code/target/rest-config-1.0-SNAPSHOT.jar"]
FROM build

#for passing proxy setting
ENV http_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80
ENV https_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80

WORKDIR /code

#get source code and unzip it
RUN wget https://github.com/billgql/docker-build/archive/master.zip
RUN unzip master.zip

#cp src folder to workdir and build it with maven
RUN ["cp", "-rf", "docker-build-master/configuration/src", "/code/src" ]
RUN ["cp", "-rf", "docker-build-master/configuration/pom.xml", "/code/pom.xml"]
RUN ["mvn", "clean", "install", "package"]

#port restful
EXPOSE 9998

#port admin
EXPOSE 9997

#start restful
CMD ["java", "-jar", "/code/target/rest-config-1.0-SNAPSHOT.jar"]

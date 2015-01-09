FROM dockerfile/java:oracle-java8

ENV http_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80
ENV https_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80

# Install maven
RUN apt-get update
RUN apt-get install -y maven wget curl unzip

WORKDIR /code

CMD ["bash"]

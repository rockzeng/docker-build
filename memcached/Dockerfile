FROM ubuntu:14.04.1

MAINTAINER Bill Li "billgql@hotmail.com"

ENV http_proxy http://mz8xks:AZUnitech89645@144.72.231.18:80

# Update apt sources

RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list

# Update the package repository

RUN apt-get update; apt-get upgrade -y; apt-get install locales

# Configure timezone and locale

RUN echo "Europe/Stockholm" > /etc/timezone; dpkg-reconfigure -f noninteractive tzdata

RUN export LANGUAGE=en_US.UTF-8; export LANG=en_US.UTF-8; export LC_ALL=en_US.UTF-8; locale-gen en_US.UTF-8; DEBIAN_FRONTEND=noninteractive dpkg-reconfigure locales

# Install base system

RUN DEBIAN_FRONTEND=noninteractive apt-get install -y wget curl memcached

USER daemon

EXPOSE 11211

ADD start.sh /start.sh

CMD ["bash", "start.sh"]

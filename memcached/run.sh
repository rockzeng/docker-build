docker build -t memcached .

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker run -d -p 11211:11211 --name=node1 memcached
docker run -d -p 11222:11211 --name=node2 memcached
docker run -d -p 11233:11211 --name=node3 memcached
docker run -d -p 11244:11211 --name=node4 memcached
docker ps -a 

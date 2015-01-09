docker build -t memcache .

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker run -d -p 11211:11211 --name=node1 memcache
docker run -d -p 11222:11211 --name=node2 memcache
docker run -d -p 11233:11211 --name=node3 memcache
docker run -d -p 11244:11211 --name=node4 memcache
docker ps -a 

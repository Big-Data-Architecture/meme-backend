mvn clean package -DskipTests=true

docker build -t us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/config-server:latest ./config-server
docker build -t us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/eureka-server:latest ./eureka-server
docker build -t us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/user-service:latest ./user-service
docker build -t us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/elastic-service:latest ./elastic-service

docker tag us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/config-server:latest us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/config-server:latest
docker tag us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/eureka-server:latest us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/eureka-server:latest
docker tag us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/user-service:latest us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/user-service:latest
docker tag us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/elastic-service:latest us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/elastic-service:latest

docker push us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/config-server:latest
docker push us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/eureka-server:latest
docker push us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/user-service:latest
docker push us-central1-docker.pkg.dev/big-data-architecture-202303/meme-backend/elastic-service:latest

docker-compose up
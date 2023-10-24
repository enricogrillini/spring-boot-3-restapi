# Stop BE
docker-compose down

# Build
cd ..
mvn clean package

# Run BE And follow
cd install
docker-compose up
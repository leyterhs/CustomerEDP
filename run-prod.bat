@echo off
echo Starting Production environment...
docker-compose --env-file .env.prod up -d
echo Production started. Backend at http://localhost:8080
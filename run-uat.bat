@echo off
echo Starting UAT environment...
docker-compose --env-file .env.uat up -d
echo UAT started. Backend at http://localhost:8080
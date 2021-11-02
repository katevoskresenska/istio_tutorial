#!/bin/bash

docker build -t alexpletnev/frontend-service:1.0 -f frontend/Dockerfile frontend
#docker push alexpletnev/frontend-service:1.0

docker build -t alexpletnev/books-service:1.0 -f books/Dockerfile books
#docker push alexpletnev/books-service:1.0

docker build -t alexpletnev/authors-service:1.0 -f authors/Dockerfile authors
#docker push alexpletnev/authors-service:1.0
#!/bin/bash

kubectl delete all --all -n istio-course

kubectl apply -f k8s/deployments/author.yml
kubectl apply -f k8s/deployments/book.yml
kubectl apply -f k8s/deployments/frontend.yml
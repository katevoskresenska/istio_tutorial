#!/bin/bash

kubectl apply -f k8s/gateway/course-ingress-controller.yml
kubectl apply -f k8s/gateway/course-gateway-controller.yml
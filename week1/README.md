#### Installation

Enable k8s cluster in Docker Desktop:

![image-20211025195234492](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211025195234492.png)

Deploy k8s dashboard:

```bash
$ export KUBECONFIG=''
$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.3.1/aio/deploy/recommended.yaml
```

```bash
$ kubectl proxy
```

Kubectl will make Dashboard available at http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/

To login to the dashboard, create ServiceAccount and add reference to it to ClusterRoleBinding:

```bash
$ kubectl apply -f dashboard-adminuser.yaml
# add service account to clusterrolebindings cluster-admin
$ kubectl get clusterrolebindings cluster-admin -o yaml > cluster_role_admin.yaml
$ kubectl apply -f cluster_role_admin.yaml
```

Retrieve access token:

```bash
$ kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa/admin-user -o jsonpath="{.secrets[0].name}") -o go-template="{{.data.token | base64decode}}"
```

![image-20211025203545204](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211025203545204.png)

Dowload and install Istio:

https://istio.io/latest/docs/setup/getting-started/

```bash
$ curl -L https://istio.io/downloadIstio | sh -
$ cd istio-1.11.4
$ export PATH=$PWD/bin:$PATH
$ istioctl install --set profile=demo -y
```

![image-20211025205914976](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211025205914976.png)

```bash
$ kubectl label namespace default istio-injection=enabled
$ kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml
$ kubectl get services
$ kubectl get po
$ kubectl exec "$(kubectl get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}')" -c ratings -- curl -sS productpage:9080/productpage | grep -o "<title>.*</title>"
$ kubectl apply -f samples/bookinfo/networking/bookinfo-gateway.yaml
$ istioctl analyze
```

<img src="/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211026112404941.png" alt="image-20211026112404941" style="zoom:60%;" />

Execute the following command to determine if your Kubernetes cluster is running in an environment that supports external load balancers:

```bash
$ kubectl get svc istio-ingressgateway -n istio-system
```

![image-20211026112614566](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211026112614566.png)

```bash
$ export INGRESS_HOST=127.0.0.1
$ export INGRESS_PORT=80
$ export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
$ echo "$GATEWAY_URL"
$ echo "http://$GATEWAY_URL/productpage"
```

![image-20211026185300998](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211026185300998.png)

Install Kiali and the other addons and wait for them to be deployed.

```bash
$ kubectl apply -f samples/addons
$ kubectl rollout status deployment/kiali -n istio-system
$ kubectl get po -n istio-system
```

![image-20211026190510064](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211026190510064.png)

Access the Kiali dashboard.

```bash
$ istioctl dashboard kiali
```

![image-20211026190806618](/Users/kvoskresenska/Library/Application Support/typora-user-images/image-20211026190806618.png)

To see trace data, you must send requests to your service. The number of requests depends on Istio’s sampling rate. You set this rate when you install Istio. The default sampling rate is 1%. You need to send at least 100 requests before the first trace is visible. To send a 100 requests to the `productpage` service, use the following command:

```bash
$ for i in $(seq 1 100); do curl -s -o /dev/null "http://127.0.0.1/productpage"; done
```



#### Uninstall

The Istio uninstall deletes the RBAC permissions and all resources hierarchically under the `istio-system` namespace. It is safe to ignore errors for non-existent resources because they may have been deleted hierarchically.

```bash
$ kubectl delete -f samples/addons
$ istioctl manifest generate --set profile=demo | kubectl delete --ignore-not-found=true -f -
```

The `istio-system` namespace is not removed by default. If no longer needed, use the following command to remove it:

```bash
$ kubectl delete namespace istio-system
```

The label to instruct Istio to automatically inject Envoy sidecar proxies is not removed by default. If no longer needed, use the following command to remove it:

```bash
$ kubectl label namespace default istio-injection-
```
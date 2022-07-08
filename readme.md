# This project aims to deploy a lightweight spring boot application on Kubernetes using Helm charts

**TL;DR**

`minikube start`

`helm install springboot-app --generate-name`

`export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=springboot-app,app.kubernetes.io/instance=springboot-app-1657196205" -o jsonpath="{.items[0].metadata.name}")`

`export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")`

`kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT`

Visit http://127.0.0.1:8080



**prerequisites :**

- A dockerhub account
- Docker installed on your machine for linux, Docker Desktop for Windows users
- Minikuke installed for Linux, or Docker Desktop with windows to run a Kubernetes cluster
- Kubectl installed ( comes with no installation with Docker Desktop )
- Helm installed


### Youtube video for Helm charts 
[Helm Chart Demo - How to create your first Helm Chart? - Part 6](https://www.youtube.com/watch?v=2dqQcou_MCU)

### Build the project
mvn clean install

### Build the image : cd root/of/project ( here root is helm03 )
`docker build -t skcgi/alpine-customer:1.0 -f src/main/resources/Dockerfile . `

This image is a simple "hello world" spring boot application running on Alpine. You can build your own application and push it to your
dockerhub repository.


### Push the image to your repository ( you must create one on dockerhub for example )
`docker push skcgi/alpine-customer:1.0`


### Just in case : Access to H2 database console when running locally
http://localhost:8080/h2-console    with url=, user, password => see application.properties


### Create charts for helm
`cd src/main/resources`

`helm create springboot-app`


### Adapt deployment.yml and values.yml ( find #here to localize adaptations in files )


### check the result aggregation
`helm template springboot-app`


### Verify your charts. It should return : 1 chart(s) linted, 0 chart(s) failed
`helm lint .\springboot-app\`


### Try a dry run to verify that everything is ok
`helm install mytest --debug --dry-run springboot-app`


### Start your minikuke or K8s cluster
tips on Windows : open a CMD window and apply :  
`doskey minikube=C:\MINIKUBE\minikube-windows-amd64.exe $* `

where C:\MINIKUBE\ is the directory where minikube is installed, and then : 

`minikube start`


### Deploy the application. 
`helm install springboot-app --generate-name`


### Check the result : it should display "status deployed"
`helm list --all`

`kubectl get all`

### How check log if application is not available :pill:
`kubectl get pods`

**result example here : the application seems not ready 0/1**

*NAME                                         READY   STATUS             RESTARTS      AGE*

*springboot-app-1657194438-67c7b796b5-8n9rw   0/1     CrashLoopBackOff   5 (66s ago)   4m9s*

`kubectl logs springboot-app-1657194438-67c7b796b5-8n9rw` --follow

**result example :**

*Exception in thread "main" java.lang.UnsupportedClassVersionError: com/sks/ToDeployInK8sApplication has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only rec*
*ognizes class file versions up to 52.0*
*at java.lang.ClassLoader.defineClass1(Native Method)*
*at java.lang.ClassLoader.defineClass(ClassLoader.java:763)*


### Forwarding port from Kubernetes to locahost to see your application in browser : 


1. Get the application URL by running these commands:
2. `export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=springboot-app,app.kubernetes.io/instance=springboot-app-1657196205" -o jsonpath="{.items[0].metadata.name}")`
3. `export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")`
4. `kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT`
5. Visit http://127.0.0.1:8080 to use your application
   
**result on http://127.0.0.1:8080:**

*Greetings from Spring Boot ! writing from customer controller*   :dragon:


### Before closing your computer : uninstall application and stop minikube
`helm list`
`helm delete <generate-name>`
`minikube stop`

Enjoy !! :sunglasses: :tropical_drink: :tropical_drink:

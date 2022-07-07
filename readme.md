# This project aim to deploy a light spring boot application on Kubernetes using Helm charts

**prerequisites :**

- A dockerhub account
- Docker installed on your machine for linux, Docker Desktop for Windows users
- Minikuke installed for Linux, or Docker Desktop with windows to run a Kubernetes cluster
- Kubectl installed ( comes with no installation with Docker Desktop )
- Helm installed


### youtube video for Helm charts 
[Helm Chart Demo - How to create your first Helm Chart? - Part 6](https://www.youtube.com/watch?v=2dqQcou_MCU)

### to build image : cd root/of/project ( here root is helm03 )
`docker build -t skcgi/alpine-customer:1.0 -f src/main/resources/Dockerfile . `

### push images to your repository ( you must create one on dockerhub for example )
`docker push skcgi/alpine-customer:1.0`

### access to H2 database console when running locally
http://localhost:8080/h2-console    with url=, user, password => see application.properties

### create charts for helm
`cd src/main/resources`
`helm create springboot-app`

### adapt deployment.yml and values.yml ( find #here to localize adaptations in files )

### check the result aggregation
`helm template springboot-app`

### verify your charts. It should return : 1 chart(s) linted, 0 chart(s) failed
`helm lint .\springboot-app\`

### try a dry run to verify that everything is ok
`helm install mytest --debug --dry-run springboot-app`

### start your minikuke or K8s cluster
tips on Windows : open cmd and apply :  doskey minikube=C:\MINIKUBE\minikube-windows-amd64.exe $*
where C:\MINIKUBE\ is the directory where minikube is installed
`minikube start`

### deploy application. 
`helm install springboot-app --generate-name`

### check result : shoud display "status deployed"
`helm list --all`
`kubectl get all`

### how check log if application is not available
`kubectl get pods`

**result example :**

*NAME                                         READY   STATUS             RESTARTS      AGE*

*springboot-app-1657194438-67c7b796b5-8n9rw   0/1     CrashLoopBackOff   5 (66s ago)   4m9s*

`kubectl logs springboot-app-1657194438-67c7b796b5-8n9rw` --follow

**result example :**

*Exception in thread "main" java.lang.UnsupportedClassVersionError: com/sks/ToDeployInK8sApplication has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only rec *
* ognizes class file versions up to 52.0 *
* at java.lang.ClassLoader.defineClass1(Native Method) *
* at java.lang.ClassLoader.defineClass(ClassLoader.java:763) *

### forwarding port from Kubernetes to locahost : 
NOTES:
1. Get the application URL by running these commands:
   export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=springboot-app,app.kubernetes.io/instance=springboot-app-1657196205" -o jsonpath="{.items[0].metadata.name}")
   export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
   
2. kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT

3. echo "Visit http://127.0.0.1:8080 to use your application"
   
**result : **

*Greetings from Spring Boot ! writing from customer controller*
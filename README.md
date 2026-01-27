
---

## 🔁 Jenkins CI/CD Pipeline Execution

![Jenkins Pipeline](docs/images/Jenkins%20Pipeline%20Workflow.jpeg)

**Description:**  
This screenshot shows the complete Jenkins declarative pipeline execution.  
The pipeline automates:
- Source code checkout from GitHub
- Build & unit testing using Maven
- SonarQube static code analysis
- Quality Gate enforcement
- Publishing artifacts to Nexus
- Building Docker images
- Pushing images to Docker Hub

---

## 🧩 CI/CD Architecture Diagram

![CI/CD Diagram](docs/images/Diagram%20of%20CI-CD.png)

**Description:**  
This diagram explains the overall CI/CD architecture and how GitHub, Jenkins, SonarQube, Nexus, Docker, and Kubernetes integrate together to deliver the application automatically.

---

## 🧪 SonarQube Quality Gate & Email Notification

![SonarQube Mail](docs/images/test%20sonarQube%20send%20mail.jpeg)

**Description:**  
When the SonarQube Quality Gate fails, Jenkins sends an automated email notification including:
- Job name
- Build number
- Failure status
- Direct link to build logs  
This ensures early detection of quality and security issues.

---

## 🗄️ Nexus Repository – Maven Artifacts

![Nexus Repo](docs/images/Nexus%20Repo.jpeg)

**Description:**  
This image confirms that the Spring Boot Maven artifact has been successfully published to Nexus Repository, providing centralized artifact storage, versioning, and rollback capability.

---

## 📦 Docker Image Published to Docker Hub

![Docker Hub](docs/images/DockerHub.jpeg)

**Description:**  
After a successful pipeline run, Jenkins builds a Docker image and pushes it to Docker Hub using the Jenkins build number as the image tag, ensuring full traceability.

---

## 🚀 GitOps Workflow with Argo CD

![Argo GitOps](docs/images/Argo%20CD_GitOps%20workflow.jpeg)

**Description:**  
This diagram demonstrates the GitOps deployment model where Git acts as the single source of truth and Argo CD continuously synchronizes Kubernetes manifests with the live cluster state.

---

## 📊 Argo CD Application – Live Kubernetes State

![Argo App](docs/images/Argo_app.jpeg)

**Description:**  
This view shows the Argo CD application tree including Deployments, ReplicaSets, Pods, and Services.  
The application status is **Healthy** and **Synced**, confirming a successful deployment.

---

## 🛠️ Tools & Technologies
- Java (Spring Boot)
- Jenkins
- Maven
- SonarQube + JaCoCo
- Nexus Repository
- Docker
- Docker Hub
- Argo CD
- Kubernetes

---

## ✅ Key DevOps Concepts Demonstrated
- End-to-End CI/CD Automation
- Quality Gates & Shift-Left Testing
- Artifact Management
- Docker Image Lifecycle
- GitOps-Based Continuous Delivery
- Kubernetes Deployment

---

## 📌 Conclusion
This project represents a **real-world DevOps implementation** combining CI, CD, and GitOps best practices.  
It is ideal for DevOps portfolios, interviews, and hands-on learning.

⭐ Feel free to star the repository and extend it with monitoring, logging, or security scanning.

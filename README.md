# 🚀 Jenkins Full DevOps CI/CD & GitOps Project

This repository demonstrates a **complete end-to-end DevOps workflow** for a **Spring Boot application**, starting from code commit and CI/CD automation, passing through code quality enforcement, artifact management, containerization, and ending with **GitOps-based deployment using Argo CD on Kubernetes**.

🔗 **Repository:**  
https://github.com/Yassin-Abuelsheikh/Jenkins-Full-Project

---

## 🧩 CI/CD High-Level Architecture

![CI/CD Diagram](docs/images/Diagram%20of%20CI-CD.png)

**Description:**  
This diagram illustrates the full CI/CD architecture used in this project.  
The workflow starts when code is pushed to GitHub, triggering Jenkins to run build, test, and quality analysis stages. Artifacts are stored in Nexus, Docker images are pushed to Docker Hub, and deployment is handled using GitOps with Argo CD.

---

## 🔁 Jenkins CI/CD Pipeline Execution

![Jenkins Pipeline](docs/images/Jenkins%20Pipeline%20Workflow.jpeg)

**Description:**  
This screenshot shows the full Jenkins declarative pipeline execution.  
The pipeline includes the following stages:
- Checkout source code from GitHub
- Build & unit tests using Maven
- SonarQube static code analysis
- Quality Gate validation
- Publish artifact to Nexus Repository
- Build Docker image
- Push Docker image to Docker Hub

---

## 🧪 SonarQube Analysis – Quality Gate FAILED

![SonarQube Failed](docs/images/test%20sonarQube%20send%20mail.jpeg)

**Description:**  
This image shows a **failed SonarQube Quality Gate** scenario.  
When the code does not meet the defined quality standards (bugs, vulnerabilities, code smells, or insufficient test coverage), the pipeline is **immediately aborted**.

At this point:
- Deployment is blocked
- Jenkins sends an **automatic email notification** with build details
- Bad code is prevented from moving forward

This enforces **shift-left testing** and strong code quality governance.

---

## ✅ SonarQube Analysis – Quality Gate PASSED

![SonarQube Passed](docs/images/WhatsApp%20Image%202026-01-26%20at%2012.55.33%20PM.jpeg)

**Description:**  
After fixing the issues and improving code quality, the SonarQube analysis passes successfully.  
Once the **Quality Gate is green**, the pipeline is allowed to continue to the next stages such as artifact publishing and containerization.

---

## 🗄️ Artifact Published to Nexus Repository

![Nexus Repo](docs/images/Nexus%20Repo.jpeg)

**Description:**  
This image confirms that the Spring Boot Maven artifact has been successfully published to **Nexus Repository**.

Nexus is used as:
- A centralized artifact repository
- A versioned and reliable storage for build outputs
- A source for future deployments and rollbacks

---

## 📦 Docker Image Pushed to Docker Hub

![Docker Hub](docs/images/DockerHub.jpeg)

**Description:**  
After publishing the artifact, Jenkins builds a Docker image from the Spring Boot application and pushes it to **Docker Hub**.

Each image is tagged using the Jenkins build number, ensuring:
- Full traceability
- Easy rollback
- Clear mapping between code, artifact, and container image

---

## 🚀 GitOps Workflow with Argo CD

![Argo GitOps](docs/images/Argo%20CD_GitOps%20workflow.jpeg)

**Description:**  
This diagram represents the **GitOps deployment model** used in the project.

Key principles:
- Git is the single source of truth
- Kubernetes manifests are stored in a Git repository
- Argo CD continuously monitors Git
- Any change is automatically synchronized to the cluster

No manual `kubectl apply` is required.

---

## 📊 Argo CD Application – Live Kubernetes State

![Argo App](docs/images/Argo_app.jpeg)

**Description:**  
This view shows the Argo CD application tree displaying live Kubernetes resources:
- Deployment
- ReplicaSet
- Pods
- Service

The application status is **Healthy** and **Synced**, confirming a successful GitOps deployment.

---

## 🛠️ Tools & Technologies
- **Language:** Java (Spring Boot)
- **CI/CD:** Jenkins
- **Build Tool:** Maven
- **Code Quality:** SonarQube + JaCoCo
- **Artifact Repository:** Nexus Repository
- **Containerization:** Docker
- **Container Registry:** Docker Hub
- **CD / GitOps:** Argo CD
- **Orchestration:** Kubernetes

---

## ✅ Key DevOps Concepts Demonstrated
- End-to-End CI/CD Automation
- Quality Gates & Shift-Left Testing
- Artifact Versioning and Management
- Docker Image Lifecycle
- GitOps-Based Continuous Delivery
- Kubernetes Application Deployment

---

## 📌 Conclusion
This project represents a **real-world DevOps pipeline** that closely matches production environments.  
It demonstrates how CI, code quality, artifact management, containerization, and GitOps can be combined into a single, automated workflow.

⭐ Feel free to star the repository and extend it with monitoring, logging, or security scanning.

# 👥 Employee Management System

> A complete employee management web app built for my MCA major project.  
> It helps HR or admin folks manage employee records, track locations, handle leave applications, and see real-time stats — all in one place.

---

## 📌 Why I built this

During my 4th semester MCA project work, I realized that most small-to-medium organizations still manage employee data using spreadsheets or manual records. That’s slow, error-prone, and hard to maintain. So I decided to build a clean, functional, and scalable system that solves these issues.

This project is my attempt to combine everything I’ve learned — Spring Boot, Thymeleaf, PostgreSQL, Docker, and CI/CD — into one meaningful application.

---

## ✨ What it can do

- Add, edit, view, and delete employee records
- Group employees by location (12 cities like NOIDA, DELHI, MUMBAI, etc.)
- Apply for leave, check leave balance, and view history
- Admin can approve or reject leave requests
- Dashboard shows total employees, location-wise charts, and recent activity
- Search employees by name, email, or location
- Fully responsive — works on mobile, tablet, and desktop
- Dockerized for easy setup and deployment

---

## 🧰 Tech Stack (what I used)

| Layer | Tools |
|-------|-------|
| Backend | Spring Boot, Java 25, Hibernate |
| Frontend | Thymeleaf, HTML, CSS, Bootstrap |
| Database | PostgreSQL (running inside Docker) |
| Build | Maven |
| Container | Docker, Docker Compose |
| Version Control | Git, GitHub |
| CI/CD | GitHub Actions |
| IDE | IntelliJ IDEA |

---

## 📁 How the project is organized

EMPLOYEE-MANAGEMENT/
├── src/main/java/ # Controllers, Models, Services, Repos
├── src/main/resources/
│ ├── templates/ # All HTML files (Thymeleaf)
│ ├── static/ # CSS, images
│ └── application.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md




---

## ▶️ How to run it on your machine

### Step 1: Clone the repo
```bash
git clone https://github.com/yourusername/employee-management.git
cd employee-management


mvn clean package -DskipTests

docker run --name employee-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=employeedb \
  -p 5432:5432 \
  -d postgres:17



  java -jar target/EMPLOYEE-MANAGEMENT-0.0.1-SNAPSHOT.jar

  http://localhost:8080





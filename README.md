# 🏦 Digital Banking Transaction System

<p align="center">
Enterprise Banking Application built using Spring Boot, Spring Security, JWT, MySQL and Angular.
A real-world banking system designed using enterprise software architecture and industry best practices.
</p>

<p align="center">
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Angular](https://img.shields.io/badge/Angular-20-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![JWT](https://img.shields.io/badge/JWT-Security-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

</p>

---

# 📌 Table of Contents

- Project Overview
- Project Goals
- Features
- Technology Stack
- System Architecture
- Project Structure
- Database Design
- User Roles
- Business Workflows
- API Modules
- Enterprise Practices
- Getting Started
- Installation
- Configuration
- Running the Project
- Future Enhancements
- Learning Outcomes
- Current Progress
- Screenshots
- Author

---

## 📖 Overview

Digital Banking Transaction System is a full-stack enterprise banking application that simulates real-world banking operations.

The project is developed to understand how modern banking software is designed in large organizations while following enterprise architecture, secure REST APIs, clean coding standards, layered architecture, SOLID principles, and modern software development practices.

Unlike traditional CRUD applications, this project focuses on business workflows, transaction consistency, security, scalability, and maintainability.

---

## 🎯 Project Goals

- Build a real-world banking application
- Learn enterprise backend development
- Master Spring Boot architecture
- Understand banking business logic
- Implement secure authentication
- Build scalable REST APIs
- Follow Clean Architecture
- Learn transaction management
- Understand enterprise design patterns
- Prepare for Software Engineer interviews

---

# ✨ Key Features
✔ JWT Authentication
✔ Role-Based Authorization
✔ Customer Management
✔ Employee Management
✔ Account Management
✔ Deposit Money
✔ Withdraw Money
✔ Fund Transfer
✔ Beneficiary Management
✔ Loan Management
✔ Loan Repayment
✔ Dashboard & Analytics
✔ Transaction History
✔ Internet Banking
✔ Global Exception Handling
✔ Validation
✔ Enterprise DTO Pattern
✔ Repository Pattern
✔ Mapper Pattern
✔ Transaction Management
✔ Clean Architecture

---

# 🛠 Technology Stack

## Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- Maven
- Lombok
- Validation API
- MapStruct
- MySQL

---

## Frontend

- Angular
- TypeScript
- Bootstrap
- HTML5
- CSS3
- AG Grid


---

## Tools

- VS Code
- Postman
- Git
- GitHub
- MySQL Workbench

---

# 🏛 System Architecture

```
               Angular UI
                      │
                      ▼
          REST Controllers
                      │
                      ▼
             Service Layer
                      │
                      ▼
              Mapper Layer
                      │
                      ▼
          Repository Layer
                      │
                      ▼
            MySQL Database
```

---

## 🚀 Enterprise Highlights

- Layered Architecture
- SOLID Principles
- Clean Code
- DTO Pattern
- Repository Pattern
- Mapper Pattern
- Global Exception Handling
- Validation
- JWT Authentication
- Role-Based Security
- Transaction Management
- Code Sequence Generator
- Pessimistic Locking
- Modular Design
- Reusable Components
---

# 📦 Completed Modules

✅ Authentication Module
✅ User Management
✅ Employee Management
✅ Customer Management
✅ Account Management
✅ Deposit Module
✅ Withdraw Module
✅ Fund Transfer
✅ Beneficiary Management
✅ Internet Banking
✅ Loan Management
    • Apply Loan
    • Approve Loan
    • Reject Loan
    • EMI Calculator
    • Loan Disbursement
    • Loan Repayment
    • Repayment History
    • Dashboard
    • Loan Closure Certificate
---

# 👥 User Roles

## 👨‍💼 Admin

- Manage Employees
- View Reports
- Monitor Transactions
- Manage Users

---

## 👨‍💻 Employee

- Register Customer
- Open Account
- Deposit Money
- Withdraw Money
- Transfer Money
- Search Customer

---

## 👤 Customer

- Login
- View Profile
- View Balance
- Transaction History
- Fund Transfer

---

# 🔄 Business Workflows

## Customer Registration

```
Employee

      │

      ▼

Customer Request

      │

      ▼

Validation

      │

      ▼

Generate Customer Code

      │

      ▼

Map DTO → Entity

      │

      ▼

Save Customer

      │

      ▼

Return Response
```

---

## Money Transfer

```
Sender Account

      │

      ▼

Validate Balance

      │

      ▼

Debit Sender

      │

      ▼

Credit Receiver

      │

      ▼

Create Transaction

      │

      ▼

Commit Transaction

      │

      ▼

Return Success
```

---

# 📡 API Modules

- Authentication API
- Customer API
- Account API
- Transaction API
- Employee API
- Dashboard API
- Reports API

---

# 🧱 Enterprise Practices

This project follows enterprise software development standards.

- Layered Architecture
- DTO Pattern
- Repository Pattern
- Service Layer
- Mapper Layer
- Validation
- Exception Handling
- Global Exception Handler
- API Response Wrapper
- Transaction Management
- Pessimistic Locking
- Code Sequence Generator
- Clean Code
- SOLID Principles

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/yourusername/digital-banking-system.git
```

---

## Backend Setup

```bash
cd digital-banking-system
```

Run

```bash
mvn clean install
```

Start application

```bash
mvn spring-boot:run
```

---

## Frontend Setup

```bash
cd angular
```

Install packages

```bash
npm install
```

Run

```bash
ng serve
```

---

# 🛢 Database Configuration

Update your `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_banking

spring.datasource.username=root

spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
```

---

# 📈 Future Enhancements

- Loan Management
- Fixed Deposit
- Beneficiary Management
- QR Payments
- UPI
- Credit Cards
- Debit Cards
- Email Notification
- SMS Notification
- Dashboard Analytics
- Audit Logs
- KYC Upload
- PDF Statement
- Interest Calculation
- Branch Management

---

# 📚 Learning Outcomes

This project demonstrates practical understanding of:

- Spring Boot
- Spring Security
- JWT Authentication
- REST APIs
- JPA & Hibernate
- Entity Relationships
- DTO Mapping
- Exception Handling
- Transaction Management
- Locking Mechanisms
- Repository Pattern
- SOLID Principles
- Clean Architecture
- Angular Integration
- Enterprise Development Practices

---

# 🚀 Current Progress

## ✅ Completed

- Spring Boot Setup
- Maven Configuration
- MySQL Integration
- JPA & Hibernate
- Entity Relationships
- DTO Layer
- Repository Layer
- Mapper Layer
- Exception Handling
- API Response Wrapper
- Code Sequence Module
- Customer Registration
- Customer Search

---

## 🚧 In Progress

- Customer List API
- Customer Update API
- Customer Delete API

---

## ⏳ Upcoming

- Account Module
- Deposit Module
- Withdraw Module
- Transfer Module
- Transaction History
- JWT Authentication
- Angular Frontend
- Dashboard
- Reports

---



**Tech Stack**

- Java
- Spring Boot
- Angular
- MySQL
- REST APIs
- Hibernate

---

# ⭐ Project Goal

The objective of this project is **not only to implement CRUD operations**, but to understand how enterprise banking systems are designed in real organizations.

The project emphasizes:

- Scalable Architecture
- Maintainable Code
- Reusable Components
- Secure REST APIs
- Business-Oriented Design
- Enterprise Development Practices

This repository serves as a learning project as well as a portfolio project demonstrating backend development skills suitable for software engineering roles.

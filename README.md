# Digital Banking System

A full-stack digital banking application built using Java and modern web technologies. This project allows users to create accounts, deposit and withdraw money, transfer funds, check balances, and manage their banking records securely.

## 1. Project Overview

This application is designed to simulate a real-world banking system with:
- User registration and login
- Account creation and management
- Balance checking
- Deposit and withdrawal operations
- Fund transfer between accounts
- Transaction history
- Role-based access for admin and customers
- Secure backend APIs

## 2. Purpose of the System

The purpose of this project is to provide:
- a secure banking workflow
- a clean separation between frontend and backend
- easy monitoring of transactions
- a real-world Java-based enterprise style project for learning and practice

## 3. Tech Stack

Backend:
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- REST APIs

Frontend:
- HTML
- CSS
- JavaScript
- React / Angular / Thymeleaf (depends on project)

Database:
- MySQL / PostgreSQL / H2

Tools:
- Maven or Gradle
- Git
- Postman for API testing

## 4. Project Structure

A typical project structure looks like this:

```text
digital-banking-system/
├── backend/
│   ├── src/main/java
│   │   ├── com.example.banking
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── entity
│   │   │   ├── exception
│   │   │   ├── repository
│   │   │   ├── security
│   │   │   ├── service
│   │   │   └── BankingApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── schema.sql
│   └── pom.xml
├── frontend/
│   ├── src
│   ├── public
│   └── package.json
├── .gitignore
├── README.md
└── database.sql
```

## 5. Main Components and Their Roles

### 5.1 Entity Layer
The entity layer defines database tables as Java classes.

Examples:
- User
- Account
- Transaction
- BankBranch
- Admin

These classes map to database tables using JPA annotations.

#### Function Explanation
- User entity: stores customer information
- Account entity: stores account number, balance, account type, and owner
- Transaction entity: stores money movement records
- Admin entity: handles administrative tasks and roles

### 5.2 Repository Layer
Repositories are interfaces used to interact with the database.

Examples:
- UserRepository
- AccountRepository
- TransactionRepository

#### Functions commonly included
- save()
- findById()
- findAll()
- findByEmail()
- findByAccountNumber()
- deleteById()

#### Explanation
These methods let the application store, fetch, update, and delete data from the database without writing raw SQL manually.

### 5.3 Service Layer
The service layer contains the core business logic.

Examples:
- UserService
- AccountService
- TransactionService
- AuthService
- AdminService

#### Core Functions and Explanation

##### UserService
- registerUser()
  - Creates a new user account
  - Validates details like name, email, phone, password
  - Saves record to database

- loginUser()
  - Verifies credentials
  - Returns token or session data

- getUserProfile()
  - Fetches the profile of a logged-in user

- updateUserProfile()
  - Updates user information such as phone or address

- deactivateUser()
  - Disables a user account if needed

##### AccountService
- createAccount()
  - Creates a bank account for a user
  - Assigns account number and account type

- getAccountById()
  - Finds account details using account ID

- getAccountByUserId()
  - Retrieves all accounts linked to a user

- checkBalance()
  - Reads current balance for an account

- updateBalance()
  - Updates balance after deposit or withdrawal

- closeAccount()
  - Marks an account inactive

##### TransactionService
- deposit()
  - Adds money to the account
  - Validates the amount
  - Records transaction in the ledger

- withdraw()
  - Removes money from the account
  - Checks if sufficient balance exists

- transfer()
  - Moves money from one account to another
  - Validates sender balance
  - Updates both accounts
  - Logs transaction details

- getTransactionHistory()
  - Returns all past transactions for a user or account

- getTransactionById()
  - Retrieves one transaction record

##### AuthService
- authenticate()
  - Validates username/email and password

- generateToken()
  - Creates a secure JWT or session token

- validateToken()
  - Checks whether a request is authenticated

- refreshToken()
  - Creates a new token after expiration

##### AdminService
- getAllUsers()
  - Lists all registered customers

- getAllTransactions()
  - Shows all financial records

- approveAccount()
  - Approves an account created by a user

- blockUser()
  - Disables a customer account due to suspicious activity

- generateReports()
  - Produces summary reports for admins

### 5.4 Controller Layer
Controllers expose REST endpoints for the frontend or clients.

Examples:
- AuthController
- UserController
- AccountController
- TransactionController
- AdminController

#### Common endpoint functions
- registerUser()
- loginUser()
- getAccountDetails()
- depositMoney()
- withdrawMoney()
- transferMoney()
- getStatement()

#### Explanation
Controllers receive HTTP requests, validate user input, and call the corresponding services.

### 5.5 Security Layer
Security is used to protect APIs and restrict access.

Examples:
- SecurityConfig
- JwtTokenProvider
- CustomUserDetailsService

#### Important responsibilities
- restrict unauthenticated users
- allow login but block protected endpoints
- define user and admin roles
- validate JWT tokens
- encrypt passwords

### 5.6 DTO Layer
DTO stands for Data Transfer Object.

These classes carry data between layers without exposing entity internals.

Examples:
- UserRegistrationDto
- LoginRequestDto
- AccountRequestDto
- TransactionRequestDto

#### Role
DTOs:
- validate incoming request data
- reduce security risks
- prevent exposing database internals

## 6. Typical Banking Workflow

### User Registration
1. User enters name, email, password, phone number
2. System validates the values
3. User is stored in the database
4. User receives a success response

### Account Creation
1. User requests a new account
2. System checks whether account limit is valid
3. A unique account number is generated
4. Account is saved to database

### Deposit
1. User provides account number and deposit amount
2. Service validates positive amount
3. Account balance is increased
4. Transaction history is created

### Withdrawal
1. User requests withdrawal
2. System checks account balance
3. If enough funds exist, balance is decreased
4. Transaction record is saved

### Fund Transfer
1. Sender selects recipient account
2. System verifies sender balance
3. Amount is deducted from sender
4. Amount is added to receiver
5. Transaction logs are written for both parties

### Balance Inquiry
1. User sends account ID or account number
2. Backend fetches current balance
3. Response returns account status and available amount

## 7. Database Tables (Typical)

### users
- id
- full_name
- email
- password
- phone_number
- role
- created_at

### accounts
- id
- account_number
- account_type
- balance
- status
- user_id

### transactions
- id
- transaction_type
- amount
- sender_account_id
- receiver_account_id
- description
- timestamp

### admin
- id
- username
- password
- role

## 8. Common API Endpoints

Below are typical endpoints in a digital banking system:

### Auth APIs
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/logout

### User APIs
- GET /api/users/{id}
- PUT /api/users/{id}
- GET /api/users/profile

### Account APIs
- POST /api/accounts
- GET /api/accounts/{id}
- GET /api/accounts/user/{userId}
- GET /api/accounts/{id}/balance

### Transaction APIs
- POST /api/transactions/deposit
- POST /api/transactions/withdraw
- POST /api/transactions/transfer
- GET /api/transactions/{accountId}

### Admin APIs
- GET /api/admin/users
- GET /api/admin/transactions
- PUT /api/admin/accounts/{id}/approve

## 9. Security Features

A digital banking project should include:
- password encryption using BCrypt
- role-based access control
- JWT authentication
- validation of all incoming requests
- protection against unauthorized access
- locking user accounts after repeated login failures

## 10. Error Handling

Common exceptions include:
- UserNotFoundException
- InsufficientBalanceException
- InvalidAccountException
- DuplicateUserException
- TransactionFailedException

These exceptions are usually handled using:
- @ControllerAdvice
- custom exception classes
- response DTOs with error messages

## 11. Example Function Flow

### Example: depositMoney()
```java
public ResponseEntity<?> depositMoney(Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new AccountNotFoundException("Account not found"));

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new InvalidAmountException("Amount must be greater than zero");
    }

    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
    accountRepository.save(account);

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setType("DEPOSIT");
    transaction.setAmount(amount);
    transaction.setDescription("Cash Deposit");
    transactionRepository.save(transaction);

    return ResponseEntity.ok("Deposit successful");
}
```

#### Explanation
- Checks if account exists
- Validates the amount
- Updates account balance
- Saves the transaction record
- Returns success

### Example: transferMoney()
```java
public ResponseEntity<?> transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
    Account sender = accountRepository.findById(senderId)
        .orElseThrow(() -> new AccountNotFoundException("Sender not found"));
    Account receiver = accountRepository.findById(receiverId)
        .orElseThrow(() -> new AccountNotFoundException("Receiver not found"));

    if (sender.getBalance().compareTo(amount) < 0) {
        throw new InsufficientBalanceException("Insufficient balance");
    }

    sender.setBalance(sender.getBalance().subtract(amount));
    receiver.setBalance(receiver.getBalance().add(amount));

    accountRepository.save(sender);
    accountRepository.save(receiver);

    Transaction transaction = new Transaction();
    transaction.setSenderAccount(sender);
    transaction.setReceiverAccount(receiver);
    transaction.setAmount(amount);
    transaction.setType("TRANSFER");
    transactionRepository.save(transaction);

    return ResponseEntity.ok("Transfer successful");
}
```

#### Explanation
- Verifies both accounts
- Checks if sender has enough balance
- Deducts money from sender
- Adds money to receiver
- Saves transaction record

## 12. Best Practices for This Project

- Use DTOs instead of returning entity objects directly
- Validate inputs on both frontend and backend
- Use service layer for all business logic
- Do not put database logic directly in controllers
- Use custom exception handling
- Log all transactions
- Use secure password hashing
- Keep API responses consistent

## 13. Conclusion

This digital banking system is a practical project that demonstrates:
- Java backend development
- database integration
- REST API creation
- secure user authentication
- financial transaction logic
- full-stack application design

It is a strong example of a realistic Java enterprise application and is suitable for:
- learning backend development
- building banking systems
- preparing for interviews
- understanding system design

## 14. How to Run the Project

### Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm start
```

### Database
Create database and apply schema:
```sql
CREATE DATABASE digital_banking_system;
```

Then update credentials in:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_banking_system
spring.datasource.username=root
spring.datasource.password=your_password
```

## 15. Summary of Main Functions

- registerUser() – create customer
- loginUser() – authorize customer
- createAccount() – open bank account
- deposit() – add money
- withdraw() – remove money
- transfer() – send money to another account
- checkBalance() – view account balance
- getTransactionHistory() – show transaction record
- updateUserProfile() – edit user profile
- generateReports() – admin summary views
- validateToken() – check request authority
- authenticate() – verify credentials

If you want, I can now turn this into a more exact README based on your actual Java files, and I can also document each class and method in your project line by line.

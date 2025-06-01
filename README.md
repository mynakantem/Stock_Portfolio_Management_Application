📊 Stock Portfolio Monitoring Application
A Spring Boot-based simulation project to manage personal stock portfolios — enabling users to track their holdings, fetch live stock prices, monitor gain/loss performance, and generate reports.


🌐 Domain
Finance / Investment / Portfolio Management


🎯 Objectives
•	Track and manage user stock portfolios.
•	Fetch real-time stock prices using external APIs.
•	Calculate per-stock and total portfolio gain/loss.
•	Set alerts for price thresholds or overall loss.
•	Export reports in Excel format.
•	Provide a secure and modular backend system.


🧱 Tech Stack
Language: Java
Framework: Spring Boot, Spring Data JPA
Authentication: Spring Security
Persistence: Spring Data JPA
Database: MySQL
REST Client: RestTemplate
JSON Parsing: Jackson
Reporting: Apache POI (Excel)
Scheduler: Spring Scheduler
Build Tool: Maven
Utilities:	Lombok, Logger, Swagger (springdoc)
Testing: JUnit, Mockito


🧩 Key Modules
•	User Management: Register/login, role-based access
•	Portfolio Management: Create, update, and delete portfolios
•	Holdings Management: Add/edit/delete individual stocks
•	Real-Time Price Fetcher: Fetch live prices via 3rd-party APIs
•	Alerts Module: Track price thresholds or loss percentages
•	Gain/Loss Calculator: Real-time valuation & performance metrics
•	Reporting Module: Daily summaries and export capabilities


🔐 Roles & Access
Admin: Full access to all modules and user controls
User: Personal portfolio and alert management


🗃 Entity Overview
•	User: id, username, email, password, role
•	Portfolio: id, userId, name
•	Holding: id, portfolioId, symbol, quantity, buyPrice
•	StockPrice: StockSymbol, price, LastUpdated
•	Alert: id, thresholdValue, AlertType, Symbol
•	Report: generated via logic, no persistent entity


🔁 REST API Endpoints

🔐 AuthController (/api/auth):
POST /register — Register a new user
POST /login — Authenticate and receive response (no JWT in your setup)
-----
👤 AdminController (/api/admin)
GET / — Get all registered users
GET /user/{id} — Get user details by ID
PUT /{id}/role?email=email@example.com — Update user's role
DELETE /user/{id} — Delete user by ID
-----
📦 PortfolioController (/api/portfolios)
GET / — Get all portfolios of the logged-in user
POST / — Create a new portfolio
GET /{id} — Get specific portfolio by ID
PUT /{id} — Update portfolio name
DELETE /{id} — Delete portfolio
GET /{id}/holdings — Get holdings for a specific portfolio
-----
📈 HoldingController (/api/holdings)
POST / — Add a new holding to a portfolio
GET /{id} — Get a specific holding by ID
PUT /{id} — Update a holding
DELETE /{id} — Delete a holding
-----
🚨 AlertController (/api/alerts)
GET / — View all alerts
POST / — Create a new alert
PUT /{id} — Update an alert by ID
DELETE /{id} — Delete an alert
GET /evaluate — Manually evaluate all alerts based on current prices
-----
📊 GainLossController (/api/gainloss)
POST /calculate/{portfolioId} — Trigger gain/loss calculation for a portfolio
GET /today/{portfolioId} — Get today’s gain/loss for a specific portfolio
GET /today — (Not implemented) Get all gain/loss for today
GET /total/{portfolioId} — Get total gain amount for a portfolio
-----
💹 PriceFetcherController (/api/stock-prices)
GET /{symbol} — Get live or cached stock price for a symbol
GET /{symbol}/refresh — Force refresh stock price for a symbol
GET /cache — Get all cached prices
DELETE /cache — Clear all cached stock prices
GET /status — Check API availability and cache status
-----
📑 ReportController (/api/reports)
GET /export/excel?portfolioId={id} — Export an Excel report for a given portfolio
-----


🧪 Example Workflow
1.	User Registration & Login: A user registers via 'POST /api/auth/register' and logs in via 'POST /api/auth/login'.
2.	Portfolio Creation: The user creates a portfolio using 'POST /api/portfolios' (e.g., "My Tech Stocks").
3.	Add Holdings to Portfolio: The user adds stocks like AAPL, MSFT, or TCS via 'POST /api/holdings', specifying quantity and buy price.
4.	Real-Time Price Fetching: A scheduled job or API call (GET /api/stock-prices/{symbol}) fetches current stock prices from external APIs and updates the cache.
5.	Gain/Loss Calculation:
    - The user (or system) triggers gain/loss calculation via 'POST /api/gainloss/calculate/{portfolioId}'.
    - Results can be viewed using 'GET /api/gainloss/today/{portfolioId}'.
7.	Set and Evaluate Alerts:
    - The user sets price-based or loss-percentage alerts using 'POST /api/alerts'.
    - These are evaluated periodically or via 'GET /api/alerts/evaluate'.
8.	Generate Reports: The user exports Excel reports for a portfolio via 'GET /api/reports/export/excel?portfolioId=1'.
9.	Admin Activities (Optional): Admin can view all users (GET /api/admin), update roles (PUT /api/admin/{id}/role), or delete users.


🖼 ER Diagram
 ![image](https://github.com/user-attachments/assets/597cf6f0-0cd3-4038-a5a9-4c6cf2a59052)


🧭 Class Diagram
![image](https://github.com/user-attachments/assets/8d829ad4-0d92-4fe0-98ba-b69d5f2d269f)


⚙ Sample Configuration (application.properties)
spring.application.name=Stock_Portfolio_Management_Application
spring.datasource.url=jdbc:mysql://10.9.183.139:3306/springboot_team_db
spring.datasource.username=team_user
spring.datasource.password=localpass123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
server.port=8080
#Show basic info for Spring and libraries
logging.level.root=INFO
# Show detailed debug logs only for our own app code
logging.level.com.stockapp=DEBUG
# Formats the console output
logging.pattern.console=%d{HH:mm:ss} - %logger{36} - %msg%n
# API key to fetch realtime stock prices from rapidApi
rapidapi.key=c821f362f7msh33eebb96bb36bd6p104f26jsn8dd575770bab
rapidapi.host=indian-stock-market-data-nse-bse-bcd-mcx-cds-nfo.p.rapidapi.com
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration


📁 Suggested Project Structure
com.stockportfolio
├── controller
├── dto
├── entity
├── repository
├── service
├── config
├── exception
├── scheduler (optional)  
└── StockPortfolioApplication.java


🧪 Testing
•	Unit testing with JUnit and Mockito.
•	Integration testing for REST APIs.
•	Mock price fetchers for reliable tests.


✅ Optional Enhancements
Email notifications: JavaMailSender
Scheduled price fetching: @Scheduled
PDF Reports: iText


🗂 Suggested Sprint Week Deliverables
1. User Management                                
2. Portfolio Management
3. Real-Time Fetching
4. Alerting Module
5. Gain/Loss Calculator
6. Reporting Module
7. Testing, documentation, Swagger UI


▶️ How to Use the Project
🛠 Prerequisites:
•	Java 17+
•	Maven
•	MySQL Database
•	Postman or Swagger UI


🚀 Steps to Run:
       - git clone https://github.com/mynakantem/Stock_Portfolio_Management_Application.git
       - cd Stock_Portfolio_Management_Application
1.	Create a MySQL DB stock_portfolio
2.	Configure application.properties with your DB credentials
3.	Build and run: 
       - ./mvnw clean install
  	   - ./mvnw spring-boot:run
5.	Visit Swagger UI:
       - http://localhost:8080/swagger-ui/index.html
6.	Register/Login and test endpoints using token authorization.


👥 Authors
•	Myna Kantem – Team Lead, Reporting - Excel Export, Database Design, API Testing and Global Exception Handling
•	Bhakyalakshmi – User Management, API Testing and configuration
•	Monish Padala – Portfolio and Holdings
•	Sreayas Kannemoni – Real-Time Price Fetching and Testing
•	Bhaskar – Alerting Module and Testing
•	Pareekshith – Gain/Loss Calculator


🤝 Contributors
Thanks to everyone who contributed through feedback, testing, or documentation.



















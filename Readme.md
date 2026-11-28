# AutomationExercise UI + API Test Automation Framework

Hybrid test automation framework built with **Java 25**, **Selenium WebDriver**, **RestAssured**, and **TestNG** to test core user, product, and cart workflows on [AutomationExercise](https://automationexercise.com).

## Why This Framework Structure?
Instead of testing everything slowly through the browser UI, this project separates fast API checks from UI journeys and combines both in `HybridE2ETest`:
- **API Layer (`api/client`, `api/endpoints`, `api/models`, `api/services`):** Handles request specs, endpoint paths, Jackson POJOs, service calls, and JSON Schema validation (`products-list-schema.json`).
- **UI Layer (`base`, `constants`, `pages`, `utils`):** Uses Page Object Model (`BasePage`, `HomePage`, `LoginPage`, `ProductsPage`, `CartPage`) with `ThreadLocal<WebDriver>` (`DriverFactory`) and `listeners` for reporting.
- **Hybrid Flow (`tests/hybrid/HybridE2ETest`):** Creates a fresh user via API (`Datafaker`), verifies login and cart flow on the UI, and deletes the test account via API during teardown so no stale test data remains.

## Tech Stack
- **Language & Build:** Java 25, Apache Maven, Surefire Plugin (`3.5.2`)
- **UI:** Selenium `4.27.0` (Chrome, Firefox, Edge with headless mode support)
- **API:** RestAssured `5.5.0`, Jackson `2.18.2`, JSON Schema Validator
- **Test Runner & Data:** TestNG `7.10.2`, Datafaker `2.4.2`, Apache POI `5.3.0`
- **Reporting & Logs:** ExtentReports `5.1.2` (with failure screenshots), Log4j2 `2.24.3`
- **CI/CD:** GitHub Actions (`.github/workflows/ci.yml`) and Jenkins (`Jenkinsfile`)

## Project Structure
```text
automation-ui-api-framework/
├── .github/
│   └── workflows/
│       └── ci.yml                           # GitHub Actions CI workflow
├── src/
│   ├── main/java/com/automationexercise/
│   │   ├── api/
│   │   │   ├── client/                      # RestAssured request/response wrappers
│   │   │   ├── endpoints/                   # API route constants
│   │   │   ├── models/                      # Request/Response POJOs (Jackson)
│   │   │   └── services/                    # Service Object Model classes
│   │   ├── base/                            # DriverFactory (ThreadLocal WebDriver)
│   │   ├── constants/                       # Framework & test constants
│   │   ├── listeners/                       # TestNG & ExtentReports listeners
│   │   ├── pages/                           # Page Objects & vignette ad handling
│   │   └── utils/                           # ConfigReader, test data & helpers
│   └── test/
│       ├── java/com/automationexercise/
│       │   ├── base/                        # BaseTest setup & teardown
│       │   └── tests/
│       │       ├── api/                     # API & JSON schema tests
│       │       ├── hybrid/                  # End-to-end API + UI tests
│       │       └── ui/                      # UI functional tests
│       └── resources/
│           ├── config/
│           │   └── config.properties        # Environment & browser settings
│           ├── schemas/
│           │   └── products-list-schema.json
│           ├── testdata/                    # External test data files
│           ├── testsuites/
│           │   ├── api-suite.xml            # Fast API suite
│           │   ├── Regression.xml           # Full suite
│           │   └── smoke.xml                # Smoke suite
│           └── log4j2.xml                   # Log4j2 configuration
├── Jenkinsfile                              # Declarative Jenkins CI pipeline
└── pom.xml                                  # Maven dependencies & Surefire config


How to Run
Prerequisites: JDK 25, Maven 3.9+, and Chrome/Firefox/Edge installed.

Bash: 
Run API suite only
 mvn clean test -DsuiteXmlFile=api-suite.xml

Run Smoke suite in headless mode
 mvn clean test -DsuiteXmlFile=smoke.xml -Dheadless=true

Run Smoke suite with visible browser
 mvn clean test -DsuiteXmlFile=smoke.xml -Dheadless=false

Run full Regression suite
 mvn clean test -DsuiteXmlFile=Regression.xml -Dheadless=true
 
 Notes & Real-World Handling
Google Vignette Ads: automationexercise.com frequently triggers #google_vignette interstitial URLs on navigation clicks. BasePage detects vignette URLs, refreshes/retries cleanly, and avoids false element-click failures.

Public Demo Server Stability: Because automationexercise.com is a shared demo environment, occasional HTTP 503 responses can occur under rapid consecutive API calls.

Reports
TestNG / Surefire XML & Logs: target/surefire-reports/

Extent HTML Report: Generated automatically after suite completion with embedded failure screenshots.
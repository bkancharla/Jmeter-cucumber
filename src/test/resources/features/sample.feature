Feature: System performs with acceptable level of anomalies
@collection @exec
Scenario: Testing Collection Service
    Given configured GET http sampler for "XYZ Page" page
    And add sampler request to TestPlan
    And add HeaderManager to TestPlan
    Given configured GET http sampler for "Navigation Page" page
    And add sampler request to TestPlan
    And set the ThreadCount to 1 and rampUp to 1 seconds
    And repeat the script in 2 loops
    Then run this script

  Scenario: Create Vs Account and sign out for multiple users

    Given add HeaderManager to TestPlan
    And add CookieManager to TestPlan
    When configured POST http sampler for "Create Account" api
    And add sampler request to TestPlan
    And read the csv data file for "Create Account" api
    And add CsvDataset to the TestPlan
    When configured POST http sampler for "Sign Out" api
    And add sampler request to TestPlan
    And set the ThreadCount to 4 and rampUp to 10 seconds
    And repeat the script in 1 loops
    Then run this script


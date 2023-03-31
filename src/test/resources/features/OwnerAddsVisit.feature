Feature: Fetch Visit from Owner

Background:
  Given the Owner has the following
    | firstName  | lastName   | address          | city        | telephone  | id |
    | Mickey     | Mouse      | 123 Main Street  | DisneyLand  | 5141234567 | 11 |

  Scenario: Add a visit for one Pet (Successful)
    Given the Owner has the following Pet
      | name     | birthDate     | type    | pet_id |
      | Pluto    | 1930-09-05    | dog     | 14     |
    When the Owner completes the Add Visit Form with the following Visit
    | visitDate    | description  | pet_id |
    | 2022-11-10   | check-up     | 14     |
    Then the Owner redirects to a new page
    And the visit with date "2022-11-10" and description "check-up" and pet_id 14 is returned

  Scenario: Fetching visit with no given date
    Given the Owner has the following Pet
      | name     | birthDate     | type    | pet_id |
      | Pluto    | 1930-09-05    | dog     | 15     |
    When the Owner completes the Add Visit Form with the following Visit
      | visitDate  | description  | pet_id  |
      | [blank]    | check-up     | 15      |
    Then the Owner redirects to a new page
    And the visit with date " " and description "check-up" and pet_id 15 is returned

  Scenario: Fetching visit without a description
    Given the Owner has the following Pet
      | name     | birthDate     | type    | pet_id |
      | Pluto    | 1930-09-05    | dog     | 16     |
    When the Owner completes the Add Visit Form with the following Visit
    | visitDate    | description | pet_id |
    | 2022-11-10   | [blank]     | 16     |
    Then the Owner returns to the same page

  Scenario: Fetching visit with invalid pet (Invalid)
    When the Owner fetches the Add Visit Form for Pet with an ID of 100
    Then the Owner redirects to an error page

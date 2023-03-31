Feature: As a Vet, I would like to add a specialty so that I can attend more visits.

Scenario: Adding Specialty successfully
  Given the following Vet exists
    | firstName  | lastName     |
    | Jimbo      | McNugget     |
  When Vet adds a Specialty
    | name      | id |
    | Urology   | 1  |
    Then Vet shall have 1 Specialties

Scenario: Adding Specialty with invalid name
  Given the following Vet exists
    | firstName  | lastName     |
    | Jimbo      | McNugget     |
  When Vet adds a Specialty
    | name      | id  |
    | [blank]   | -1  |
  Then Vet shall have 0 Specialties

        

# card-management

Service that provides cards management. It stores and exposes information about persons (credit card applicants) and card files.
It enables these actions for person (card applicant): create person, bulk person create, retrieve person, delete person and card file create.
Other needed actions for card management (such as person modify, retrieve card file etc.) can be developed in future.

#### Internal storage:
   * H2
   
#### Information exposure:
  * REST API. See [openapi definition](src/main/resources/openapi.yml)
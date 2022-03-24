1. The First Service uses Spring Boot & REST

The Service manages passports. Domain model is Passport.
DB: PostgreSQL

Supports the following mappings:

- /save
- /update?id=*
- /delete?id=*
- /find
- /find?serial=*
- /unavailable
- /find-replaceable

2. The Second Service uses Spring Boot and calls the methods of the 1st Service by RestTemplate.
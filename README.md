1. Implement REST service based on spring-boot framework

   ϖ	Seperate web, service and repository levels

   ϖ	Create application for person data operations

       ¬	Find person by name
    
       ¬	Find person by birth date
    
       ¬	Find all saved persons
    
       ¬	Save person
    
       ¬	Update person data
    
       ¬	Delete person

   ϖ	Service output Person object with following properties

       ¬	Personal id
    
       ¬	First name, last name
    
       ¬	Gender: male, female
    
       ¬	Date of birth
    
       ¬	Phone number
    
       ¬	E-mail

   ϖ	Cover input with validations

   ϖ	Throw exepctions on validations

   ϖ	For data storage need to use database, e.g. H2

   ϖ	Dependency manger: maven

   ϖ	Log some service activities to log file and in database

   ϖ	Implement unit tests

*Optional
2. Implement angular client to use the implemented service

   ϖ	Input form with search parameters

   ϖ	Search button

   ϖ	Result table


FYI
-
Used Java version - 17

Running app locally prefilled H2 DB table is accessible on http://localhost:8080/h2

and endpoints- http://localhost:8080/swagger-ui/index.html#
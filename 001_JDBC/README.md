# JDBC intro
Database initialization files [her](data_init).  
You also need to change the values of *URl*, *LOGIN*, and *PASSWORD* 
[her](src/main/java/com/jdbc/repository/EmployeeRepository.java) to connect to your database.
#
In this case, I decided to create a simple QueryBuilder. This is a static nested class and is used to
dynamically build a database query, which can make the code more elastic in my opinion.  
If you find any bad things, know where to make corrections, or think it's inappropriate, please contact me, 
it will be interesting to discuss it.
## Tasks:
1. Create database:
- Table 1 contains the names and phone numbers of the company's employees.
- Table 2 contains statements of salaries and positions of employees.
- Table 3 contains information on marital status, date of birth, and place of residence.
2. Using JDBC, do sampling using JOIN's for these jobs:
- Obtain employee contact information (phone numbers, residence).
- Get information about all single employees' date of birth and their numbers.
- Get information about all managers of the company: date of birth and phone number.

**Additional task:** Add CRUD functionality to your API.

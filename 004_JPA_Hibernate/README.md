# JPA and Hibernate
Database initialization files [here](../data_init/Hibernate).  
You should change a **values** in the [properties](src/main/resources/META-INF/persistence.xml)
**named** *javax.persistence.jdbc.url*, *javax.persistence.jdbc.user*, and *javax.persistence.jdbc.password* 
that you use to access to your database.
## Tasks:
1. Create a new database and a table in it.
2. Create a project and connect **JPA** and **Hibernate** libraries to it.
3. Create a file with *persistence.xml* settings in the **META-INF** folder.
4. Create a class with get and set methods as an entity to our table.
5. Use jpa to make CRUD in the *Helper* class.
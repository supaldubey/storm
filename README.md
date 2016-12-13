A small and FAST Java ORM solution for Android SQLite database. Currently being used across projects from Enterprise to Open Source

Storm was created with intention to be easy to use ORM solution for Android platform. While working for a corporate client we realized that the available tools either expect 
too much to be done manually, or are super-complicated or just want entities to extend their own.

The target was to have something which is easy to use, super-fast, convineant and does not asks you to write all queries, updates, creates or even to extend their models.

# Getting Started

Storm needs to know the basic database details and the tables it needs to manage. 

Three basic steps:

### Step One (Define the Table)

Tables can be defined easily with marking your entities with annotations similar to most ORMs
```java
@Table(name = "DEMO_ENTITY")
class Entity {
   @PrimaryKey
   @Column(name="ID", type = FieldType.INTEGER)
   private int id;
 }
```
Once you have tables defined as above, the next step is to bind them to a database. 

### Step Two (Define the Database)
Database also have their annotation which can be applied, there can be multiple databases defined. 

```java
@Database(name="MY_DB", tables = {Entity.class, AnotherEntity.class}, version = 2)
class Database {}
```

### Step Three (Start Using)

With database ready for us, we may start using it as below:

Retrival
```java
StormService service = new BaseService(getContext(), Database.class);
List<Entity> savedEntities  = service.findAll(Entity.class);
```

Save
```java
StormService service = new BaseService(getContext(), Database.class);
Entity entity = new Entity (); 
// Set all values
service.save(entity);

Assert.assertTrue(entity.id > 0 );

```

Not only storm would save the entity for you, it would also auto increment the ID (You may override this behaviour with @PrimaryKey annotation), it would 
also update the new ID value generated to your entity. 

### That's it
No other configurations, no create tables, nothing required. We donot want an entry in AndroidManifest, or any String to be declared in the XML.

We would read the entities defined as per the @Database annotation and make sure we make everything ready.

View demo application: [link to Google Play Store!](https://play.google.com/store/apps/details?id=in.cubestack.material.androidmaterial&hl=en),
[source code] (https://github.com/supaldubey/gre) and [Entity Mappings] (https://github.com/supaldubey/gre/tree/master/app/src/main/java/in/cubestack/material/androidmaterial/model) 

# Features
* ******* Absolutely no lines of SQL Written for the application *******


* Manage Relations
* Save entire object graph in one transaction
* Fetch entire object graph with conditions
* Limit and Order support
* Supports cascading
* Insert
* Bulk Insert (Transaction)
* Auto create tables on Create 
* Auto update tables on update of application versions
* Version Audit history
* Find relations
* Utilizes Where / Restrictions / Limits / Orders constraints (Without a line of SQL code)
* All list options are Transnational for performance
* Handy callback options
* Sync and Async calling suppport
* Performance (Almost similar to Raw SQL)
* No Constraints, your entities need not extend any classes
* Non Invasive Framework.
* Less that 100 KB Footprint, no additional dependency




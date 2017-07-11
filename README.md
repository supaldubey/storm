A small and FAST Java ORM solution for Android SQLite database. Currently being used across projects from Enterprise to Open Source

Storm was created with intention to be easy to use ORM solution for Android platform. While working for a corporate client we realised that the available tools either expect too much to be done manually, or are super-complicated or just want entities to extend their own.

The target was to have something which is easy to use, super-fast,convenient and does not asks you to write all queries, updates, creates or even to extend their models.



### Cubestack Apps using Storm
 * [Coins - Shared & personal Expense Manager with Offline and Sync Capabilities] (https://play.google.com/store/apps/details?id=in.cubestack.coins)
 * [High Frequency Wordlist Aggregator] (https://play.google.com/store/apps/details?id=in.cubestack.material.androidmaterial)
 * [Pixr: Unofficial Unsplash Application - Royalty Free High resolution Photos] (https://play.google.com/store/apps/details?id=in.cubestack.pixr)
 
 [More details here] (http://cubestacklabs.com/category/storm/)


# Adding to Project
To add storm to your Android project, please use below for Gradle in your project specific Gradle file. This would add Cubestack bitray Mave repository to your project
```gradle
maven { url "http://dl.bintray.com/cubestack/maven" }
```
Once Maven repository is added, you may import Storm to your project with current version (1.0g) or any other

```gradle
dependencies {
    compile 'in.cubestack.android.lib:storm:1.0g'
}
```

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

### Find and Fetch

There is a handy Criteria API to help you fetch the data from your tables:

Creating the restrictions:

```
  restrictions = StormRestrictions.restrictionsFor(TestEntity.class);
  Restriction idRes = restrictions.equals("id", 7);
```
Multiple Restrictions:

``` 
  Restriction name = restrictions.likeIgnoreCase("name", "Bruce Wayne");
  Restriction combo = restrictions.and(idRes, name);
```

Using restrictions:

```
List<TestEntity> responseList = stormService.find(TestEntity.class, combo);
```

There is also an Async API with callbacks to help avoid writing the AsyncTasks

Example: [Word List Project] (https://github.com/supaldubey/gre/blob/master/app/src/main/java/in/cubestack/material/androidmaterial/fragment/MainFragment.java)

``` 
 //              WordList is mapped Table, Restirctions can be paged, order on multiple Properties and callback
stormService.find(WordList.class, restriction.page(pageNo), Order.orderFor(WordList.class, new String[]{"word"}, SortOrder.ASC),
         new StormCallBack<WordList>() {
             @Override
             public void onResults(final List<WordList> results) {
                // Do something with results on Main UI Thread
             }

             @Override
             public void onError(Throwable throwable) {
               // Handle Error, on UI Thread
             }
         });
```

Not only storm would save the entity for you, it would also auto increment the ID (You may override this behaviour with @PrimaryKey annotation), it would 
also update the new ID value generated to your entity. 

### That's it
No other configurations, no create tables, nothing required. We donot want an entry in AndroidManifest, or any String to be declared in the XML.

We would read the entities defined as per the @Database annotation and make sure we make everything ready.

View demo application: [link to Google Play Store!](https://play.google.com/store/apps/details?id=in.cubestack.material.androidmaterial&hl=en),
[source code] (https://github.com/supaldubey/gre) and [Entity Mappings] (https://github.com/supaldubey/gre/tree/master/app/src/main/java/in/cubestack/material/androidmaterial/model) 

# Features

* Manage Relations (one to One and One to Many Supported)
* Save entire object graph in one transaction (In case of relational database and collections)
* Fetch entire object graph with conditions 
* Limit and Order support (Paging)
* Supports cascading (Delete Orphans and Save Child relations)
* Bulk Insert (Works in Transaction)
* Auto create tables on Create 
* Auto update tables on update of application versions
* Version Audit history via Annotations
* Utilizes Where / Restrictions / Limits / Orders constraints (Without a line of SQL code)
* All list options are Transnational for performance
* Handy callback options
* Sync and Async calling suppport
* Performance (Almost similar to Raw SQL)
* No Constraints, your entities need not extend any classes
* Non Invasive Framework.
* Less that 100 KB Footprint, no additional dependency




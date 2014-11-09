IEDB
====

**International Enterteinment Database**

All enterteinment reference in just one place.

WebApp
------

IEDB is developed with the [Play][1] framework and [activator][2]
development tool, in [Java][3] and [Scala][4].

In order to run the server locally, just type 

```
$ activator run
```

and access `localhost:9000`.

IEDB source code is freely avaiable on [GitHub][5], under 
Apache 2.0 license. In order to see the complete application 
running, please visit our [official website][6] at [Heroku][7].

Database
--------

IEDB's database is implemented with [PostgreSQL][8]. All avaiable
SQL files are stored in `/app/models/sql/`.

To recreate the database, run them on PostgreSQL in the 
following order:

1. IEDB.sql
2. IEDB\_procedures.sql
3. IEDB\_views.sql

To populate it with a sample database, just run `IEDB_data.sql`.

[1]: https://www.playframework.com/
[2]: https://typesafe.com/activator
[3]: https://www.oracle.com/java/index.html
[4]: http://www.scala-lang.org/
[5]: https://github.com/
[6]: http://iedb.herokuapp.com
[7]: http://www.heroku.com/
[8]: http://www.postgresql.org/

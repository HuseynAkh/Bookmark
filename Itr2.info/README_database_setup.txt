We are running a database on an online server (hosted off Hostinger)
Here is what you need:

Connection Attributes:
String url = "jdbc:mariadb://sql958.main-hosting.eu/u880453721_Bookmark";
String user = "u880453721_user";
String password = "@BookmarkDbcp01";

Supported db driver libraries:
mvn library: mariadb-java-client (3.0.7)

ORR you can use any msql Connector/J but set:
enabledTLSProtocols to TLSv1.2

Jar will be working with the database in itr 3

If you want to set up the database locally see "SQL_Script.txt"
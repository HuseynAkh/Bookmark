We are running a database on an online server (hosted off Hostinger)
Here is what you need:

***** First You need the database driver inorder fo the program to run *****
Supported db driver libraries:
mvn library: mariadb-java-client (3.0.7)

ORR you can use any msql Connector/J but set:
enabledTLSProtocols to TLSv1.2

***** If you want to use a local database *****
SEE "SQL_Script.txt" SCHEMA & TABLES

ALSO you will need to change the connection strings found in "DatabaseConnection.java"
under controller -> database packages:

private String url = "jdbc:mariadb://sql958.main-hosting.eu/u880453721_Bookmark"; //Change for local
private String user = "u880453721_user"; //Change for local
private String password = "@BookmarkDbcp01"; //Change for local

To your own local database

Jar will be working with the database in itr 3
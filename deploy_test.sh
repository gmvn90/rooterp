rm -rf $BASE_TOMCAT_SERVER_WEBAPPS/mill3*
mvn package
cp $BASE_MILL_PROJECT/target/mill3.war $BASE_TOMCAT_SERVER/webapps
$BASE_TOMCAT_SERVER/bin/startup.sh

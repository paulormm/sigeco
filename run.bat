call mvn clean -Ddt
call D:\utils\tomcat\bin\shutdown
PING 1.1.1.1 -n 1 -w 5000 >NUL
call D:\utils\tomcat\bin\catalina jpda start
PING 1.1.1.1 -n 1 -w 9000 >NUL
call mvn clean install -Ddt
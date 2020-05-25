This software assigns students to give schools.
Files are comma separated (CSV) files.


To build this software, please install maven and execute mvn install
To run it please execute:
java -jar studentassigner-1.0-SNAPSHOT.jar <path of schools.csv> <path of applications.csv>

example:
java -jar studentassigner-1.0-SNAPSHOT.jar schools-sample.csv applications-sample.csv
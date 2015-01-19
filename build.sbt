name := "AsyncServlet"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += "Spy" at "http://files.couchbase.com/maven2/"

libraryDependencies ++= Seq(
  "javax.servlet"       %  "javax.servlet-api" % "3.1.0",
  "com.typesafe"        %  "config"            % "1.2.1",
  "org.scalikejdbc"     %% "scalikejdbc-async" % "0.5.+",
  "com.github.mauricio" %% "mysql-async"       % "0.2.+",
  "com.bionicspirit"    %% "shade"             % "1.6.+"
)


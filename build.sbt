name := "AsyncServlet"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "javax.servlet"       %  "javax.servlet-api" % "3.1.0",
  "org.scalikejdbc"     %% "scalikejdbc-async" % "0.5.+",
  "com.github.mauricio" %% "mysql-async"       % "0.2.+"
)


name := "AsyncServlet"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += "Spy" at "http://files.couchbase.com/maven2/"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

libraryDependencies ++= Seq(
  "javax.servlet"       %  "javax.servlet-api" % "3.1.0",
  "com.typesafe"        %  "config"            % "1.2.1",
  "org.scalikejdbc"     %% "scalikejdbc-async" % "0.5.+",
  "com.github.mauricio" %% "mysql-async"       % "0.2.+",
  "com.bionicspirit"    %% "shade"             % "1.6.+",
  "commons-codec"       %  "commons-codec"     % "1.4",
  "org.json4s"          %% "json4s-jackson"    % "3.2.11",
  "org.msgpack"         %% "msgpack-scala"     % "0.6.11"
)


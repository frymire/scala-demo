# scala-demo
Proof that I can code in Scala.

### Build with Maven
To build with Maven, set the JAVA_HOME environment variable to a Java 1.8 JDK (e.g. `C:\Program Files\Java\jdk1.8.0_333`). Scala will not work with a later version of the JDK, even while referencing 1.8 functionality. You will see an error like this...

`[ERROR] error: scala.reflect.internal.MissingRequirementError: object java.lang.Object in compiler mirror not found.`

Alternatively, you can temporarily set JAVA_HOME from the command prompt with

`> set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_333`

or in PowerShell with

`> $env:JAVA_HOME = 'C:\Program Files\Java\jdk1.8.0_333'`

### Import into IntelliJ

If you import as a Maven project and try to build while it is referencing a JDK version later than 1.8, you will likely see the warning

`scala: skipping Scala files without a Scala SDK in module(s) ScalaDemo`

In Project Settings | Project | SDK, be sure to set a Java 1.8 JDK as described above for Maven builds.

### Import into Eclipse
Import into Eclipse as an Existing Maven Project, then

1) Project Properties -> Configure -> Add Scala Nature<br>
2) Right-click src/main/scala, then Build Path -> Use as Source Folder.<br>

(After much fuss with m2e and scala-maven-plugin, there doesn't seem to be a good way to automate these steps.)

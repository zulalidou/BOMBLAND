@echo off
echo Running JavaFX Application...
cd /d "%~dp0"
.\mvnw clean compile exec:java -Dexec.mainClass="com.example.bombland.Main"
pause
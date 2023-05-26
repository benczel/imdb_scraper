# Requirements

    scala 2.13.10
    sbt 1.8.2

# Project structure
Imdb folder itself contains all project related files that are created for resolving the hometest.
The folder structure is the following:

- project/
    
        project specific configuration 

- src/
  
      projects' source code and test cases
        
- target/


      compiled classes

- build.sbt

        this file contains all dependencies references which are in the project

# Application usage

- clone the github repository

- in terminal windows, navigate to the folder where you cloned the repo in the previous step

- execute the following command:


    sbt run

- if the running is success the out.csv will be under the imdb folder


# SBT commands

After cloning the repository, you have to go the root folder where the build.sbt file is placed. 
You can manipulate the project via SBT and its commands.

- execution 
  
After compilation, it executes the main entry point of the application (ImdbApplication).

        sbt run

- test running

After compilation, it executes the test cases from the test folder.

        sbt test

- jar creation

It compiles the projects and creates a fat jar file which contains the project files and these dependencies. 
  
        sbt assembly  

- doc generation

Generates the scala documentation from doc comments

      sbt doc


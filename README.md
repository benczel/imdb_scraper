# The Objective

Your assignment is to create an application that scrapes data from [IMDB](https://www.imdb.com/chart/top/) and adjusts IMDB ratings based on some rules. You don’t have to extract the whole list, please concentrate your attention on the TOP 20 movies only.

# The Tasks
- Three functions are required:
    - Scraper (1) - see below for more details
    
    Rating Adjustment
    
    - Oscar Calculator (2) - see below for more details
    - Review Penalizer (3) - see below for more details
- Unit Tests for all the three functions
- Write out the TOP 20 movies in a sorted (descending) way including both the original and the adjusted new ratings to a file (JSON, CSV, txt, etc.).
- Provide detailed instructions on how to run your assignment in a separate markdown file.

# Scraper

Scrape the following properties for each movie from the [IMDB TOP 250](https://www.imdb.com/chart/top/) list. It is part of the exercise to design the data structure for it: 

- Rating
- Number of ratings
- Number of Oscars
- Title of the movie


# Review Penalizer:

Ratings are good because they give us an impression of how many people think a movie is good or bad. However, it does matter how many people voted. The goal of this exercise is to penalize those movies where the number of ratings is low. 

Find the movie with the maximum number of ratings (remember, out of the TOP 20 only). This is going to be the benchmark. Compare every movie’s number of ratings to this and penalize each of them based on the following rule: Every 100k deviation from the maximum translates to a point deduction of 0.1. 

**For example**, suppose that the movie with the highest number of ratings has 2.456.123 ratings. This means that for a given movie with 1.258.369 ratings and an IMDB score of 9.4, the amount of the deduction is 1.1 and therefore the adjusted rating is 8.3.


# Oscar Calculator

The Oscars should mean something, shouldn’t they? Here are the rewards for them:

- 1 or 2 oscars → 0.3 point
- 3 or 5 oscars → 0.5 point
- 6 to 10 oscars → 1 point
- 10+ oscars → 1.5 point

**For example,** if a movie is awarded 4 Oscar titles and the original IMDB rating is 7.5, the adjusted value will increase to 8 points.

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

- if the running is success the top20Movies.csv will be under the imdb folder


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


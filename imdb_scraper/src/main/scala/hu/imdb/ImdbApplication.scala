package hu.imdb

import com.github.tototoshi.csv.CSVWriter
import hu.imdb.functions.{OscarCalculator, ReviewPenalizer}
import hu.imdb.imdb.Imdb
import hu.imdb.model.{FinalDataset, ImdbMovie, MovieId, Oscars, Rating, Title}

import java.io.File

object ImdbApplication {

  def main(args:Array[String]):Unit = {

    //select top movies from the IMDB Top chart if is not reachable the program will stop
    val imdbTopMovies = Imdb.getTop250(20) match {
      case Left(movies) => movies
      case Right(_) => sys.exit(1)
    }

    val imdbTopMoviesDetails = imdbTopMovies.map(element =>
      Imdb.getMovieDetails(element.url) match {
        case Left(movie) => movie
        case Right(_) => sys.exit(1)
      }
    )

    //create the required dataset
    val listOfFinalDatasets = imdbTopMovies.map(element =>
      FinalDataset(
        title = element.title,
        numberOfOscars = imdbTopMoviesDetails.find(item => item.title == element.title)
          .getOrElse(ImdbMovie(
            MovieId(""),
            Title(""),
            Oscars(0)
          )).oscars,
        numberOfRatings = element.numberOfRatings,
        rating = element.rating,
        newRating = Rating()
      )
    )

    /***
     * TODO refactor this part because it not consistent
     */
    //create ReviewPenalizer object
    val reviewPenalizer = ReviewPenalizer(listOfFinalDatasets)
    val reviewedMovies = reviewPenalizer.penalize

    //create OscarCalculator object
    val oscarCalculator = OscarCalculator(10)
    val finalDataset = reviewedMovies.map(movie =>
      movie.copy(newRating = Rating(oscarCalculator.calculate(movie)))
    )

    /***
     * TODO create a writer class for this part of the code
     */
    //writing top20Movies.csv file
    val file = new File("top20Movies.csv")
    val writer = CSVWriter.open(file)

    val writeableFormat = finalDataset.sortBy(_.newRating.value)(Ordering[Double].reverse)
      .map(movie => List(
              movie.title.value,
              movie.numberOfOscars.value,
              movie.numberOfRatings.value,
              movie.rating.value,
              movie.newRating.value
            )
          )

    writer.writeAll(writeableFormat)

    writer.close()
  }
}

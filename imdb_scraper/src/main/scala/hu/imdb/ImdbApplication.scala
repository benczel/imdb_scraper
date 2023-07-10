package hu.imdb

import hu.imdb.functions.{OscarCalculator, Penalizer}
import hu.imdb.imdb.Imdb
import hu.imdb.model.{FinalMovie, ImdbMovie, MovieId, Oscars, Rating, Title}

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
      FinalMovie(
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

    //create ReviewPenalizer object
    val penalizer = Penalizer(listOfFinalDatasets)
    val penalizedMovies = penalizer.calculateAndSetNewRating

    //create OscarCalculator object
    val oscarCalculator = OscarCalculator(10)
    val finalDataset = penalizedMovies.map(movie =>
      movie.copy(newRating = Rating(oscarCalculator.calculateOscarValue(movie)))
    )


    val writeableFormat = finalDataset.sortBy(_.newRating.value)(Ordering[Double].reverse)
      .map(movie =>
                FinalMovie.unapply(movie).get.productIterator.toList
          )

    Writer().write("top20Movies.csv", writeableFormat)
  }
}

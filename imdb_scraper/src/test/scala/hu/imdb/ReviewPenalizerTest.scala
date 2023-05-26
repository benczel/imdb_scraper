package hu.imdb

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import hu.imdb.functions.ReviewPenalizer
import hu.imdb.model.{FinalDataset, NumberOfRatings, Oscars, Rating, Title}
class ReviewPenalizerTest
  extends AnyFunSuite
  with BeforeAndAfter{


  private var movies:List[FinalDataset] = _
  private var reviewPenalizer:ReviewPenalizer = _

  before{

    movies = List(
      FinalDataset(Title("The Shawshank Redemption"),Oscars(7),  NumberOfRatings(2700000) ,Rating(9.3) ),
      FinalDataset(Title("The Godfather"), Oscars(3), NumberOfRatings(1900000), Rating(9.2)),
      FinalDataset(Title("The Dark Knight"), Oscars(2), NumberOfRatings(2700000), Rating(9.0)),
      FinalDataset(Title("The Godfather Part II"), Oscars(6), NumberOfRatings(1300000), Rating(9)),
      FinalDataset(Title("12 Angry Men"), Oscars(3), NumberOfRatings(803000), Rating(9))
    )
    reviewPenalizer = ReviewPenalizer(movies)
  }

  test("The Shawshank Redemption's new rating is the same as the old") {
    val sampleMovieTitle = Title("The Shawshank Redemption")
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title == sampleMovieTitle).get
    assert(movie.rating == movie.newRating)
  }

  test("12 Angry Men's new rating is smaller than the old one") {
    val sampleMovieTitle = Title("12 Angry Men")
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title == sampleMovieTitle).get
    assert(movie.newRating.value < movie.rating.value )
  }

  test("The Godfather's new rating is 8.4"){
    val sampleMovieTitle = Title("The Godfather")
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title equals sampleMovieTitle).get
    assert(movie.newRating.value == 8.4)
  }
}

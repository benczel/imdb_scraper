package hu.imdb

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import hu.imdb.functions.ReviewPenalizer
import hu.imdb.model.FinalDataset
class ReviewPenalizerTest
  extends AnyFunSuite
  with BeforeAndAfter{


  private var movies:List[FinalDataset] = _
  private var reviewPenalizer:ReviewPenalizer = _

  before{

    movies = List(
      FinalDataset("The Shawshank Redemption",7, 2700000,9.3),
      FinalDataset("The Godfather", 3, 1900000, 9.2),
      FinalDataset("The Dark Knight",2, 2700000, 9.0 ),
      FinalDataset("The Godfather Part II",6, 1300000, 9),
      FinalDataset("12 Angry Men", 3, 803000, 9)
    )
    reviewPenalizer = ReviewPenalizer(movies)
  }

  test("The Shawshank Redemption's new rating is the same as the old") {
    val sampleMovie = "The Shawshank Redemption"
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title equals sampleMovie).get
    assert(movie.rating == movie.newRating)
  }

  test("12 Angry Men's new rating is smaller than the old one") {
    val sampleMovie = "12 Angry Men"
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title equals sampleMovie).get
    assert(movie.newRating < movie.rating )
  }

  test("The Godfather's new rating is 8.4"){
    val sampleMovie = "The Godfather"
    val movies = reviewPenalizer.penalize
    val movie = movies.find(_.title equals sampleMovie).get
    assert(movie.newRating == 8.4)
  }
}

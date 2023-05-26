package hu.imdb

import hu.imdb.functions.OscarCalculator
import hu.imdb.model.{FinalDataset, NumberOfRatings, Oscars, Rating, Title}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

/** Test cases for Oscar calculator object*/
class OscarCalculatorTest
  extends AnyFunSuite
  with BeforeAndAfter
  {

    //Reference for the Oscar calculator
    private var oscarCalculator:OscarCalculator = _

    before {
       oscarCalculator =  OscarCalculator(10)
    }

  test("if number of oscars is 0 and the rating is 7.5") {
    val numberOfOscars = 0
    val rating = 7.5
    val expected = 7.5
    val movie = FinalDataset(Title("movie"),
      numberOfOscars=Oscars(numberOfOscars),
      numberOfRatings=NumberOfRatings(0),
      rating = Rating(rating),
      newRating=Rating()
    )
    assert(oscarCalculator.calculate(movie) == expected)
  }

  test("if number of oscars is 4 and the rating is 7.5") {
    val numberOfOscars = 4
    val rating = 7.5
    val expected = 8
    val movie = FinalDataset(Title("movie"),
      numberOfOscars = Oscars(numberOfOscars),
      numberOfRatings = NumberOfRatings(0),
      rating = Rating(rating),
      newRating = Rating()
    )
    assert(oscarCalculator.calculate(movie) == expected)
  }

  test("if number of oscars is 10 and the rating is 7") {
    val numberOfOscars = 10
    val rating = 7
    val expected = 8
    val movie = FinalDataset(Title("movie"),
      numberOfOscars = Oscars(numberOfOscars),
      numberOfRatings = NumberOfRatings(0),
      rating = Rating(rating),
      newRating = Rating()
    )
    assert(oscarCalculator.calculate(movie) == expected)
  }
  test("if number of oscars is 11 and the rating is 7") {
    val numberOfOscars = 11
    val rating = 7
    val expected = 8.5
    val movie = FinalDataset(Title("movie"),
      numberOfOscars = Oscars(numberOfOscars),
      numberOfRatings = NumberOfRatings(0),
      rating = Rating(rating),
      newRating = Rating()
    )
    assert(oscarCalculator.calculate(movie) == expected)
  }

  test("if the newly core exceeds the 10") {
    val numberOfOscars = 11
    val rating = 9
    val expected = 10
    val movie = FinalDataset(Title("movie"),
      numberOfOscars = Oscars(numberOfOscars),
      numberOfRatings = NumberOfRatings(0),
      rating = Rating(rating),
      newRating = Rating()
    )
    assert(oscarCalculator.calculate(movie) == expected)
  }
}

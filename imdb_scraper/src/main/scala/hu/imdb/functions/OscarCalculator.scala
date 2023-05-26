package hu.imdb.functions

import hu.imdb.model.{FinalDataset, Oscars}


/**  Calculate new rating for movies based on the won oscars
 *
 * @constructor create a new OscarCalculator object
 * @param maximumRatingValue the maximum value in our rating scale
 */
class  OscarCalculator(maximumRatingValue:Int) {

  /** Calculate the score based on the number of awarded Oscars
   *
   * @param numberOfOscars the number of the awarded  Oscars
   * @return the score
   */
  private def score(numberOfOscars: Oscars): Double = {
    numberOfOscars match {
      case Oscars(value) if value == 0 => 0
      case Oscars(value) if value == 1 || value == 2 => 0.3
      case Oscars(value) if value >= 3 && value <= 5 => 0.5
      case Oscars(value) if value >= 6 && value <= 10 => 1.0
      case Oscars(value) if value > 10 => 1.5
    }
  }

  /**
   * Check the value whether exceeds the maximum value
   *
   * @param newRating the newly calculated rating value
   * @return the checked new value if it is less then 0 it will be 0
   *         if it is higher then 10 it will be 10
   *         otherwise the newly calculated value
   */
  private def checkNewValue(newRating: Double): Double = {
    newRating match {
      case _ if newRating < 0 => 0.0
      case _ if newRating > 10 => 10.0
      case _ if 1 <= newRating && newRating <= 10 => newRating
    }
  }


  /** Calculate the new rating based on the awarded oscar and original rating
   *
   * @param movie is that the calculation is executed
   * @return a new calculated rating value
   */
  def calculate(movie: FinalDataset):Double = {
    checkNewValue (movie.rating.value + score(movie.numberOfOscars))
    //what if the original rate is 0 and the movie has won some Oscars:)
    //what it the maximum value for rating 10?
    //what if the new value is higher than maximum value, currently 10
  }
}
/** Factory for [[OscarCalculator]] instances. */
object OscarCalculator {

  /** Creates an Oscar calculator with a given maximum value
   *
   * @param maximumRatingValue is the maximum value in our scale
   * @return a new Oscar Calculator object with the given maximum rating value
   */
  def apply(maximumRatingValue:Int):OscarCalculator = new OscarCalculator(maximumRatingValue)
}
package hu.imdb.functions

import hu.imdb.model.{FinalDataset, Rating}

/** Review  and Penalize the movies
 *
 * @param movies list of movies that we would like to review
 * @param divisor the divisor that defines the range after giving the penalty default: 100000
 * @param penalty the value of penalty per divisor default: 0.1
 */
class ReviewPenalizer(movies:List[FinalDataset],
                      divisor:Int = 100000,
                      penalty:Double = 0.1
                     ) {

  /** Find maximum value in the list
   *
   * @return the highest rating value
   */
  private def findTheHighestRating:Int = {
    movies.maxBy(_.numberOfRatings.value).numberOfRatings.value
  }

  /** Calculate the score
   *
   * @param highestValue the highest value in the given list
   * @param currentValue the current value of the movie
   * @return the calculated score based on the given parameter
   */
  private def calculateScore(theHighestNumberOfRating:Int,
                     numberOfRating:Int
                    ):Double = {

    ((theHighestNumberOfRating - numberOfRating) / divisor) * penalty

  }

  /** Calculate the new rating for the movie
   *
   * @param theHighestNumberOfRating is the highest number of rating in the list
   * @param movie is that object in which the calculation is applied
   * @return the newly calculated rating value for the movie
   */
  private def calculateNewRating(theHighestNumberOfRating:Int,
                                 movie: FinalDataset):Double = {
    val score = calculateScore(theHighestNumberOfRating, movie.numberOfRatings.value)
    //Formatting the output
    BigDecimal(movie.rating.value - score).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  /** Penalize the movies
   *
   * @return a new list in which the new rating values are set
   */
  def penalize:List[FinalDataset] = {
    val theHighestNumberOfRating = findTheHighestRating
    movies.map(e =>
      e.copy(newRating = Rating(calculateNewRating(theHighestNumberOfRating,e)))
    )
  }

}
/** Factory for [[ReviewPenalizer]] instances. */
object ReviewPenalizer {

  /** Creates a review penalizer instance with a given list of movies
   *
   * @param movies list of movies that we would like to review
   * @param divisor the divisor that defines the range after giving the penalty default: 100000
   * @param penalty the value of penalty per divisor default: 0.1
   * @return a new ReviewPenalizer with given list of movies
   */
  def apply(movies:List[FinalDataset],
            divisor: Int = 100000,
            penalty: Double = 0.1
           ): ReviewPenalizer =
    new ReviewPenalizer(movies,
      divisor,
      penalty
    )
}
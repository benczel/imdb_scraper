package hu.imdb.model

/**
 * Interface for movies classes
 *
 */
sealed trait Movie

/**
 * Describes a movie that is in the home test
 *
 *  @param title the title of the movie
 *  @param numberOfOscars the number of oscars that was awarded
 *  @param numberOfRatings the number of ratings how many people vote
 *  @param rating the current rating of the movie
 *  @param newRating the newly calculated rating value
 */
final case class FinalDataset(
                         title:Title,
                         numberOfOscars:Oscars,
                         numberOfRatings:NumberOfRatings,
                         rating:Rating,
                         newRating:Rating
                       ) extends Movie

/**
 * Describes a movie that is in the TOP 250 chart
 *
 * @param id the movie unique identifier in the IMDB
 * @param title the movie's title
 * @param rating reached by the voters
 * @param numberOfRatings the number of voters
 * @param url the url is the exact link to the movie
 */
final case class ImdbTopMovie(
                         id: MovieId,
                         title:Title,
                         rating: Rating,
                         numberOfRatings:NumberOfRatings,
                         url: Url
                       ) extends Movie

/**
 * Describes a movie that is in the IMDB Movie Page
 *
 * @param id the movie unique identifier in the IMDB
 * @param title the movie's title
 * @param oscars the number of oscar awards
 *
 */
final case class ImdbMovie(
                      id: MovieId,
                      title: Title,
                      oscars:Oscars
                    ) extends Movie

/** Type for Movie id
 *
 * @param value the movie id exact value
 */
final case class MovieId private (value:String) extends AnyVal

/** Type for Title
 *
 * @param value the title exact value
 */
final case class Title private (value:String) extends AnyVal

/** Type for Oscars
 *
 * @param value the oscars exact value
 */
case class Oscars private(value:Int) extends AnyVal

/** Type for Rating
 *
 * @param value the rating exact value
 */
final case class Rating private(value:Double = 0.0) extends AnyVal

/** Type for number of ratings
 *
 * @param value the number of ratings exact value
 */
final case class NumberOfRatings private(value:Int) extends AnyVal

/** Type for URL
 *
 * @param value the url exact value
 */
final case class Url private(value:String) extends AnyVal
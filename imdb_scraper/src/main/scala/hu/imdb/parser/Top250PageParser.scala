package hu.imdb.parser

import hu.imdb.model.{ImdbTopMovie, Movie, MovieId, NumberOfRatings, Rating, Title, Url}
import net.ruippeixotog.scalascraper.dsl.DSL.deepFunctorOps
import net.ruippeixotog.scalascraper.model.{Document, Element, ElementQuery}

/** Page parser for parsing TOP 250 movies in the chart
 *
 * @param top250Page the top 250 movies' HTML DOM
 */
class Top250PageParser(top250Page:Document) extends PageParser[ImdbTopMovie] {

  /** Extracts the movies' table in the HTML DOM
   *
   * @return a [[net.ruippeixotog.scalascraper.model.ElementQuery]] that represents the table of movies
   */
  def getTopMovies(): ElementQuery[Element] = {
    getSubDOM(top250Page, ".lister-list")
  }

  /** Extracts HTML elements for the given CCS selector
   *
   * @param ccsSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @return a list of selected [[net.ruippeixotog.scalascraper.model.Element]] that contains the HTML tag
   */
  private def getColumn(ccsSelector:String): List[Element] = getElements(getTopMovies(), ccsSelector)

  /** Extracts the title column from the table of movies
   *
   * @return a list of [[net.ruippeixotog.scalascraper.model.Element]] that contains the title column in the table
   */
  def getTitleColumn(): List[Element] =
    getColumn(".titleColumn")

  /** Extracts the rating column from the table of movies
   *
   * @return a list of [[net.ruippeixotog.scalascraper.model.Element]] that contains the rating column in the table
   */
  def getRatingColumn(): List[Element] =
    getColumn(".ratingColumn.imdbRating")

  /** Extracts the movie's url from the href attribute
   *
   * @return lite of the parsed urls
   */
  def getMovieUrl(): List[String] = {
    getTitleColumn().map(
      el => el >> getHrefAttribute("a")
    )
  }

  /** Extracts the movie's id from the movies HTML link tag
   *
   * @return list of the parsed movies' id
   */
  def getMovieId(): List[String] = {
    getMovieUrl().map(
      el => el.split('/')(2)
    )
  }

  /** Extracts the movie's title from the HTML link tag
   *
   * @return list of the parsed titles
   */
  def getTitle(): List[String] =
    getTitleColumn().map(
      el => el >> getTextExtractor("a")
    )

  /** Extracts the movies' rating value from the
   *
   * @return
   */
  def getRating(): List[Double] = {
    getRatingColumn().map(
      el => (el >> getTextExtractor("strong")).toDouble
    )
  }

  /** Extracts the movies' number of ratings from text of rating's HTML tag
   *
   * @return list of the parsed number of ratings
   */
  def getNumberOfRatings(): List[Int] = {
    val pattern = "\\s[0-9]+(?:,[0-9]+)*".r

    val numberOfRatingsStrings = getRatingColumn()
      .map(el => el >> getAttributeExtractor("strong", "title"))

    numberOfRatingsStrings.map(
      e => pattern.findFirstIn(e).getOrElse("0").trim.replace(",", "").toInt
    )
  }

  /** Parse the top 250 page
   *
   *  @return list of the parsed movies that are on the given page
   */
  override def parse(): List[ImdbTopMovie] = {
    val listOfMovieIds = getMovieId()
    val listOfMovieTitle = getTitle()
    val listOfRating = getRating()
    val listOfNumberOfRatings = getNumberOfRatings()
    val listOfUrls = getMovieUrl()

    for (i <- List.range(0, 250))
      yield ImdbTopMovie(
        MovieId(listOfMovieIds(i)),
        Title(listOfMovieTitle(i)),
        Rating(listOfRating(i)),
        NumberOfRatings((i)),
        Url(listOfUrls(i))
      )
  }
}
/** Factory for [[Top250PageParser]] instances. */
object Top250PageParser {

  /** Creates a parser for TOP 250 page
   *
   * @param top250Page the top 250 movies' HTML DOM
   * @tparam T type value that has to be a subclass of the [[Movie]]
   * @return [[Top250PageParser]] instance
   */
  def apply[T <: Movie](top250Page:Document): Top250PageParser = new Top250PageParser(top250Page)
}

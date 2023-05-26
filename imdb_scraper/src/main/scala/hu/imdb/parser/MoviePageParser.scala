package hu.imdb.parser

import hu.imdb.model.{ImdbMovie, Movie, MovieId, Oscars, Title}
import net.ruippeixotog.scalascraper.dsl.DSL.deepFunctorOps
import net.ruippeixotog.scalascraper.model.{Document, Element, ElementQuery}


/** Movie parser for parsing the movie's IMDB page
 *
 * @param moviePage the given movie's HTML DOM
 */
class MoviePageParser(moviePage:Document) extends PageParser[ImdbMovie] {

  /** Extracts the Awards section of the given page
   *
   * @return a [[net.ruippeixotog.scalascraper.model.ElementQuery]] that represents the award section in the page
   */
  def getAwardsElement:ElementQuery[Element] =
    getSubDOM(moviePage, ".sc-fcdc3619-0.kKcbrZ.base")

  /** Extracts the Oscar part of the Award section
   *
   * @return a list of selected [[net.ruippeixotog.scalascraper.model.Element]] that contains the HTML tag
   */
  def getOscarElements:List[Element] = {
    getElements(getAwardsElement, ".ipc-metadata-list-item__label.ipc-metadata-list-item__label--link")
  }

  /** Extracts the movie's title from the HTML DOM
   *
   * @return the parsed and formatted movie title
   */
  def getTitle():String = {
    moviePage.title.replaceAll("(\\(\\d+\\))", "")
      .replaceAll("-", "")
      .replaceAll("IMDb", "")
      .trim
  }

  /** Extracts the number of won Oscars
   *
   * @return the parsed and formatted number of won Oscars
   */
  def getOscar:Int = {
    val listOfOscar = getOscarElements.map(element => element >> getTextExtractor("a"))

    listOfOscar.map(
      element => Option.when(element.toLowerCase.contains("won"))
        (element.replaceAll("[A-Za-z]+", "").trim)
      ).head.getOrElse("0").toInt

  }

  /** Parse the given movie's page
   *
   *  @return list of the parsed movies that are on the given page
   */
  override def parse(): List[ImdbMovie] = {
    val numberOfOscars = getOscar
    val title = getTitle()
    List(ImdbMovie(MovieId(""), Title(title), Oscars(numberOfOscars)))
  }
}
/** Factory for [[MoviePageParser]] instances. */
object MoviePageParser{

  /** Creates a parser for given movie's HTML DOM
   *
   * @param moviePage the given movie's HTML DOM
   * @tparam T type value that has to be a subclass of the [[Movie]]
   * @return [[MoviePageParser]] instances
   */
  def apply[T <: Movie](moviePage:Document): MoviePageParser = new MoviePageParser(moviePage)
}
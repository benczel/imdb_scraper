package hu.imdb.imdb

import hu.imdb.model.{ImdbMovie, ImdbTopMovie, Url}
import hu.imdb.parser.{MoviePageParser, Top250PageParser}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document

import scala.util.{Failure, Success, Try}

/** Singleton object defines those functions that are available on the IMDB page
 *
 *
 */
object Imdb {

  private val rootUrl = "https://www.imdb.com/"
  private lazy val browser = JsoupBrowser()

  /** Tries to open the given url
   *
   * @param url the address of the webpage
   * @return try object that contains either an opened document or an exception
   */
  private def tryToOpenThePage(url:String): Try[Document] = Try {
    browser.get(url)
  }

  /** Check the given value whether exceeds the 250
   *
   * @param limit limit that says how many movies are requested in the result set
   * @return validated value that can be the given value if it is in between 1 and 250 otherwise the value is 250
  */
  private def checkLimit(limit:Int):Int =
    Option.when(1 <= limit && limit <= 250)(limit).getOrElse(250)

  /** Select the first n movies from the TOP 250 chart
   *
   * @param limit says how many movies are in the result set
   * @return the list of movies that are in the page or a throwable object
   */
  def getTop250(limit:Int = 250):Either[List[ImdbTopMovie], Throwable] = {
    val topSuffix = "chart/top/"
    val top250Url = rootUrl + topSuffix
    lazy val top250Page = tryToOpenThePage(top250Url)
    top250Page match {
      case Success(page) => Left(Top250PageParser[ImdbTopMovie](page).parse().slice(0, checkLimit(limit)))
      case Failure(throwable) => Right(throwable)
    }
  }

  /** Select the movie's details from the given url
   *
   * @param movieUrl the url of the movie
   * @return value is the movie's details that is on the given url or a throwable object
   */
  def getMovieDetails(movieUrl:Url):Either[ImdbMovie, Throwable] = {
    val moviesFullUrl = rootUrl + movieUrl
    lazy val moviePage = tryToOpenThePage(moviesFullUrl)
    moviePage match {
      case Success(page) => Left(MoviePageParser[ImdbMovie](page).parse().head)
      case Failure(throwable) => Right(throwable)
    }
  }
}

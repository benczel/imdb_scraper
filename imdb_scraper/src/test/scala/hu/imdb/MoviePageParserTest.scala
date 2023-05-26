package hu.imdb

import hu.imdb.model.{ImdbMovie, Title}
import hu.imdb.parser.MoviePageParser
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success, Try}

class MoviePageParserTest
  extends AnyFunSuite
    with BeforeAndAfter {

  private val browser = JsoupBrowser()
  private var moviePage: Document = _
  private val url = "https://www.imdb.com/title/tt0111161/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=1a264172-ae11-42e4-8ef7-7fed1973bb8f&pf_rd_r=QG5WKGC455J8SCPY87Q8&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_tt_1"
  private var moviePageParser: MoviePageParser = _
  val engTitle = Title("The Shawshank Redemption")
  val huTitle = Title("A remény rabjai")

  before {
    moviePage = Try {
      browser.get(url)
    } match {
      case Success(page) => page
      case Failure(_) => cancel()
    }
    moviePageParser = MoviePageParser[ImdbMovie](moviePage)
  }

  test("the movie has not won Oscar") {
    val movie = moviePageParser.parse().head
    val parsedTitle = movie.title
    assert(movie.oscars.value == 0  &&
      (parsedTitle == engTitle) || (parsedTitle == huTitle)
    )
  }
  test("movie page title is The Shawshank Redemption/A remény rabjai"){
    val parsedTitle = moviePageParser.getTitle()
    assert((parsedTitle equals  engTitle.value) || (parsedTitle equals huTitle.value))
  }
}

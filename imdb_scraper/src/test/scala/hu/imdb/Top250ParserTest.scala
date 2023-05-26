package hu.imdb

import hu.imdb.model.ImdbTopMovie
import hu.imdb.parser.Top250PageParser
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.deepFunctorOps
import net.ruippeixotog.scalascraper.model.Document
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success, Try}

class Top250ParserTest
  extends AnyFunSuite
  with BeforeAndAfter {

  private val browser = JsoupBrowser()
  private var top250Page:Document = _
  private val url = "https://www.imdb.com/chart/top"
  private var top250Parser:Top250PageParser = _

  before{
      top250Page = Try {
        browser.get(url)
      } match {
        case Success(page) => page
        case Failure(_) => cancel()
      }
      top250Parser = Top250PageParser[ImdbTopMovie](top250Page)

  }

  test("if the get move id is 250"){
    assert(top250Parser.getMovieId().length == 250)
  }

  test("if the get title is 250"){
    assert(top250Parser.getTitle().length == 250)
  }

  test("if the get rating is 250"){
    assert(top250Parser.getRating().length == 250)
  }

  test("if the get get number of ratings"){
    assert(top250Parser.getNumberOfRatings().length == 250)
  }

  test("if the get rating column is empty"){
    assert(top250Parser.getRatingColumn().isEmpty == false)
  }

  test("if the text extractor is correct and the first element value is 9.2"){
    val ratingColumn = top250Parser.getRatingColumn().head
    val rating = ratingColumn >> top250Parser.getTextExtractor("strong")

    assert(rating.toDouble == 9.2)

  }
}

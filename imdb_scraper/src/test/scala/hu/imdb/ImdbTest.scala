package hu.imdb

import org.scalatest.{BeforeAndAfter, Ignore}
import org.scalatest.funsuite.AnyFunSuite
import hu.imdb.imdb.Imdb
import hu.imdb.model.{ImdbMovie, Title, Url}

class ImdbTest
  extends AnyFunSuite
  with BeforeAndAfter{


  test("there is no internet") {
    val imdb = Imdb.getTop250()

    imdb match {
      case Right(_) => assert(true)
      case Left(_) => cancel()
    }
  }

  test("if the result count is 250") {
    Imdb.getTop250() match {
      case Left(listOfMovies) => assert(listOfMovies.length == 250 )
      case Right(throwable) => cancel()
    }
  }

  test("if the get top is lower then 250 and higher then 1") {
    val limit = 10

    Imdb.getTop250(limit) match {
      case Left(listOfMovies) => assert(listOfMovies.length == limit)
      case Right(_) => cancel()
    }
  }

  test("if the get top is lower then 1") {
    val limit = -1

    Imdb.getTop250(limit) match {
      case Left(listOfMovies) => assert(listOfMovies.length == 250)
      case Right(_) => cancel()
    }
  }

  test("the top movie is The Shawshank Redemption/A remény rabjai"){
    val engTitle = Title("The Shawshank Redemption")
    val huTitle = Title("A remény rabjai")


    Imdb.getTop250(1) match {
      case Left(topMovieTitle) => assert(
        (topMovieTitle.head.title ==  engTitle) || (topMovieTitle.head.title == huTitle)
      )
      case Right(_) => cancel()
    }
  }


  ignore("the top movie's number of ratings is 2,719,862"){
    val numberOfRatings  = 2719862

    Imdb.getTop250(1) match {
      case Left(topMovie) => assert(topMovie.head.numberOfRatings.value == numberOfRatings)
      case Left(_) => cancel()
    }
  }

  test("get movie details by url") {
    val movieUrl = "title/tt0111161/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=1a264172-ae11-42e4-8ef7-7fed1973bb8f&pf_rd_r=5YYE6GMQMZVZCPPB1ZVQ&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_tt_1"

    Imdb.getMovieDetails(Url(movieUrl)) match {
      case Left(movie) => assert(movie.isInstanceOf[ImdbMovie])
      case Right(_) => cancel()
    }
  }
}
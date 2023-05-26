package hu.imdb.parser

import hu.imdb.model.Movie
import net.ruippeixotog.scalascraper.dsl.DSL.{deepFunctorOps, extractor}
import net.ruippeixotog.scalascraper.model.{Document, Element, ElementQuery}
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{attr, elementList, text}
import net.ruippeixotog.scalascraper.scraper.HtmlExtractor

/** A representation of a Page parser.
 * It provides some predefined functionality in order to make easier the page parsing
 *
 * @tparam T type value that has to be a subclass of the [[Movie]]
 */
trait PageParser[T <: Movie] {

  /** Extract the given css selector and its children elements from the given document
   *
   * @param document it is a [[net.ruippeixotog.scalascraper.model.Document]] that represents a HTML DOM
   * @param cssSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @return [[net.ruippeixotog.scalascraper.model.ElementQuery]] that is a part of the given document
   */
  def getSubDOM(document: Document,
                 cssSelector: String,
                ): ElementQuery[Element] =
    document >> extractor(cssSelector)

  /** Extract elements in the sub DOM
   *
   * @param elementQuery [[net.ruippeixotog.scalascraper.model.ElementQuery]] that is a part of the given document
   * @param cssSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @return a [[net.ruippeixotog.scalascraper.model.Element]] that represents a HTML tag
   */
  def getElements(elementQuery: ElementQuery[Element],
                  cssSelector: String
                 ): List[Element] =
    elementQuery >> extractor(cssSelector, elementList)

  /** Return a HTML extractor object that extracts the given attribute value from the given HTML tag
   *
   * @param cssSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @param attributeName it is an attribute name that is in a HTML tag.
   * @return a [[net.ruippeixotog.scalascraper.scraper.HtmlExtractor]] that extracts the element's content
   */
  def getAttributeExtractor(cssSelector: String,
                            attributeName: String
                           ): HtmlExtractor[Element, String] =
    extractor(cssSelector, attr(attributeName))

  /** Extracts the text content of the given css selector
   *
   * @param cssSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @return a [[net.ruippeixotog.scalascraper.scraper.HtmlExtractor]] that extracts the element's content
   */
  def getTextExtractor(cssSelector: String): HtmlExtractor[Element, String] =
    extractor(cssSelector, text)

  /** Extracts the href attribute of the given HTML tag
   *
   * @param cssSelector it is a HTML class or id name that is in the DOM. [[https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors]]
   * @return a [[net.ruippeixotog.scalascraper.scraper.HtmlExtractor]] that extracts the given element's attribute
   */
  def getHrefAttribute(cssSelector: String): HtmlExtractor[Element, String] = {
    getAttributeExtractor(cssSelector, "href")
  }

  /** Abstract method that the subclasses have to implement
   *
   *
   * @return list of the parsed movies that are on the given page
   * @Todo what if one of the selector is invalid? Handling this kind of error message
   */
  def parse():List[T]
}
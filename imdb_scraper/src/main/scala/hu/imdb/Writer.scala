package hu.imdb


import com.github.tototoshi.csv.CSVWriter

import java.io.File

/***
 * Writer class for writing the csv out
 */
class Writer {

  /***
   * Open the given file for writing
   *
   * @param file a file object that represents the output file
   * @return an opened CSVWriter object
   */
  private def openTheFile(file: File): CSVWriter = {
    CSVWriter.open(file)
  }

  /*** Write the given dataset into the given file
   *
   * @param fileName where to be required the dataset
   * @param dataSet that contains the data and to be wanted to write
   */
  def write(fileName: String,
            dataSet: List[List[Any]]
           ):Unit = {
    val csvWriter = openTheFile(new File(fileName))
    csvWriter.writeAll(dataSet)
    csvWriter.close()
  }
}

/***
 * Factory for [[Writer]] instances
 */
object Writer{
  /**
   * Creates a new writer instance
   *
   * @return a new Writer object
   */
  def apply():Writer = new Writer()
}
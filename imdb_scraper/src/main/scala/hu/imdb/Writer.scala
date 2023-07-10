package hu.imdb


import com.github.tototoshi.csv.CSVWriter

import java.io.File

class Writer {

  private def createCSVWriter(file: File): CSVWriter = {
    CSVWriter.open(file)
  }


  def write(fileName: String,
            dataSet: List[List[Any]]
           ):Unit = {
    val csvWriter = createCSVWriter(new File(fileName))
    csvWriter.writeAll(dataSet)
    csvWriter.close()
  }
}

object Writer{

  def apply():Writer = new Writer()
}
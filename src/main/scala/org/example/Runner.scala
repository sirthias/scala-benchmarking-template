package org.example

import com.google.caliper.runner.CaliperMain

object Runner {
  def main(args: Array[String]) {
    CaliperMain.main(Array(classOf[org.example.MyBenchmark].getName))
  }
}
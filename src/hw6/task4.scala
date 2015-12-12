package hw6

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random


object SimpleApp {
  def merge(srcPath: String, dstPath: String): Unit = {
    val hadoopConfig = new Configuration()
    val hdfs = FileSystem.get(hadoopConfig)
    FileUtil.copyMerge(hdfs, new Path(srcPath), hdfs, new Path(dstPath), false, hadoopConfig, null)
  }

  def generateData(sc: SparkContext): RDD[Double] = {
    sc.parallelize(Seq[Double](), 10)
      .mapPartitions { _ => {
        (1 to 10000000).map { _ => Random.nextDouble }.iterator
      }
      }
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)

//    generateData(sc).saveAsTextFile("data")
//    merge("data", "data.txt")
//    scala.reflect.io.Path("data").deleteRecursively()

    val data = sc.textFile("data.txt").sortBy(_.toDouble)
    data.saveAsTextFile("tmp")
    merge("tmp", "out.txt")
    scala.reflect.io.Path("tmp").deleteRecursively()
  }
}
package connection

import slick.driver.MySQLDriver

trait MySQLDBImpl {

  val driver = MySQLDriver

  import driver.api._
  import slick.driver.MySQLDriver.api._

  val db = Database.forConfig("mysql")

}

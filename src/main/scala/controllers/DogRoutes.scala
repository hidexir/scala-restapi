package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives
import dao.DogDao
import model.Dog

import scala.concurrent.ExecutionContextExecutor

trait DogRoutes extends SprayJsonSupport with DogDao {


    implicit val dispatcher: ExecutionContextExecutor

    val routes =
        pathPrefix("dogs") {
        pathEnd {
            get {
                complete(getAll)
            } ~
            post {
                entity(as[Dog]) { dog =>
                    complete {
                        createEntry(dog).map { result => HttpResponse(entity = "dog has been saved successfully") }
                    }
                }
            }
        } ~
            path(IntNumber) { id =>
                get {
                    complete(getById(id))
                } ~
                put {
                    entity(as[Dog]) { dog =>
                        complete {
                            val newDog = Dog(dog.name, Option(id))
                            updateEntry(newDog).map { result => HttpResponse(entity = "dog has been updated successfully") }
                        }
                    }
                } ~ delete {
                    complete {
                        deleteEntry(id).map { result => HttpResponse(entity = "dog has been deleted successfully") }
                    }
                }
            }
    }

    def  create(params: CreateParams) =
        service ? Create(params)
}


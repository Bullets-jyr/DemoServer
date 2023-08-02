package kr.co.bullets

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable
import kotlin.Exception

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        module()
//        module2()
    }.start(wait = true)
}

fun Application.module() {
//    routing {
//        get("/") {
//            call.respondText("Hello, World!")
//        }
//    }
    install(Routing) {
//        route(path = "/", method = HttpMethod.Get) {
//           handle {
//               call.respondText("Hello, World!")
//           }
//        }
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/users/{username}") {
            val username = call.parameters["username"]
            val header = call.request.headers["Connection"]
            if (username == "Admin") {
                call.response.header(name = "CustomHeader", "Admin")
                call.respond(message = "Hello Admin", status = HttpStatusCode.OK)
            }
            call.respondText("Greetings, $username with header: $header")
        }
        get("/user") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText("Hi, my name is $name, I'm $age years old!")
        }
        get("/person") {
            val person = Person(name = "jyr", age = 26)
            try {
                call.respond(message = person, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get("/redirect") {
            call.respondRedirect("/moved", permanent = false)
        }
        get("/moved") {
            call.respondText("You have been successfully redirected!")
        }
    }
}

//fun Application.module2() {
//    routing {
//        get("/book") {
//            call.respondText("Hello, World 2!")
//        }
//    }
//}

@Serializable
data class Person(
    val name: String,
    val age: Int,
)


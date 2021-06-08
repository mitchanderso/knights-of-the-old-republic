package com.ld.learning

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ld.learning.domain.Jedi
import com.ld.learning.domain.enum.JediRank
import com.ld.learning.exception.ExceptionResponse
import com.ld.learning.repository.JediRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    install(Authentication) {
        jwt("galactic-republic-id") { // We want to do JWT based authentication, this could be basic auth or others...
            realm = "https://galactic-replublic"
            // Verify that the token provided to us meets certain parameters
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret")) // No wonder the republic fell apart so easily...
                    .withIssuer("galactic-republic") // The galactic republic issues JWTs
                    .withAudience("ktor-api") // That are meant to be used on our little ktor API
                    .build())
            // Validate that the token provided meets certain criteria
            validate { credential ->
                if (credential.payload.audience.contains("ktor-api")) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }



    install(ContentNegotiation) {
        jackson()
    }

    install(StatusPages) {
        exception<BadRequestException> {
                cause -> call.respond(HttpStatusCode.BadRequest, ExceptionResponse(cause.message!!, HttpStatusCode.BadRequest))
        }
        exception<NotFoundException> {
                cause -> call.respond(HttpStatusCode.NotFound, ExceptionResponse(cause.message!!, HttpStatusCode.NotFound))
        }
    }

    val jediRepository = JediRepository()

    routing {
        route("/jedi") {
            get("/{name}") {
                val name = call.parameters["name"] ?: throw BadRequestException("Missing {name} parameter")
                val jedi = jediRepository.getJediByName(name) ?: throw NotFoundException("Midichlorian count too low")
                call.respond(jedi)
            }
        }

        authenticate("galactic-republic-id") {
            patch("/admit") {
                // Only members of the high council can permit new members to the council
                val rankClaim = call.authentication.principal<JWTPrincipal>()?.payload?.getClaim("rank")
                if (rankClaim?.asString() == "master") {
                    // ... permit them to the council
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    val file = File("resources/files/windu_gif.gif")
                    // We do not permit you to the council OR grant you the rank of master
                    call.respondBytes(file.readBytes(), ContentType.parse("image/gif"), HttpStatusCode.Unauthorized)
                }
            }
        }

        get("/") {
            call.respondText("Close the blast doors!", contentType = ContentType.Text.Plain)
        }

        route("/trade-federation"){
            get("/ambassadors") {
                call.respond(
                    listOf(
                        Jedi("Qui-gon Jinn", "Count Dooku", "Obi-Wan Kenobi", JediRank.KNIGHT),
                        Jedi("Obi-Wan Kenobi", "Qui-gon Jinn", "Anakin Skywalker", JediRank.PADAWAN)
                    )
                )
            }
        }
    }

}


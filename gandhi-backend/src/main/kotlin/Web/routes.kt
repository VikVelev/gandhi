package Web

import Data.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.UserPasswordCredential
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.apache.ignite.cache.query.ScanQuery
import java.util.*

fun Application.routes() {

    install(Routing) {

//        get("/top/{count}/{time?}") {
        get("/") {

            call.respond(DataStore.blocks.query(ScanQuery<Long, Block>()).map { it.value })

        }

        route("blocks") {

            get("/{id}") {

                call.respond(DataStore.blocks.get(call.parameters["id"]!!.toLong()))
            }

            post("/submit") {
                val block = call.receive<Block>()


                DataStore.blocks.put(block.number, block);

                call.respondText("post received")

            }

        }

        route("balance") {
            get("/{id}") {
                call.respond(DataStore.balances.get(call.parameters["id"]))
            }

            get("/") {
                call.respond(DataStore.balances.query(ScanQuery<String, Long>()).map { Pair(it.key, it.value) } )
            }
        }

        route("transactions") {

        }

    }
//        /**
//         * All [Route]s in the authentication block are secured.
//         */
//        authenticate {
//            route("secret") {
//
//                get {
//                    //                    val user = call.user!!
////                    call.respond(user.countries)
//                }
//
//                put {
//                    TODO("All your secret routes can follow here")
//                }
//
//            }
//        }
//
//        /**
//         * Routes with optional authentication
//         */
//        authenticate(optional = true) {
//            get("optional") {
//                //                val user = call.user
////                val response = if (user != null) "authenticated!" else "optional"
////                call.respond(response)
//            }
//        }
    }

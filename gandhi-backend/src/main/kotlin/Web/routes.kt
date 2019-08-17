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
import org.apache.ignite.lang.IgniteFuture
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun <V> IgniteFuture<V>.await(): V {
    return suspendCoroutine { continuation ->
        this.listen { fut ->
            try {
                val res = fut.get();
                continuation.resume(res);
            }catch(e: Exception){
                continuation.resumeWithException(e);
            }
        }
    }
}

fun Application.routes() {

    install(Routing) {

//        get("/top/{count}/{time?}") {


        route("blocks") {

            get("/{id}") {

                call.respond(DataStore.blocks.getAsync(call.parameters["id"]!!.toLong()).await())
            }

            post("/submit") {
                val block = call.receive<Block>()
				println("block: $block")


                block.number = DataStore.nextBlock()
                block.timestamp = Date().time
                DataStore.blocks.putAsync(block.number, block).await()

                call.respond(mapOf("number" to block.number, "timestamp" to block.timestamp))
            }

        }

        route("balance") {
            get("/{id}") {
                call.respond(DataStore.balances.getAsync(call.parameters["id"]).await())
            }

            get("/") {
                call.respond(DataStore.balances.query(ScanQuery<String, Long>()).map { Pair(it.key, it.value) } )
            }
        }

        route("transactions") {
            get("/") {
                call.respond(DataStore.blocks.query(ScanQuery<Long, Block>()).map { it.value })
            }
            get("/{id}") {
                call.respond(DataStore.blocks.getAsync(call.parameters["id"]!!.toLong()).await())
            }
            get("/latest/{count?}") {
                call.respond(DataStore.blocks.getAsync(DataStore.counter.get() - 1).await().transactions.flatMap {
                    it.value.transfers.map { transfer ->
                        mapOf("from" to it.value.sender, "to" to transfer.address, "amount" to transfer.value.toLong())
                    }
                }.take( call.parameters["count"]?.toInt() ?: 10 ))
            }
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


import Data.Block
import Data.Transaction
import Data.Transfer
import Data.TransferState
import Web.module
import Web.routes
import com.marcinmoskala.math.combinations
import com.marcinmoskala.math.combinationsWithRepetitions
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.ScanQuery
import java.nio.file.Paths
import org.apache.ignite.IgniteDataStreamer
import java.math.BigInteger
import java.util.*
import kotlin.random.Random

var random = Random(Date().time)

fun testA() {
    var block = Block(author = "gosho", number = DataStore.nextBlock(), timestamp = Date().time, transactions = mutableMapOf())
    for(i in 500L .. 999) {
        val user = "user${i}"
        block.transactions[user] = Transaction(user, (1..50).map { Transfer("user${random.nextLong(0,  500)}", value = BigInteger.valueOf(1), state = TransferState.Finished ) }, blockNumber = block.number.toInt())
    }

    DataStore.blocks.putAsync(block.number, block)
}

fun testB() {
    var block = Block(author = "pesho", number = DataStore.nextBlock(), timestamp = Date().time, transactions = mutableMapOf())
    for(i in 1L .. 500) {
        val user = "user${i}"
        block.transactions[user] = Transaction(user, (1..50).map { Transfer("user${random.nextLong(501, 501 + i)}", value = BigInteger.valueOf(1), state = TransferState.Finished ) }, blockNumber = block.number.toInt())
    }

    DataStore.blocks.putAsync(block.number, block)
}

fun testASingle() {
    var block = Block(author = "gosho", number = DataStore.nextBlock(), timestamp = Date().time, transactions = mutableMapOf())
    val user = "user${random.nextLong(500, 1000)}"

    block.transactions[user] = Transaction(user, (1..2).map { Transfer("user${random.nextLong(0,  500)}", value = BigInteger.valueOf(1), state = TransferState.Finished ) }, blockNumber = block.number.toInt())


    DataStore.blocks.put(block.number, block)
}

fun testBSingle() {
    var block = Block(author = "pesho", number = DataStore.nextBlock(), timestamp = Date().time, transactions = mutableMapOf())

    val user = "user${Random(Date().time).nextLong(0, 500)}"
    block.transactions[user] = Transaction(user, (1..2).map { Transfer("user${Random(Date().time).nextLong(501, 1000)}", value = BigInteger.valueOf(1), state = TransferState.Finished ) }, blockNumber = block.number.toInt())


    DataStore.blocks.put(block.number, block)
}

fun main() {
    println("Ignite Version: ${DataStore.ignite.version()}")

    DataStore.balances.put("pesho", BigInteger.valueOf(1000))
    DataStore.balances.put("gosho", BigInteger.valueOf(1))

    for(i in 1 .. 1000) {
        DataStore.balances.put("user${i}", BigInteger.valueOf(random.nextLong(50000, 100000)))
    }

    DataStore.ignite.services().deployClusterSingleton("BlockProcessor", BlockProcessor())


//    //heat up
//    for(i in 0 .. 5) {
//        if(i % 2 == 0) {
//            testA()
//        }else{
//            testB()
//        }
//    }
//
//    for(i in 0 .. 5) {
//        if(i % 2 == 0) {
//            testASingle()
//        }else{
//            testBSingle()
//        }
//
//    }

//    var t = System.nanoTime()
//    for(i in 0 .. 60) {
//        if(i % 2 == 0) {
//            testA()
//        }else{
//            testB()
//        }
//    }
//    println("   Elapsed time: ${(System.nanoTime() - t) / 1000000.0 }")


    embeddedServer(Netty, 8080) { module(); routes() }.start(wait = false)

//    t = System.nanoTime()
//    for(i in 0 .. (50 * 500)) {
//        if(i % 2 == 0) {
//            testASingle()
//        }else{
//            testBSingle()
//        }
//
//    }
//    println("   Elapsed time: ${(System.nanoTime() - t) / 1000000.0 }")
//
}
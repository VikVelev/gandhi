
import Web.module
import Web.routes
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.ScanQuery
import java.nio.file.Paths
import org.apache.ignite.IgniteDataStreamer
import java.math.BigInteger
import java.util.*


fun main() {
    println("Ignite Version: ${DataStore.ignite.version()}")

    DataStore.balances.put("pesho", BigInteger.valueOf(1000))
    DataStore.balances.put("gosho", BigInteger.valueOf(1))

    DataStore.ignite.services().deployClusterSingleton("BlockProcessor", BlockProcessor())

    embeddedServer(Netty, 8080) { module(); routes() }.start(true)
}
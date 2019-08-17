
import Web.module
import Web.routes
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.ScanQuery
import java.nio.file.Paths
import org.apache.ignite.IgniteDataStreamer
import java.util.*


fun main() {
    println("IgniteVersion: ${DataStore.ignite.version()}")
    embeddedServer(Netty, 8080) { module(); routes() }.start(true)
}
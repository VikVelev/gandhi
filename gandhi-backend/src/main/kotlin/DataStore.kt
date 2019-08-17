import Data.*
import org.apache.ignite.IgniteCache
import org.apache.ignite.IgniteDataStreamer
import org.apache.ignite.Ignition
import org.apache.ignite.cache.CacheMode
import org.apache.ignite.cache.query.ScanQuery
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.DataStorageConfiguration
import org.apache.ignite.stream.StreamVisitor
import java.math.BigInteger
import java.nio.file.Paths
import java.util.*
import kotlin.random.Random


object DataStore {
    var blo_cfg = CacheConfiguration<Long, Block>("Blocks")
    var bal_cfg = CacheConfiguration<String, BigInteger>("Balances")

    val ignite = Ignition.start(igniteConfiguration {
        igniteHome = Paths.get("ignite").toAbsolutePath().toString()
        isPeerClassLoadingEnabled = true

        blo_cfg.setCacheMode(CacheMode.REPLICATED)
        bal_cfg.setCacheMode(CacheMode.REPLICATED)

        setCacheConfiguration(blo_cfg)
        setCacheConfiguration(bal_cfg)

//        dataStorageConfiguration = DataStorageConfiguration()
//        dataStorageConfiguration.defaultDataRegionConfiguration.setPersistenceEnabled(true)
    })

    var counter = ignite.atomicLong("Counter", 0, true)
    fun nextBlock(): Long {
        return counter.andIncrement
    }

    init {
        ignite.cluster().active(true)
    }

    var blocks: IgniteCache<Long, Block> = ignite.getOrCreateCache("Blocks")

//    var senders: IgniteCache<Pair<Long, BigInteger>, Boolean> = ignite.getOrCreateCache("Senders")

    var balances: IgniteCache<String, BigInteger> = ignite.getOrCreateCache("Balances")

    var random = Random(Date().time)
}


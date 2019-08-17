import Data.Block
import org.apache.ignite.services.Service
import org.apache.ignite.services.ServiceContext
import org.apache.ignite.Ignite
import org.apache.ignite.IgniteQueue
import org.apache.ignite.configuration.CollectionConfiguration
import org.apache.ignite.lang.IgniteFuture
import org.apache.ignite.resources.IgniteInstanceResource
import java.math.BigInteger
import java.util.concurrent.CompletableFuture


fun <V> IgniteFuture<V>.toCompletableFuture(): CompletableFuture<V> {
	val future = CompletableFuture<V>()
	this.listen { fut ->
		try {
			val res = fut.get();
			future.complete(res);
		}catch(e: Exception){
			future.completeExceptionally(e);
		}
	};
	return future;
}


class BlockProcessor : Service {

	@IgniteInstanceResource
	lateinit var ignite: Ignite

	lateinit var queue: IgniteQueue<Block>

	override fun init(ctx: ServiceContext?) {
		queue = ignite.queue<Block>("PendingBlocks", 0, CollectionConfiguration())
	}

	override fun cancel(ctx: ServiceContext?) {

	}

	override fun execute(ctx: ServiceContext?) {
		while (!ctx!!.isCancelled) {
			var block = queue.poll()

			val results = CompletableFuture.allOf(
				*block.transactions.map {
					ignite.compute().affinityCallAsync(DataStore.balances.name, it.key) {

						for (transfer in it.value.transfers) {
							
						}

					}.toCompletableFuture()
				}.toTypedArray()
			)



			println("Block: ${block.number}")


		}
	}

	fun addBlock(block: Block) {
		queue.put(block)
	}

}
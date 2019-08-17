import Data.Block
import Data.TransferState
import org.apache.ignite.services.Service
import org.apache.ignite.services.ServiceContext
import org.apache.ignite.Ignite
import org.apache.ignite.IgniteQueue
import org.apache.ignite.cache.query.ContinuousQuery
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

	override fun init(ctx: ServiceContext?) {

	}

	override fun cancel(ctx: ServiceContext?) {

	}

	override fun execute(ctx: ServiceContext?) {
		var query = ContinuousQuery<Long, Block>()

		query.setLocalListener { list ->
			list.forEach { pair ->
				val block = pair.value

				val results = CompletableFuture.allOf(
					*block.transactions.map {
						DataStore.ignite.compute().affinityCallAsync(DataStore.balances.name, it.key) {
							var current = BigInteger.ZERO
							if(DataStore.balances.containsKey(it.key)){
								current = DataStore.balances.get(it.key)
							}

							for (transfer in it.value.transfers) {
								if( block.transactions.containsKey(transfer.address) ) {
//									transfer.state = TransferState.Failed
									continue
								}

								if(current >= transfer.value) {
									current -= transfer.value

									if(DataStore.balances.containsKey(transfer.address)) {
										val balance = DataStore.balances.get(transfer.address)
										DataStore.balances.replace(transfer.address, balance, balance + transfer.value)
									}else{
										DataStore.balances.put(transfer.address, transfer.value)
									}

								}else{
//									transfer.state = TransferState.Failed
									break;
								}
							}

							println("Transaction: ${it.key}")
						}.toCompletableFuture()
					}.toTypedArray()
				)

			}
		}

		DataStore.blocks.query(query)
	}
}
package Data

import java.math.BigInteger

enum class TransferState {
	Pending,
	Finished,
	Failed
}

data class Transfer(
	val address: String,
	val value: BigInteger,
	var state: TransferState
)
data class Transaction(val sender: String, val transfers: List<Transfer>, val blockNumber: Int)
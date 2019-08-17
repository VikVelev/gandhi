package Data

import java.math.BigInteger

enum class TransferState {
	Pending,
	Finished,
	Failed
}

data class Transfer(
	val address: Address,
	val value: BigInteger,
	var state: TransferState
)
data class Transaction(val sender: Address, val transfers: List<Transfer>, val blockNumber: Int)
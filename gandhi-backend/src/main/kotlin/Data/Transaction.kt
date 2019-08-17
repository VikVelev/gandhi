package Data

import java.math.BigInteger


data class Transaction(val sender: Address, val transfers: List<Pair<Address, BigInteger>>, val blockNumber: Int)
package Data

data class Transaction(val sender: Address, val transfers: List<Tuple<Address, BigInt>, val blockNumber: Int)
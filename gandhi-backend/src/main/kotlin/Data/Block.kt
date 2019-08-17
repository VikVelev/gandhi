package Data

import java.util.Date

data class Block(val number: Long, val timestamp: Date, val author: Address, val transactions: Map<Address, Transaction>)

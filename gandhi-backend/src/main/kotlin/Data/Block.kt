package Data

import java.util.Date

data class Block(val number: Int, val timestamp: Date, val author: Address, val transactions: Map<Address, Transaction>)

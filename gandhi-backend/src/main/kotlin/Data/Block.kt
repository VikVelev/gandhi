package Data

import java.util.Date

data class Block(val number: Long, val timestamp: Long, val author: String, val transactions: Map<String, Transaction>)

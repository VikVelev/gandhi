package Data

import java.util.Date

data class Block(var number: Long, var timestamp: Long, val author: String, val transactions: Map<String, Transaction>)

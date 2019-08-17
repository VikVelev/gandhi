const axios = require('axios')

NUM_ACCOUNTS=50

block_number=0

function transfers_to_transactions(transfers, sender) {
	return {[sender]: { "sender": sender, "transfers": transfers } }
}

function transactions_to_block(transactions) {
	return {"timestamp": 0, "number": 0, "author": "pesho", "transactions": transactions}
}

function pesho_to_all() {
	transfers = []
	for(i=0; i<NUM_ACCOUNTS; i++) {
		transfers.push({"address": "ACCOUNT"+i, "value": 1, "state": 0})
	}

	return transactions_to_block(transfers_to_transactions(transfers, "pesho"))
}


function all_to_pesho() {
	transfers = []

	

}

axios.post('http://127.0.0.1:8080/blocks/submit', pesho_to_all())
.then((res) => {
  console.log(`statusCode: ${res.statusCode}`)
  console.log(res)
})
.catch((error) => {
  console.error(error)
})

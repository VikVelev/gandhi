const axios = require('axios')


block = {
	'number': 1,
	'timestamp': '1566037695',
	'author': "pesho",
	'transactions': {
		"pesho": {
			"sender": "pesho",
			"transfers": [
				{
					"address": "gosho",
					"value": 12,
					"state": 0
				}
			],
			
		}
	}
}


axios.post('http://127.0.0.1:8080/blocks/submit', block)
.then((res) => {
  console.log(`statusCode: ${res.statusCode}`)
  console.log(res)
})
.catch((error) => {
  console.error(error)
})

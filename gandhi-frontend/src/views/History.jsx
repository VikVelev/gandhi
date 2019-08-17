import React, { Component } from 'react';
import TransactionItem from '../components/Transaction'
import axios from "axios"


class History extends Component {
    state = { 
        transactions: [
            {
                from: "#########################",
                to: "#########################",
                amount: 1
            },
            
            {
                from: "#########################",
                to: "#########################",
                amount: 1
            },
            {
                from: "#########################",
                to: "#########################",
                amount: 1
            },
            {
                from: "#########################",
                to: "#########################",
                amount: 1
            },
            {
                from: "#########################",
                to: "#########################",
                amount: 1
            }
        ]
         
    }

    constructor(props) {
        super(props)

        setTimeout(async () => {
            let { data } = await axios.get("http://127.0.0.1:8080/transactions/latest/25")
            this.setState({ transactions: data })
        }, 0)

        //transaction = Query here
    }

    render() {
        var items = this.state.transactions.map((item, index) => {
            return (
                <TransactionItem key={index} transaction={item}/>
            );
        });

        return (
            <div className="history-transactions"> {items} </div>
        );
    }
}
 
export default History;
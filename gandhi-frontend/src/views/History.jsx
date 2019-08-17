import React, { Component } from 'react';
import TransactionItem from '../components/Transaction'

class History extends Component {
    state = { 
        transactions: [
            {
                from: "Ivan",
                from_address: "0x123asd123123",
                to: "Dimo",
                to_address: "0x41231fhgd",
                amount: 15.2
            }, 
            {
                from: "Pesho",
                from_address: "0x123asd123123",
                to: "Gosho",
                to_address: "0x41231fhgd",
                amount: 69.31
            },
            {
                from: "Trotrlio",
                from_address: "0x123asd123123",
                to: "Tosho",
                to_address: "0x41231fhgd",
                amount: 123.2
            },
            {
                from: "Kradec na kolela",
                from_address: "0x123asd123123",
                to: "Zorko",
                to_address: "0x41231fhgd",
                amount: 899
            }
        ]
         
    }

    constructor(props) {
        super(props)
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
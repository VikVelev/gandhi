import React, { Component } from 'react';
import { Input, Button, Dropdown, Divider, Modal, Header } from 'semantic-ui-react';
import axios from 'axios'
// import { Participants } from '../components/Participants'

const friendOptions = [
    {
      key: 'Jenny Hess',
      text: 'Jenny Hess',
      value: 'Jenny Hess',
      icon: 'user',
    },
    {
      key: 'Elliot Fu',
      text: 'Elliot Fu',
      value: 'Elliot Fu',
      icon: 'user',
    },
    {
      key: 'Stevie Feliciano',
      text: 'Stevie Feliciano',
      value: 'Stevie Feliciano',
      icon: 'user',
    },
    {
      key: 'Christian',
      text: 'Christian',
      value: 'Christian',
      icon: 'user',
    },
    {
      key: 'Matt',
      text: 'Matt',
      value: 'Matt',
      icon: 'user',
    },
    {
      key: 'Justen Kitsune',
      text: 'Justen Kitsune',
      value: 'Justen Kitsune',
      icon: 'user',
    },
]



function transfers_to_transactions(transfers, sender) {
    return {[sender]: { "sender": sender, "transfers": transfers } }
}

function transactions_to_block(transactions) {
    return {"timestamp": 0, "number": 0, "author": "pesho", "transactions": transactions}
}

function pesho_to_all(num_of_accounts) {
    let transfers = []
    for(let i=0; i<num_of_accounts; i++) {
        transfers.push({"address": "ACCOUNT"+i, "value": 1, "state": 0})
    }

    return transactions_to_block(transfers_to_transactions(transfers, "pesho"))
}

function parseBlock(block) {

}

class Home extends Component {
    

    state = {
        transaction_one_from_name: "",
        transaction_one_from_address: "",
        transaction_one_to_name: "",
        transaction_one_to_address: "",
        transaction_one_amount: "",
        transaction_two_from_name: "",
        transaction_two_from_address: "",
        transaction_two_to_name: "",
        transaction_two_to_address: "",
        transaction_two_amount: "",
        transaction_three_from_name: "",
        transaction_three_from_address: "",
        transaction_three_to_name: "",
        transaction_three_to_address: "",
        transaction_three_amount: "",
        sum: 0,
    }

    handleChange = (e, { name, value }) => {
        this.setState({ [name] : value })
	}

    state = { open: false }

    show = dimmer => () => this.setState({ dimmer, open: true })
    close = () => this.setState({ open: false })

    onResolve() {
        this.setState({ sum: (
                parseFloat(this.state.transaction_one_amount) + 
                parseFloat(this.state.transaction_two_amount) + 
                parseFloat(this.state.transaction_three_amount)
            )
        })
        
        let block = {
            transactions: [
                {
                    from: this.state.transaction_one_from_name,
                    //from_address: this.state.transaction_one_from_address,
                    to: this.state.transaction_one_to_name,
                    //to_address: this.state.transaction_one_to_address,
                    amount: this.state.transaction_one_amount
                },
                {
                    from: this.state.transaction_two_from_name,
                    //from_address: this.state.transaction_two_from_address,
                    to: this.state.transaction_two_to_name,
                    //to_address: this.state.transaction_two_to_address,
                    amount: this.state.transaction_two_amount
                },
                {
                    from: this.state.transaction_three_from_name,
                    //from_address: this.state.transaction_three_from_address,
                    to: this.state.transaction_three_to_name,
                    //to_address: this.state.transaction_three_to_address,
                    amount: this.state.transaction_three_amount
                },
            ],
        }
        
        //Send query here
        console.log(block)

        //Enable the modal
        axios.post('http://127.0.0.1:8080/blocks/submit', parseBlock(block)).then(
            (res) => {
                console.log(`statusCode: ${res.statusCode}`)
                this.show('blurring')()
        }).catch((error) => {
            console.error(error)
        })
        
    }

    render() {
        const { open, dimmer } = this.state

        return (
            <div className="home">
                <div className="list-item">
                    From:
                    <Dropdown
                        className="sender item-el"
                        placeholder="Select a sender 1" 
                        fluid 
                        selection 
                        options={friendOptions}
                        onChange={this.handleChange}
                        name="transaction_one_from_name"
                        value={this.state.transaction_one_from_name}
                    />
                    To:
                    <Dropdown 
                        className="receiver item-el" 
                        placeholder="Select a receiver 1" 
                        fluid 
                        selection 
                        options={friendOptions} 
                        onChange={this.handleChange}
                        name="transaction_one_to_name"
                        value={this.state.transaction_one_to_name}
                    />
                    $
                    <Input 
                        className="amount item-el"
                        onChange={this.handleChange}
                        name="transaction_one_amount"
                        placeholder="$"
                        value={this.state.transaction_one_amount}
                    />
                    {/* <Button type="button" className="close" icon="close" circular onClick={this.onClickClose.bind(this)}/> */}
                    <Divider/>                
                </div>
                <div className="list-item">
                    From:
                    <Dropdown
                        className="sender item-el"
                        placeholder="Select a sender 2" 
                        fluid 
                        selection 
                        options={friendOptions} 
                        onChange={this.handleChange}
                        name="transaction_two_from_name"
                        value={this.state.transaction_two_from_name}
                    />
                    To:
                    <Dropdown 
                        className="receiver item-el" 
                        placeholder="Select a receiver 2" 
                        fluid 
                        selection 
                        options={friendOptions} 
                        onChange={this.handleChange}
                        name="transaction_two_to_name"
                        value={this.state.transaction_two_to_name}

                    />
                    <Input 
                        name="transaction_two_amount"
                        placeholder="$"
                        className="amount item-el"
                        onChange={this.handleChange}
                        value={this.state.transaction_two_amount}
                    />
                    {/* <Button type="button" className="close" icon="close" circular onClick={this.onClickClose.bind(this)}/> */}
                    <Divider/>                
                </div>
                <div className="list-item">
                    From:
                    <Dropdown
                        className="sender item-el"
                        placeholder="Select a sender 3"
                        fluid
                        selection
                        options={friendOptions}
                        onChange={this.handleChange}
                        name="transaction_three_from_name"
                        value={this.state.transaction_three_from_name}
                    />
                    To:
                    <Dropdown 
                        className="receiver item-el" 
                        placeholder="Select a receiver 3" 
                        fluid 
                        selection 
                        options={friendOptions} 
                        onChange={this.handleChange}
                        name="transaction_three_to_name"
                        value={this.state.transaction_three_to_name}
                    />
                    $
                    <Input 
                        className="amount item-el"
                        onChange={this.handleChange}
                        name="transaction_three_amount"
                        placeholder="$"
                        value={this.state.transaction_three_amount}

                    />
                    <Divider/>
                </div>

                <Button 
                    onClick={this.onResolve.bind(this)} 
                    color="green"
                    type="submit">
                        Resolve Payment
                </Button>

                <Modal dimmer={dimmer} open={open} onClose={this.close}>
                    <Modal.Header>Response</Modal.Header>
                    <Modal.Content image>
                        <Modal.Description>
                            <Header>Transactions Processed Successfully
                            </Header>
                        </Modal.Description>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button
                            positive
                            icon='checkmark'
                            labelPosition='right'
                            content="Awesome"
                            onClick={this.close}
                        />
                    </Modal.Actions>
                </Modal>
            </div>
        );
    }
}

export default Home;
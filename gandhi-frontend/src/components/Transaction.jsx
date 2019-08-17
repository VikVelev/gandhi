import React, { Component } from 'react';
import { Segment, Grid, Form, Button, Divider, Icon } from 'semantic-ui-react';


class TransactionItem extends Component {
    render() { 
        return (
            <Segment placeholder className="history_transaction">
                <Grid columns={2} relaxed='very' className="history_transaction_column">
                    <Grid.Column>
                        <Icon size="huge" name="user" className="item-el history_transaction_column"/>
                        <div>
                            {this.props.transaction.from}
                        </div>
                    </Grid.Column>

                    <Grid.Column className="history_transaction_column">
                        <Icon size="huge" name="user" className="item-el"/>
                        <div className="item-el">
                            {this.props.transaction.to}
                        </div>
                    </Grid.Column>
                </Grid>
                    
                <Divider vertical>Sent {this.props.transaction.amount} Coins to</Divider>
            </Segment>
        );
    }
}
 
export default TransactionItem;
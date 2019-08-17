import React, { Component } from 'react';
import { Input, Button, Dropdown, Divider } from 'semantic-ui-react';




export class Participants extends Component {
    render() {
        var items = this.props.items.map((item, index) => {
            return (
                <ParticipantListItem
                    handleChange={this.props.onChange}
                    key={index}
                    item={item}
                    index={index}
                    removeItem={this.props.removeItem}
                />
            );
        });
        return (
            <div className="list-group"> {items} </div>
        );
    }
}


export class ParticipantListItem extends Component {
    
    onClickClose() {
        var index = parseInt(this.props.index);
        this.props.removeItem(index);
    }

    render() {
        return (
            <div className="list-item">
                From:
                <Dropdown
                    className="sender item-el" 
                    placeholder="Select a sender" 
                    fluid 
                    selection 
                    options={friendOptions} 
                    onChange={this.props.handleChange}
                />
                To:
                <Dropdown 
                    className="receiver item-el" 
                    placeholder="Select a receiver" 
                    fluid 
                    selection 
                    options={friendOptions} 
                    onChange={this.props.handleChange}
                />
                $
                <Input 
                    className="amount item-el"
                    onChange={this.props.handleChange}
                />
                {/* <Button type="button" className="close" icon="close" circular onClick={this.onClickClose.bind(this)}/> */}
                <Divider/>                
            </div>
        );
    }
}
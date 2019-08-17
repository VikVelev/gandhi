import React, { Component } from 'react';
import { Menu } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import "./Navbar.css"


class Navbar extends Component {
    
    state = {
        activeItem: 'home'
    }

    handleItemClick = (e, { name }) => this.setState({ activeItem: name })
    
    render() {
        const { activeItem } = this.state
        
        return (
            <Menu inverted>
                <Menu.Item
                    // position='left'
                    as={Link}
                    to="/"
                    name='home'
                    active={activeItem === 'home'}
                    onClick={this.handleItemClick}
                />

                <Menu.Item
                    as={Link}
                    to="/history"
                    name='history'
                    active={activeItem === 'history'}
                    onClick={this.handleItemClick}
                />

                <Menu.Item
                    as={Link}
                    to="/friends"
                    name='friends'
                    active={activeItem === 'friends'}
                    onClick={this.handleItemClick}
                />
          </Menu>
        );
    }
}
 
export default Navbar;
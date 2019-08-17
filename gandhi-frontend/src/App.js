import React from 'react';
import { Route, BrowserRouter as Router } from 'react-router-dom'

import Navbar from './components/Navbar'

import Home from './views/Home'
import History from './views/History'

import 'semantic-ui-css/semantic.min.css'


function App() {
	return (
		<Router className="App">
			<div>
				<Navbar/>
				<Route path="/" exact component={Home} />
				<Route path="/history/" component={History} />
			</div>
    	</Router>
	);
}

export default App;

import React from 'react';
import {Switch, Route, BrowserRouter} from 'react-router-dom';

import Home from "./home/Home";
import './app.css';

export function App() {
  return (
    <BrowserRouter>
        <div className="App">
            <header className="App-header">
                <Switch>
                    <Route path="/" exact component={Home}/>
                </Switch>
            </header>
        </div>
    </BrowserRouter>
  );
}

export default App;

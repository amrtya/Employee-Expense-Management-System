<<<<<<< HEAD
import './App.css';
import { BrowserRouter } from 'react-router-dom';
import Layout from './containers/Layout/Layout';

function App() {
    return (
      <BrowserRouter>
        <div className="App">
            <Layout />
        </div>
      </BrowserRouter>
    );
=======
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
>>>>>>> af1c72483de0ead0f7f4a1839628505d21f7fd72
}

export default App;

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import Campaigns from './components/Campaigns';
import Sellers from './components/Sellers';

function App() {
  return (
    <Router>
      <div className="container mt-4">
        <nav className="nav nav-tabs mb-4">
          <Link className="nav-link" to="/campaigns">Campaigns</Link>
          <Link className="nav-link" to="/sellers">Sellers</Link>
        </nav>
        
        <Routes>
          <Route path="/campaigns" element={<Campaigns />} />
          <Route path="/" element={<Campaigns />} />
          <Route path="/sellers" element={<Sellers />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

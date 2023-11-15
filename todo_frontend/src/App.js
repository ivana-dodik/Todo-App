import Sidebar from './components/Sidebar';
import Main from './components/Main';
import './App.css';
import { useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [selectedCategoryId, setSelectedCategoryId] = useState(1);

  return (
    <div
      className="App"
      style={{
        backgroundImage: `url(${process.env.PUBLIC_URL}/tree.jpg)`,
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        height: '100vh',
      }}
    >
      <Container style={{ height: "100%" }}>
        <Row style={{ height: "100%" }}>
          <Col xs={4}>
            <Sidebar onCategorySelect={setSelectedCategoryId} />
          </Col>
          <Col xs={8}>
            <Main selectedCategoryId={selectedCategoryId} />
          </Col>
        </Row>
      </Container>
    </div>
  );


}

export default App;

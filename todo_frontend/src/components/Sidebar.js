import './sidebar.css';
import { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import Category from './Category';

function Sidebar(props) {
  const [categories, setCategories] = useState([]);
  const [newCategoryName, setNewCategoryName] = useState('');

  const [selectedCategoryId, setSelectedCategoryId] = useState(null);

  const handleCategorySelect = (categoryId) => {
    setSelectedCategoryId(categoryId);
    props.onCategorySelect(categoryId);
  };


  useEffect(() => {
    fetch('/api/categories')
      .then(response => response.json())
      .then(data => {
        setCategories(data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  const handleAddCategory = (event) => {
    event.preventDefault();

    fetch('/api/categories', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ name: newCategoryName }),
    })
      .then(response => response.json())
      .then(data => {
        setCategories([...categories, data]);
        setNewCategoryName('');
      })
      .catch(error => {
        console.error(error);
      });
  };

  const handleNewCategoryNameChange = (event) => {
    setNewCategoryName(event.target.value);
  };

  const handleDeleteCategory = (categoryId) => {
    setCategories(categories.filter(category => category.id !== categoryId));
  };

  const handleEditCategory = (updatedCategory) => {
    setCategories(categories.map(category => {
      if (category.id === updatedCategory.id) {
        return updatedCategory;
      } else {
        return category;
      }
    }));
  };

  return (
    <div className="sidebar">
      <Container>
        <h2>Categories</h2>
        <Row className="mb-3 align-items-center">
          <Col md={9}>
            <Form.Control
              type="text"
              value={newCategoryName}
              onChange={handleNewCategoryNameChange}
              placeholder='Add new category'
            />
          </Col>
          <Col md={3}>
            <Button variant="primary" type="submit" onClick={handleAddCategory}>
              Add
            </Button>
          </Col>
        </Row>
      </Container>

      <ul>
        {categories.map(category => (
          <li
            key={category.id}
            onClick={() => handleCategorySelect(category.id)}
            className={selectedCategoryId === category.id ? 'selected' : ''}
          >
            <Category category={category} onDelete={handleDeleteCategory} onEdit={handleEditCategory} />
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Sidebar;


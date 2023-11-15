import React, { useState } from 'react';
import { Button, Form, Row, Col } from 'react-bootstrap';
import {FaTrash, FaEdit} from 'react-icons/fa'

function Category(props) {
  const { category, onDelete, onEdit } = props;
  const [isEditing, setIsEditing] = useState(false);
  const [newName, setNewName] = useState(category.name);

  const handleDeleteCategory = () => {
    fetch(`/api/categories/${category.id}`, {
      method: 'DELETE',
    })
      .then(() => {
        onDelete(category.id);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const handleEditCategory = () => {
    setIsEditing(true);
  };

  const handleSaveCategory = () => {
    fetch(`/api/categories/${category.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ name: newName }),
    })
      .then(response => response.json())
      .then(data => {
        onEdit(data);
        setIsEditing(false);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const handleCancelEdit = () => {
    setNewName(category.name);
    setIsEditing(false);
  };

  const handleNewNameChange = (event) => {
    setNewName(event.target.value);
  };

  return (
    <div className="category">
      {isEditing ? (
        <Form>
          <Form.Group>
            <Form.Control
              type="text"
              value={newName}
              onChange={handleNewNameChange}
            />
          </Form.Group>
          <Button variant="primary" onClick={handleSaveCategory}>Save</Button>{' '}
          <Button variant="secondary" onClick={handleCancelEdit}>Cancel</Button>
        </Form>
      ) : (
        <>
          <Row className="align-items-center">
            <Col md={8} className="category-name">{category.name}</Col>
            <Col md={4} className="category-buttons">
              <Button variant="danger" onClick={handleDeleteCategory}><FaTrash /></Button>{' '}
              <Button variant="primary" onClick={handleEditCategory}><FaEdit /></Button>
            </Col>
          </Row>
        </>
      )}
    </div>
  );
}

export default Category;

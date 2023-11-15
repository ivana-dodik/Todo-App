import React, { useState } from "react";
import { Button, Form, Row, Col } from 'react-bootstrap';
import { FaTrash, FaEdit } from 'react-icons/fa'

// Define task component
function Task(props) {
  const task = props.task;
  const onEdit = props.onEdit;

  const { id, name, priority, due, completed } = props.task;
  const [isEditing, setIsEditing] = useState(false);
  const [editedTask, setEditedTask] = useState(task);

  const priorityClass = `priority-${priority.toLowerCase()}`;
  const daysUntilDue = Math.ceil((new Date(due) - new Date()) / (1000 * 60 * 60 * 24));

  const formattedDate = new Date(due).toLocaleString('en-US', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: 'numeric',
    minute: 'numeric',
  });

  const [date, time] = formattedDate.split(', ');
  const [month, day, year] = date.split('/');
  const [hour, minute] = time.split(':');

  const formattedDateString = `${month}.${day}.${year} at ${hour}:${minute}`;


  const dueClass = daysUntilDue > 7 ? 'due-grey' :
    daysUntilDue > 1 ? `due-${daysUntilDue}` :
      'due-red';
  const taskClass = `task ${priorityClass} ${dueClass} ${completed ? 'completed' : ''}`;

  const handleDelete = () => {
    props.onDelete(id);
  };

  const handleTaskToggle = (id) => {
    props.handleTaskToggle(id);
  }

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setEditedTask(task);
  };

  const handleSave = () => {
    onEdit(editedTask);
    setIsEditing(false);
  };

  const handleNameChange = (event) => {
    setEditedTask({ ...editedTask, name: event.target.value });
  };

  const handleDateChange = (event) => {
    setEditedTask({ ...editedTask, due: event.target.value });
  };

  const handlePriorityChange = (event) => {
    setEditedTask({ ...editedTask, priority: event.target.value });
  };

  if (isEditing) {
    return (
      <div className="task">
        <Form onSubmit={handleSave}>
          <Form.Group controlId="editedTaskName">
            <Form.Control type="text" value={editedTask.name} onChange={handleNameChange} />
          </Form.Group>
          <Form.Group controlId="editedTaskDue">
            <Form.Label>Due Date:</Form.Label>
            <Form.Control type="datetime-local" value={editedTask.due} onChange={handleDateChange} />
          </Form.Group>
          <Form.Group controlId="editedTaskPriority">
            <Form.Label>Priority:</Form.Label>
            <Form.Control as="select" value={editedTask.priority} onChange={handlePriorityChange}>
              <option value="DEFAULT">Default</option>
              <option value="LOW">Low</option>
              <option value="MEDIUM">Medium</option>
              <option value="HIGH">High</option>
            </Form.Control>
          </Form.Group>
          <br />
          <Button variant="primary" type="submit">Save</Button>{' '}
          <Button variant="secondary" onClick={handleCancel}>Cancel</Button>
        </Form>
      </div>
    );
  } else {
    return (
      <div className={taskClass}>
        <Row className="align-items-center">
          <Col md={1}>
            <Form.Check
              type="checkbox"
              checked={completed}
              onChange={() => handleTaskToggle(id)}
            />
          </Col>
          <Col md={7}>
            <span className="task-name">{name}&nbsp;</span>
            {priority !== 'DEFAULT' && <span className="task-priority"> {priority}&nbsp;</span>}
            {due && <span className="task-due"> Due: {formattedDateString}</span>}
          </Col>
          <Col md={4} className="d-flex justify-content-end">
            <Button variant="primary" size="sm" onClick={handleEdit}><FaEdit /></Button>&nbsp;
            <Button variant="danger" size="sm" onClick={handleDelete}><FaTrash /></Button>
          </Col>
        </Row>
      </div>
    );
  }
}

export default Task;

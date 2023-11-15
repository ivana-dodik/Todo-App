import React from 'react'
import Task from './Task';
import './main.css'
import { useState, useEffect } from 'react';
import { Form, Button, Row, Col } from 'react-bootstrap';

// Define main component
function Main(props) {
    const [tasks, setTasks] = useState([]);
    const [taskName, setTaskName] = useState('');
    const [taskPriority, setTaskPriority] = useState('DEFAULT');

    useEffect(() => {
        let selectedCategoryId = props.selectedCategoryId;
        let url = `/api/categories/${selectedCategoryId}/tasks`;

        if (selectedCategoryId) {
            url += '?categoryId=' + selectedCategoryId;
        }
        fetch(url)
            .then(response => response.json())
            .then(data => {
                setTasks(data);
                setTaskName('');
                setTaskPriority('DEFAULT');
            })
            .catch(error => {
                console.error(error);
            });
    }, [props.selectedCategoryId]);

    const handleTaskAdd = () => {
        let selectedCategoryId = props.selectedCategoryId;

        const newTask = {
            name: taskName,
            priority: taskPriority,
            completed: false
        };

        fetch(`/api/categories/${selectedCategoryId}/tasks`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newTask),
        })
            .then(response => response.json())
            .then(data => {
                setTasks([...tasks, data]);
                setTaskName('');
                setTaskPriority('DEFAULT');
            })
            .catch(error => {
                console.error(error);
            });
    };

    const handleTaskNameChange = (event) => {
        setTaskName(event.target.value);
    };

    const handleTaskPriorityChange = (event) => {
        setTaskPriority(event.target.value);
    };

    const handleTaskDelete = (taskId) => {
        fetch(`/api/tasks/${taskId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.status === 204) {
                    setTasks(tasks.filter(task => task.id !== taskId));
                }
            })
            .catch(error => {
                console.error(error);
            });
    }

    const handleTaskToggle = (id) => {
        fetch(`/api/tasks/${id}/toggle-complete`, {
            method: 'PATCH'
        })
            .then(response => response.json())
            .then(data => {
                const updatedTasks = tasks.map(task => {
                    if (task.id === data.id) {
                        return data;
                    }
                    return task;
                });
                updatedTasks.sort((a, b) => {
                    if (a.completed === b.completed) {
                        return a.name.localeCompare(b.name);
                    }
                    return a.completed ? 1 : -1;
                });
                setTasks(updatedTasks);
            })
            .catch(error => {
                console.error(error);
            });
    };


    const handleTaskEdit = (editedTask) => {
        console.log(editedTask);
        fetch(`/api/tasks/${editedTask.id}`,
            {
                method: "PUT",
                body: JSON.stringify(editedTask),
                headers: {
                    'Content-Type': 'application/json',
                },
            })
            .then((response) => response.json())
            .then((updatedTask) => {
                console.log(updatedTask);
                const updatedTasks = tasks.map((task) => {
                    if (task.id === updatedTask.id) {
                        return updatedTask;
                    } else {
                        return task;
                    }
                });
                setTasks(updatedTasks);
            });
    };

    return (
        <div className="main">
            <Row className="mb-2">
                <Col md={8}>
                    <Form.Control type="text" placeholder="Enter new task" value={taskName} onChange={handleTaskNameChange} />
                </Col>
                <Col md={2}>
                    <Form.Select value={taskPriority} onChange={handleTaskPriorityChange}>
                        <option value="DEFAULT">DEFAULT</option>
                        <option value="LOW">LOW</option>
                        <option value="MEDIUM">MEDIUM</option>
                        <option value="HIGH">HIGH</option>
                    </Form.Select>
                </Col>
                <Col>
                    <Button onClick={() => handleTaskAdd()} className="w-100">Add</Button>
                </Col>
            </Row>
            <div className="task-list">
                {tasks.map((task) => (
                    <Task key={task.id} task={task} onEdit={handleTaskEdit} onDelete={handleTaskDelete} handleTaskToggle={handleTaskToggle} />
                ))}
            </div>
        </div>
    );
}

export default Main;
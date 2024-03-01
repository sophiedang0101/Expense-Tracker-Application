import { useLocation } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import Stack from 'react-bootstrap/Stack';
import { Link } from 'react-router-dom';
import { useState } from 'react';

const EditForm = () => {
  const location = useLocation();
  const expense = location.state;
  const [backupState, setBackupState] = useState({ ...expense });
  const [newState, setNewState] = useState({ ...expense });


  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewState((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleCategorySelect = (categoryId) => {
    setNewState((prevData) => ({ ...prevData, categoryId }));
  };



  const handleSave = () => {


    newState.cost = Number(newState.cost)
    newState.categoryId = Number(newState.categoryId)
    console.log("inside save BUTTON: ", newState)

    fetch(`http://localhost:8080/api/edit/${newState.expenseId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newState),
    })
      .then((res) => {
        if (res.ok) {
  
          console.log('Expense added successfully!');
        } else {
          console.error('Error adding expense:', res.statusText);
        }
      })
      .catch((error) => {
        console.error('Network error:', error.message);
      });
  };

  const handleReset = () => {
    setNewState({ ...backupState });
  };

  return (
    <div>
      <div className="text-center mb-5">
        <h2> Edit Expense {`#${backupState.expenseId}: ${backupState.expenseDescription}`}</h2>
      </div>

      <div className="d-flex flex-column align-items-center">
        <Form style={{ width: '40%' }}>
          <Form.Group controlId="expenseDescription" className="mb-2">
            <Form.Label>Description:</Form.Label>
            <Form.Control
              type="text"
              name="expenseDescription"
              value={newState.expenseDescription}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="vendor" className="mb-2">
            <Form.Label>Vendor:</Form.Label>
            <Form.Control
              type="text"
              name="vendor"
              value={newState.vendor}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="cost" className="mb-2">
            <Form.Label>Amount:</Form.Label>
            <Form.Control
              type="text"
              name="cost"
              value={newState.cost}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="expenseDate" className="mb-3">
            <Form.Label>Date:</Form.Label>
            <Form.Control
              type="text"
              name="expenseDate"
              placeholder="YYYY-MM-DD"
              value={newState.expenseDate}
              onChange={handleChange}
            />
          </Form.Group>
          <Dropdown onSelect={handleCategorySelect} className="mb-3">
            <Dropdown.Toggle variant="success" id="dropdown-basic">
              Category
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item eventKey="1">Office Supplies</Dropdown.Item>
              <Dropdown.Item eventKey="2">Utilities</Dropdown.Item>
              <Dropdown.Item eventKey="3">Rent/Lease</Dropdown.Item>
              <Dropdown.Item eventKey="4">Insurance</Dropdown.Item>
              <Dropdown.Item eventKey="5">Inventory</Dropdown.Item>
              <Dropdown.Item eventKey="6">Maintenance/Repair</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>

          <Form.Group className="mb-3" controlId="notes">
            <Form.Label>Notes:  {<i>(optional)</i>}</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              type="text"
              name="notes"
              value={newState.notes}
              onChange={handleChange}
            />
          </Form.Group>

          <Stack direction="horizontal" gap={3}>
            <Link to="/viewAll" className="ms-auto">
              <Button  variant="primary" onClick={handleSave}>
                Save
              </Button>
            </Link>
            <Button variant="secondary" onClick={handleReset}>
              Reset
            </Button>
            <Link to="/viewAll">
              <Button variant="danger">Cancel</Button>
            </Link>
          </Stack>
        </Form>
      </div>
    </div>
  );
};

export default EditForm;

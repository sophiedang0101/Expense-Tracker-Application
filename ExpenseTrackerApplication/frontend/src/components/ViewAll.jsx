import { useEffect, useState } from 'react';
import { Table, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Stack from 'react-bootstrap/Stack';

const ViewAll = () => {
  const [expenses, setExpenses] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/expenses');
        const data = await response.json();
        setExpenses(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [expenses]); // runs only once on mount

  const handleDelete = (expenseId) => {
    // Delete request
    fetch(`http://localhost:8080/api/delete/${expenseId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        if (res.ok) {
          setExpenses(() =>
            expenses.filter((expense) => expense.expenseId !== expenseId)
          ); //causes a remount
        } else {
          console.error('Error adding expense:', res.statusText);
        }
      })
      .catch((error) => {
        console.error('Network error:', error.message);
      });
  };

  const handleEdit = (expenseId) => {
    console.log(`Editing expense with ID ${expenseId}`);
  };

  return (
    <div style={{ width: '100%', marginLeft: 20, marginRight:20 }}>
      <div className="text-center mb-5">
        <h2>View All Expenses</h2>
      </div>
      <Stack direction="horizontal" gap={3}>
        <Link className="ms-auto mb-3" to="/">
          <Button variant="primary">Back to Dashboard</Button>
        </Link>
      </Stack>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Expense ID</th>
            <th>Description</th>
            <th>Cost</th>
            <th>Expense Date</th>
            <th>Vendor</th>
            <th>Notes</th>
            <th>Category ID</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((expense) => (
            <tr key={expense.expenseId}>
              <td>{expense.expenseId}</td>
              <td>{expense.expenseDescription}</td>
              <td>${expense.cost}</td>
              <td>{expense.expenseDate}</td>
              <td>{expense.vendor}</td>
              <td>{expense.notes}</td>
              <td>{expense.categoryId}</td>
              <td>
                <Link to="/editForm" state={expense}>
                  <Button
                    variant="info"
                    onClick={() => handleEdit(expense.expenseId)}>
                    Edit
                  </Button>
                </Link>{' '}
                <Button
                  variant="danger"
                  onClick={() => handleDelete(expense.expenseId)}>
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Stack direction="horizontal" gap={3}>
        <Link className="ms-auto" to="/">
          <Button variant="primary">Back to Dashboard</Button>
        </Link>
      </Stack>
    </div>
  );
};

export default ViewAll;

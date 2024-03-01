import { useEffect, useState } from 'react';
import { Table } from 'react-bootstrap';



const RecentExpenses= () => {
  const [expenses, setExpenses] = useState([])

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
  }, []); // runs only once on mount

  
  return (
    <div style={{ width: '100%', marginLeft: 20, marginRight:20 }}>
      <div className="text-center mb-2" >
        <h4>Recent Expenses</h4>
    
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
   
            <th>Description</th>
            <th>Cost</th>
            <th>Expense Date</th>
            <th>Vendor</th>
            <th>Notes</th>
            <th>Category ID</th>
      
          </tr>
        </thead>
        <tbody>
          {expenses.slice(expenses.length-4).reverse().map((expense) => (
            <tr key={expense.expenseId}>
              
              <td>{expense.expenseDescription}</td>
              <td>${expense.cost}</td>
              <td>{expense.expenseDate}</td>
              <td>{expense.vendor}</td>
              <td>{expense.notes}</td>
              <td>{expense.categoryId}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default RecentExpenses;

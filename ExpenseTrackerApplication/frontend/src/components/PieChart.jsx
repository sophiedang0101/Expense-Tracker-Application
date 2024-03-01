import { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { ArcElement, Chart as ChartJS } from 'chart.js';

ChartJS.register(ArcElement);
// import {
//   ArcElement,
//   CategoryScale,
//   Chart as ChartJS,
//   Legend,
//   LinearScale,
//   Title,
//   Tooltip,
// } from 'chart.js';

// ChartJS.register(
//   ArcElement,
//   // CategoryScale,
//   // LinearScale,
//   // Title,
//   // Tooltip,
//   // Legend
// );

const PieChart = () => {
  const [expenses, setExpenses] = useState([]);
  const [categories, setCategories] = useState([
    '1. Office Supplies',
    '2. Utilities',
    '3. Rent/Lease',
    '4. Insurance',
    '5. Inventory',
    '6. Maintenance/Repair',
  ]);

  function calculateCategoryCosts(expenses) {
    const categoryCosts = {};

    expenses.forEach((expense) => {
      const categoryId = expense.categoryId;
      const cost = expense.cost;

      if (categoryId in categoryCosts) {
        categoryCosts[categoryId] += cost;
      } else {
        categoryCosts[categoryId] = cost;
      }
    });

    return Object.values(categoryCosts);
  }

  const myExpenses = calculateCategoryCosts(expenses);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/expenses');
        const data = await response.json();
        console.log;
        setExpenses(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const data = {
    labels: categories,
    datasets: [
      {
        label: '',
        data: myExpenses,
        backgroundColor: ['red', 'orange', 'yellow', 'green', 'blue', 'violet'],
      },
    ],
  };

  return (
    <div style={{ width: 500, height: 300 }}>
      <div className="text-center mb-1">
        <h4> Expenses </h4>
      </div>
      <Pie
        data={data}
        options={{
          maintainAspectRatio: false,
          title: {
            display: true,
          },
        }}
      />
      <div></div>
    </div>
  );
};

export default PieChart;

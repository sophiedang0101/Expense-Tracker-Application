import { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import Stack from "react-bootstrap/Stack";
import { Link } from "react-router-dom";
import { Bar } from "react-chartjs-2";
import {
  BarElement,
  ArcElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  Title,
  Tooltip,
} from "chart.js";

ChartJS.register(
  BarElement,
  ArcElement,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend
);

const Filter = () => {
  const [expenses, setExpenses] = useState([]);
  const [year, setYear] = useState("");
  const [filteredExpenses, setFilteredExpenses] = useState([]);
  const [categories, setCategories] = useState([
    "1. Office Supplies",
    "2. Utilities",
    "3. Rent/Lease",
    "4. Insurance",
    "5. Inventory",
    "6. Maintenance/Repair",
  ]);
  const [data, setData] = useState({
    labels: categories,
    datasets: [
      {
        label: "",
        data: [],
        backgroundColor: ["red", "orange", "yellow", "green", "blue", "violet"],
      },
    ],
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/expenses");
        const data = await response.json();
        setExpenses(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []); // runs only once on mount

  const handleChange = (e) => {
    const { value } = e.target;
    setYear(value);
  };
  function calculateCategoryCosts(expenses) {
    const categoryCosts = {
      0: 0,
      1: 0,
      2: 0,
      3: 0,
      4: 0,
      5: 0,
    };

    expenses.forEach((expense) => {
      const categoryId = expense.categoryId;
      const cost = expense.cost;

      if (categoryId - 1 in categoryCosts) {
        categoryCosts[categoryId - 1] += cost;
      } else {
        categoryCosts[categoryId - 1] = cost;
      }
    });

    // return categoryCosts;
    return Object.values(categoryCosts);
  }
  const handleGet = () => {
    const expensesArr = expenses.filter(
      (expense) =>
        new Date(expense.expenseDate).getFullYear().toString() === year
    );
    setFilteredExpenses(expensesArr);
    console.log("FILTER EXPENSES", expensesArr);
    const newData = calculateCategoryCosts(expensesArr);
    console.log(newData);

    const updatedData = {
      labels: categories,
      datasets: [
        {
          label: "",
          data: newData,
          backgroundColor: "blue",
        },
      ],
    };
    setData(updatedData);
    setYear("");
  };
  return (
    <div>
      <div className="text-center mb-5">
        <h2>Filter Your Expenses by Year</h2>
      </div>
      <div className="d-flex flex-column align-items-center">
        <Form style={{ width: "40%" }}>
          <Form.Group controlId="expenseDate" className="mb-3">
            <Form.Label>Year:</Form.Label>
            <Form.Control
              type="text"
              name="expenseDate"
              placeholder="YYYY"
              value={year}
              onChange={handleChange}
            />
          </Form.Group>

          <Stack direction="horizontal" gap={3}>
            <Button className="ms-auto" variant="primary" onClick={handleGet}>
              Get
            </Button>
            <Link to="/">
              <Button variant="danger">Back</Button>
            </Link>
          </Stack>
        </Form>

        {filteredExpenses.length > 0 && (
          <div className="mt-5">
            <div className="text-center mb-3">
              <h4> Filtered Expenses: </h4>
            </div>
            <Bar
              style={{ width: 700 }}
              data={data}
              // options={{
              //   maintainAspectRatio: false,
              //   title: {
              //     display: true,
              //   },
              // }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default Filter;

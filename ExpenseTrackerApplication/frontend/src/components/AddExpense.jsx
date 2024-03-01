import { useState } from "react";
import { Form, Button } from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import Stack from "react-bootstrap/Stack";
import { Link } from "react-router-dom";

const AddExpense = () => {
  const [selectedCategory, setSelectedCategory] = useState("");

  const [expenseData, setExpenseData] = useState({
    expenseDescription: "",
    vendor: "",
    cost: "",
    notes: "",
    expenseDate: "",
    categoryId: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setExpenseData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleCategorySelect = (categoryId, event) => {
    const categoryLabel = event.target.textContent;
    setSelectedCategory(categoryLabel);
    setExpenseData((prevData) => ({ ...prevData, categoryId }));
  };

  const checkData = (expenseData) => {
    const { expenseDescription, vendor, cost, expenseDate, categoryId } =
      expenseData;
    if (
      !expenseDescription ||
      !vendor ||
      !cost ||
      !expenseDate ||
      !categoryId
    ) {
      alert("Please fill out all the required fields.");
      return false;
    } else return true;
  };

  const handleAdd = () => {
    if (!checkData(expenseData)) {
      console.log("THIS SHOULD BE THE END");
      return;
    }
    expenseData.cost = Number(expenseData.cost);
    expenseData.categoryId = Number(expenseData.categoryId);
    console.log("checking if types are correct:  ", expenseData);
    const { expenseDescription, vendor, cost, expenseDate, categoryId, notes } =
      expenseData;
    fetch("http://localhost:8080/api/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        expenseDescription,
        vendor,
        cost,
        expenseDate,
        categoryId,
        notes,
      }),
    })
      .then((res) => {
        if (res.ok) {
          handleClear();
          console.log("Expense added successfully!");
        } else {
          console.error("Error adding expense:", res.statusText);
        }
      })
      .catch((error) => {
        console.error("Network error:", error.message);
      });
  };

  const handleClear = () => {
    setExpenseData({
      expenseDescription: "",
      vendor: "",
      cost: "",
      notes: "",
      expenseDate: "",
      categoryId: "",
    });
    setSelectedCategory("");
  };

  return (
    <div>
      <div className="text-center mb-5">
        <h2> Add Expense </h2>
      </div>

      <div className="d-flex flex-column align-items-center">
        <Form style={{ width: "40%" }}>
          <Form.Group controlId="expenseDescription" className="mb-2">
            <Form.Label>Description:</Form.Label>
            <Form.Control
              type="text"
              name="expenseDescription"
              value={expenseData.expenseDescription}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="vendor" className="mb-2">
            <Form.Label>Vendor:</Form.Label>
            <Form.Control
              type="text"
              name="vendor"
              value={expenseData.vendor}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="cost" className="mb-2">
            <Form.Label>Amount:</Form.Label>
            <Form.Control
              type="text"
              name="cost"
              value={expenseData.cost}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="expenseDate" className="mb-3">
            <Form.Label>Date:</Form.Label>
            <Form.Control
              type="text"
              name="expenseDate"
              placeholder="YYYY-MM-DD"
              value={expenseData.expenseDate}
              onChange={handleChange}
            />
          </Form.Group>

          <Dropdown onSelect={handleCategorySelect} className="mb-3">
            <Dropdown.Toggle variant="success" id="dropdown-basic">
              {selectedCategory || "Category"}
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
            <Form.Label>Notes: {<i>(optional)</i>}</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              type="text"
              name="notes"
              value={expenseData.notes}
              onChange={handleChange}
            />
          </Form.Group>

          <Stack direction="horizontal" gap={3}>
            <Button className="ms-auto" variant="primary" onClick={handleAdd}>
              Add
            </Button>
            <Button variant="secondary" onClick={handleClear}>
              Clear
            </Button>
            <Link to="/">
              <Button variant="danger">Back</Button>
            </Link>
          </Stack>
        </Form>
      </div>
    </div>
  );
};

export default AddExpense;

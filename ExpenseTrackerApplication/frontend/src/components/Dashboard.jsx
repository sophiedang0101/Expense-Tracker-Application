// Dashboard.js
import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { Link } from "react-router-dom";
import PieChart from "../components/PieChart";
import RecentExpenses from "./RecentExpenses";
import "./Dashboard.css";

const Dashboard = () => {
  return (
    <Container className="Dashboard">
      <div className="text-center mb-5">
        <h2>Dashboard</h2>
      </div>
      <div className="mb-5">
        <Row>
          {/* Left Column */}
          <Col
            md={4}
            className="d-flex flex-column justify-content-center align-items-center"
          >
            <Link to="/Add" className="mb-3">
              <Button variant="primary">Add New Expenses</Button>
            </Link>

            <Link to="/viewAll" className="mb-3">
              <Button variant="primary">View All Expenses</Button>
            </Link>

            <Link to="/filter">
              <Button variant="primary">Filter Expenses</Button>
            </Link>
          </Col>
          {/* Right Column */}
          <Col md={8}>
            <PieChart />
          </Col>
        </Row>
      </div>

      <RecentExpenses />
    </Container>
  );
};

export default Dashboard;

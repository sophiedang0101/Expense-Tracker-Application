DROP DATABASE IF EXISTS expensetracker;

CREATE DATABASE expensetracker;
USE expensetracker;

-- Table for category

CREATE TABLE category(
categoryId INT PRIMARY KEY AUTO_INCREMENT,
categoryName VARCHAR(50)
);

-- Table for expense
  
CREATE TABLE expense (
expenseId INT PRIMARY KEY AUTO_INCREMENT,
expenseDesc VARCHAR(255) NOT NULL,
cost DECIMAL(10,2) NOT NULL,
expenseDate DATE NOT NULL,
categoryId INT,
vendor VARCHAR(50) NOT NULL,
notes VARCHAR(200),
FOREIGN KEY (categoryId) REFERENCES category(categoryId)
);
  
INSERT INTO category (categoryName)
VALUES
("Office Supplies"),
("Utilities"),
("Rent/Lease"),
("Insurance"),
("Inventory"),
("Maintenance/Repairs");

-- SELECT * FROM category;

INSERT INTO expense (expenseDesc, cost, expenseDate, categoryId, vendor, notes)
VALUES
  ('Pens and Paper', 50.00, '2024-01-19', 1, 'OfficeMart', 'Office supplies restock'),
  ('Water Bill', 50.00, '2023-10-31', 2, 'WaterUtility', 'Monthly water bill'),
  ('Electricity Bill', 200.00, '2024-01-15', 2, 'PowerCo', 'Monthly utility payment'),
  ('Office Rent', 1200.00, '2024-01-01', 3, 'Property Management', 'Monthly rent payment'),
  ('Business Insurance', 300.00, '2023-01-10', 4, 'InsureCo', 'Quarterly insurance premium'),
  ('Inventory Purchase', 500.00, '2023-12-05', 5, 'SupplierX', 'Restocking inventory'),
  ('Equipment Repair', 100.00, '2024-01-12', 6, 'TechFix', 'Repair printer'),
  ('Office Chair Purchase', 80.00, '2024-01-08', 6, 'FurnitureWorld', 'New office chair'),
  ('Internet Bill', 80.00, '2024-01-05', 2, 'ISPProvider', 'Monthly internet service payment'),
  ('Air Conditioning Repair', 120.00, '2023-02-12', 6, 'CoolingServices', 'Repair office AC unit'),
  ('Office Printer Purchase', 100.00, '2024-01-11', 1, 'OfficeMart', 'New office printer')
  ;

--   SELECT * FROM expense;
  

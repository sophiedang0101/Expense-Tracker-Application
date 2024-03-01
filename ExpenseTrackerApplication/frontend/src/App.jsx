import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard.jsx';
import AddExpense from './components/AddExpense.jsx';
import ViewAll from './components/ViewAll.jsx';
import EditForm from './components/EditForm.jsx';
import Filter from './components/Filter.jsx';

const App = () => {
  return (
    <div className='App'>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/add" element={<AddExpense />} />
          <Route path="/viewAll" element={<ViewAll />} />
          <Route path="/editForm" element={<EditForm />} />
          <Route path="/filter" element={<Filter />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterPage from '../pages/authentication/register';

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="/auth/register" element={<RegisterPage />} />
      </Routes>
    </Router>
  )
}

export default AppRouter;
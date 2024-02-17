import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterPage from '../pages/authentication/register';
import { ToastContainer } from 'react-toastify';
import LoginPage from '../pages/authentication/login';
import HomePage from '../pages/todos/home';

const AppRouter = () => {
  return (
    <Router>
      <ToastContainer />
      <Routes>
        <Route path="/app/auth/register" element={<RegisterPage />} />
        <Route path="/app/auth/login" element={<LoginPage />} />
        <Route path="/app/home" element={<HomePage />} />
      </Routes>
    </Router>
  )
}

export default AppRouter;
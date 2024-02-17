import { Navigate } from 'react-router-dom';
import { getToken } from '../../core/services/authentication/user-authentication.service';

type AuthenticatedRouteGuardProps = {
  children: React.ReactNode;
  redirectTo: string;
};

const AuthenticatedRouteGuard = ({ children, redirectTo }: AuthenticatedRouteGuardProps) => {
  const isAuthenticated = (): boolean => {
    const token =  getToken();
    return token !== null && token !== undefined && token.trim().length > 0;
  }

  if (!isAuthenticated()) {
    return <Navigate to={redirectTo} replace />;
  }

  return children;
}

export default AuthenticatedRouteGuard;

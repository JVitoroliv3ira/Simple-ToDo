import { ReactNode } from 'react';
import HeaderComponent from '../../header';
import FooterComponent from '../../footer';
import { useNavigate } from 'react-router-dom';
import { removeEmail, removeToken } from '../../../core/services/authentication/user-authentication.service';

type AuthenticatedLayoutProps = {
  children: ReactNode;
};

const AuthenticatedLayout = ({ children }: AuthenticatedLayoutProps) => {
  const navigate = useNavigate();

  const logoff = () => {
    removeToken();
    removeEmail();
    navigate('/app/auth/login')
  }

  return (
    <>
      <HeaderComponent>
        <button
          onClick={logoff}
          className="border-none bg-transparent text-sm font-light"
        >
          Sair
        </button>
      </HeaderComponent>
      <main className="flex items-start justify-center w-full h-screen bg-slate-100 p-10">
        {children}
      </main>
      <FooterComponent />
    </>
  );
}

export default AuthenticatedLayout;

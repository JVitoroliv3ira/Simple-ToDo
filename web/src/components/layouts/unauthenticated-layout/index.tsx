import { ReactNode } from 'react';
import HeaderComponent from '../../header';
import FooterComponent from '../../footer';


type UnauthenticatedLayoutProps = {
  children: ReactNode;
};

const UnauthenticatedLayout = ({ children }: UnauthenticatedLayoutProps) => {
  return (
    <>
      <HeaderComponent>
        <a className="font-light" href="/app/auth/register">Crie sua conta</a>
        <a className="font-light" href="/app/auth/login">Entrar</a>
      </HeaderComponent>
      <main className="flex items-center justify-center w-full h-screen bg-slate-100 p-10">
        {children}
      </main>
      <FooterComponent />
    </>
  );
}

export default UnauthenticatedLayout;

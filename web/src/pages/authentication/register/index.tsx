import React from "react";
import HeaderComponent from "../../../components/header";
import FooterComponent from "../../../components/footer";

const RegisterPage = () => {
  return (
    <div>
      <HeaderComponent>
        <a className="font-light" href="/app/auth/register">Crie sua conta</a>
        <a className="font-light" href="/app/auth/login">Entrar</a>
      </HeaderComponent>
  
      <main
        className="
          flex
          items-center
          justify-center
          w-full
          h-screen
          bg-slate-100
        ">
      </main>

      <FooterComponent />
    </div>
  );
}

export default RegisterPage;
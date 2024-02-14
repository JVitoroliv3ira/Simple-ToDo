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

      <FooterComponent />
    </div>
  );
}

export default RegisterPage;
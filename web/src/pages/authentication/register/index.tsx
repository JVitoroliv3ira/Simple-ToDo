import React from "react";
import HeaderComponent from "../../../components/header";

const RegisterPage = () => {
  return (
    <div>
      <HeaderComponent>
        <a className="font-light" href="/app/auth/register">Crie sua conta</a>
        <a className="font-light" href="/app/auth/login">Entrar</a>
      </HeaderComponent>
    </div>
  );
}

export default RegisterPage;
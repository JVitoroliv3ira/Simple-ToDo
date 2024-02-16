import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import HeaderComponent from '../../../components/header';
import FooterComponent from '../../../components/footer';
import InputField from '../../../components/input-field';
import UserAuthenticationRequestDTO from '../../../core/dtos/requests/authentication/user-authentication-request.dto';
import AuthenticatedUserResponseDTO from '../../../core/dtos/responses/authentication/authenticated-user-response.dto';
import handleUserRegistration from '../../../core/services/authentication/user-register.service';
import { saveEmail, saveToken } from '../../../core/services/authentication/user-authentication.service';

import loadingIcon from '../../../assets/icons/dots.svg';

const RegisterPage = () => {

  const [form, setForm] = useState({ email: '', password: '' } as UserAuthenticationRequestDTO);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleFormInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleUserRegistrationClick = async (e: React.FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await handleUserRegistration(form);
      response.hasErrors ? handleRegistrationError(response.errors) : handleRegistrationSuccess(response.content);
    } catch (error) {
      toast.error('Ocorreu um erro durante o registro.');
    } finally {
      setLoading(false);
    }
  };

  const handleRegistrationSuccess = (res: AuthenticatedUserResponseDTO): void => {
    saveToken(res.token);
    saveEmail(res.email);
    navigate('/app/home');
  };

  const handleRegistrationError = (err: string[]): void => {
    err.forEach((error) => toast.error(error));
  };

  return (
    <div>
      <HeaderComponent>
        <a className="font-light" href="/app/auth/register">Crie sua conta</a>
        <a className="font-light" href="/app/auth/login">Entrar</a>
      </HeaderComponent>

      <main className="flex items-center justify-center w-full h-screen bg-slate-100 p-10">
        <div className="bg-slate-200 p-10 rounded shadow-lg w-full max-w-xl">
          <form>
            <InputField label="E-mail" name="email" value={form.email} onChange={handleFormInputChange} />
            <InputField label="Senha" name="password" type="password" value={form.password} onChange={handleFormInputChange} />

            <a href="/app/auth/login" className="font-light block mb-8">Já possui uma conta?</a>

            <button
              className={`flex align-center justify-center w-full p-3 rounded bg-slate-950 hover:bg-slate-800 text-white disabled:bg-slate-800 disabled:cursor-not-allowed disabled:text-slate-500 ${loading ? 'opacity-50' : ''}`}
              onClick={handleUserRegistrationClick}
              disabled={loading}
            >
              {!loading ? <span>Cadastrar</span> : <img src={loadingIcon} alt="Carregando" />}
            </button>
          </form>
        </div>
      </main>

      <FooterComponent />
    </div>
  );
}

export default RegisterPage;

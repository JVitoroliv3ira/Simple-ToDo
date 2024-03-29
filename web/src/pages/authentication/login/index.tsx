import { useState } from 'react';
import UnauthenticatedLayout from '../../../components/layouts/unauthenticated-layout';
import UserAuthenticationRequestDTO from '../../../core/dtos/requests/authentication/user-authentication-request.dto';
import InputField from '../../../components/input-field';

import loadingIcon from '../../../assets/icons/dots.svg';
import { toast } from 'react-toastify';
import handleUserAuthentication, { saveEmail, saveToken } from '../../../core/services/authentication/user-authentication.service';
import AuthenticatedUserResponseDTO from '../../../core/dtos/responses/authentication/authenticated-user-response.dto';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [form, setForm] = useState({ email: '', password: '' } as UserAuthenticationRequestDTO);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleFormInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleLoginClick = async (e: React.FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await handleUserAuthentication(form);
      response.hasErrors ? handleAuthenticationError(response.errors) : handleAuthenticationSuccess(response.content);
    } catch(e) {
      toast.error('Ocorreu um erro durante a autenticação.');
    } finally {
      setLoading(false);
    }
  }

  const handleAuthenticationSuccess = (res: AuthenticatedUserResponseDTO): void => {
    saveToken(res.token);
    saveEmail(res.email);
    navigate('/app/home');
  }

  const handleAuthenticationError = (err: string[]): void => {
    err.forEach((error) => toast.error(error));
  } 

  return (
    <UnauthenticatedLayout>
      <div className="bg-slate-200 p-10 rounded shadow-lg w-full max-w-xl">
        <form>
          <InputField label="E-mail" name="email" value={form.email} onChange={handleFormInputChange} />
          <InputField label="Senha" name="password" type="password" value={form.password} onChange={handleFormInputChange} />

          <a href="/app/auth/register" className="font-light block mb-8">Ainda não possui uma conta?</a>

          <button
            className={`flex align-center justify-center w-full p-3 rounded bg-slate-950 hover:bg-slate-800 text-white disabled:bg-slate-800 disabled:cursor-not-allowed disabled:text-slate-500 ${loading ? 'opacity-50' : ''}`}
            onClick={handleLoginClick}
            disabled={loading}
          >
            {!loading ? <span>Entrar</span> : <img src={loadingIcon} alt="Carregando" />}
          </button>
        </form>
      </div>
    </UnauthenticatedLayout>
  );
}

export default LoginPage;

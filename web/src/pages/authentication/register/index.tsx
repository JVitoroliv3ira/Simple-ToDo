import HeaderComponent from "../../../components/header";
import FooterComponent from "../../../components/footer";
import { useState } from "react";
import UserAuthenticationRequestDTO from "../../../core/dtos/requests/authentication/user-authentication-request.dto";
import handleUserRegistration from "../../../core/services/authentication/user-register.service";
import { toast } from "react-toastify";

const RegisterPage = () => {
  const [form, setForm] = useState({email: '', password: ''} as UserAuthenticationRequestDTO);
  const [errors, setErrors] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);

  const handleUserRegistrationClick = (e: any): void => {
    e.preventDefault();
    setLoading(true);
    handleUserRegistration(form)
      .then(r => {
        r.errors.length > 0 
          ? handleRegistrationError(r.errors)
          : handleRegistrationSuccess();
      })
      .finally(() => setLoading(false));
  }

  const handleRegistrationSuccess = (): void => {

  }

  const handleRegistrationError = (err: string[]): void => {
    setErrors(err);
    errors.forEach(e => toast.error(e));
  }

  const handleFormInputChange = (e: any) => {
    const field = e.target.name;
    const value = e.target.value;
    setForm(
      {
        ...form,
        [field]: value
      }
    );
  }

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
          p-10
        ">
        
        <div className="bg-slate-200 p-10 rounded shadow-lg w-full max-w-xl">
          <form>
            <div className="mb-8">
              <label className="block mb-2 text-sm font-medium text-gray-950">
                E-mail
              </label>
              <input
                name="email"
                value={form.email}
                onChange={handleFormInputChange}
                className="w-full p-3 rounded text-sm"
                placeholder="Informe o seu endereço de e-mail"
              />
            </div>
            <div className="mb-8">
              <label className="block mb-2 text-sm font-medium text-gray-950">
                Senha
              </label>
              <input
                name="password"
                value={form.password}
                onChange={handleFormInputChange}
                className="w-full p-3 rounded text-sm"
                placeholder="Informe a sua senha"
              />
            </div>
            <a href="/app/auth/login" className="font-light block mb-8">
              Já possui uma conta?
            </a>
            <button
              className="
                w-full
                p-3
                rounded
                bg-slate-950
                hover:bg-slate-800
                text-white"
              onClick={handleUserRegistrationClick}
              >
              Cadastrar
            </button>
          </form>
        </div>

      </main>

      <FooterComponent />
    </div>
  );
}

export default RegisterPage;
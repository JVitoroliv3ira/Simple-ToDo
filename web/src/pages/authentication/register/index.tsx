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
          p-10
        ">
        
        <div className="bg-slate-200 p-10 rounded shadow-lg w-full max-w-xl">
          <form>
            <div className="mb-8">
              <label className="block mb-2 text-sm font-medium text-gray-950">
                E-mail
              </label>
              <input
                className="w-full p-3 rounded text-sm"
                placeholder="Informe o seu endereço de e-mail"
              />
            </div>
            <div className="mb-8">
              <label className="block mb-2 text-sm font-medium text-gray-950">
                Senha
              </label>
              <input
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
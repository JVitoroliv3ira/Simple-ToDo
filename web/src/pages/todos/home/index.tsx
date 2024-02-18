import CardComponent from "../../../components/card";
import CardHeaderComponent from "../../../components/card/card-header";
import AuthenticatedLayout from "../../../components/layouts/authenticated-layout";

import plusIcon from '../../../assets/icons/plus_icon.svg';

const HomePage = () => {
  return (
    <AuthenticatedLayout>
      <CardComponent>
        <CardHeaderComponent title="Tarefas cadastradas">
          <button className="p-1 bg-slate-950 rounded">
            <img src={plusIcon} alt="Cadastrar tarefa" />
          </button>
        </CardHeaderComponent>
      </CardComponent>
    </AuthenticatedLayout>
  )
}

export default HomePage;

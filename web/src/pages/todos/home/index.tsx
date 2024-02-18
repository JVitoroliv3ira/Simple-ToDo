import CardComponent from "../../../components/card";
import CardHeaderComponent from "../../../components/card/card-header";
import AuthenticatedLayout from "../../../components/layouts/authenticated-layout";

import plusIcon from '../../../assets/icons/plus_icon.svg';
import CardBodyComponent from "../../../components/card/card-body";
import TodoDetailComponent from "../../../components/todo-detail";
import TodoCreateFormComponent from "../../../components/todo-create-form";
import { useState } from "react";

const HomePage = () => {
  const [showForm, setShowForm] = useState(false);

  const handleOpenForm = (): void => {
    setShowForm(true);
  }

  const handleCloseForm = (): void => {
    setShowForm(false);
  }

  return (
    <AuthenticatedLayout>
      <CardComponent>
        <CardHeaderComponent title="Tarefas cadastradas">
          <button className="p-1 bg-slate-950 rounded" onClick={handleOpenForm}>
            <img src={plusIcon} alt="Cadastrar tarefa" />
          </button>
        </CardHeaderComponent>
        
        <hr className="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700" />

        <CardBodyComponent>

          {showForm && <TodoCreateFormComponent onClose={handleCloseForm} />}
          
          <TodoDetailComponent
            title="Titulo 01"
            description="Descricao 01"
            isFinished={false}
            isPriority={true}
            deadlineHasPassed={true}
            dueDate={new Date("2024-01-01")}
          />

          <TodoDetailComponent
            title="Titulo 02"
            description="Descricao 02"
            isFinished={true}
            isPriority={false}
            deadlineHasPassed={false}
            dueDate={new Date("2024-01-01")}
          />

        </CardBodyComponent>
      </CardComponent>
    </AuthenticatedLayout>
  )
}

export default HomePage;

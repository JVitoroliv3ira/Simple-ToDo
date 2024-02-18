import { useState } from "react";
import TodoCreationRequestDTO from "../../core/dtos/requests/todos/todo-creation-request.dto";
import handleTodoCreation from "../../core/services/todos/todo-creation.service";
import { toast } from "react-toastify";

import loadingIcon from '../../assets/icons/dots.svg';

type TodoCreateFormComponentProps = {
  onClose: () => void;
}

const TodoCreateFormComponent = ({ onClose }: TodoCreateFormComponentProps) => {
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState<TodoCreationRequestDTO>({
    title: '',
    description: '',
    isPriority: false,
    dueDate: undefined
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    });
  };

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setForm({
      ...form,
      isPriority: event.target.value === 'true'
    });
  };

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setForm({
      ...form,
      dueDate: new Date(event.target.value)
    });
  };

  const handleCreateClick = async (e: React.FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await handleTodoCreation(form);
      response.hasErrors ? handleCreationError(response.errors) : handleCreationSuccess(response.message);
    } catch(e) {
      toast.error('Ocorreu um erro inesperado durante o cadastro da tarefa')
    } finally {
      setLoading(false);
    }
  }

  const handleCreationSuccess = (message: string) => {
    toast.info(message);
    onClose();
  }

  const handleCreationError = (err: string[]): void => {
    err.forEach((error) => toast.error(error));
  };

  return(
    <div className="flex flex-col">
      <div className="mb-4">
        <input 
          type="text" 
          name="title" 
          placeholder="Título da Tarefa" 
          className="w-full p-2 text-sm border-2 border-gray-200 shadow-sm rounded-md"
          value={form.title}
          onChange={handleInputChange} 
        />
      </div>
    
      <div className="mb-4">
        <textarea 
          name="description"
          placeholder="Descrição" 
          className="w-full p-2 border-2 text-sm border-gray-300 rounded-md shadow-sm resize-none"
          value={form.description}
          onChange={handleInputChange} 
        />
      </div>
    
      <div className="mb-4 flex justify-between">
        <select 
          name="isPriority"
          className="p-2 border-2 border-gray-300 rounded-md shadow-sm mr-2"
          value={String(form.isPriority)}
          onChange={handleSelectChange}
        >
          <option value="true">Prioridade</option>
          <option value="false">Não é prioridade</option>
        </select>
        <input 
          type="date" 
          name="dueDate"
          className="p-2 border-2 border-gray-300 shadow-sm rounded-md"
          value={form.dueDate?.toISOString().split('T')[0] || ''}
          onChange={handleDateChange}
        />
      </div>

      <div className="flex justify-end space-x-2">
        <button
          className={`px-4 py-2 border border-gray-500 rounded-md text-gray-600 hover:bg-gray-100 disabled:bg-slate-800 disabled:cursor-not-allowed disabled:text-slate-500 ${loading ? 'opacity-50' : ''}`}
          onClick={onClose}
          disabled={loading}  
        >
            Cancelar
          </button>
        <button 
          className={`px-4 py-2 text-white bg-slate-950 hover:bg-slate-800 rounded-md disabled:bg-slate-800 disabled:cursor-not-allowed disabled:text-slate-500 ${loading ? 'opacity-50' : ''}`}
          onClick={handleCreateClick}
          disabled={loading}
        >
          {!loading ? <span>Cadastrar</span> : <img src={loadingIcon} alt="Carregando" />}
        </button>
      </div>

      <hr className="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700" />
    </div>
  );
}

export default TodoCreateFormComponent;

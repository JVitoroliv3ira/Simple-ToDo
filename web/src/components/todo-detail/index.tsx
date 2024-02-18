import { useState } from 'react';
import TodoDetailsResponseDTO from '../../core/dtos/responses/todos/todo-details-response.dto';

import pencilIcon from '../../assets/icons/pencil.svg';
import trashIcon from '../../assets/icons/trash.svg';

const TodoDetailComponent = ({
  id,
  title,
  description,
  isFinished,
  isPriority,
  dueDate,
  deadlineHasPassed
}: TodoDetailsResponseDTO) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const [finished, setFinished] = useState(isFinished);

  const toggleIsExpanded = (): void => {
    setIsExpanded(!isExpanded);
  };

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.stopPropagation();
    setFinished(!finished);
  };

  const formatDate = (date: Date) => {
    return new Date(date).toLocaleDateString();
  };

  return (
    <div >
      <div className='flex justify-between items-center'>
        <div className='flex items-center space-x-2'>
          <input type="checkbox" checked={finished} onChange={handleCheckboxChange} />
          <h3
            onClick={toggleIsExpanded}
            className={`text-lg cursor-pointer ${isPriority ? 'text-orange-400' : ''}`}>
            {title}
          </h3>
        </div>
        <div className='space-x-2'>
          <button className='bg-orange-500 p-1 rounded'>
            <img src={pencilIcon} alt='Editar tarefa' />
          </button>
          <button className='bg-red-500 p-1 rounded'>
            <img src={trashIcon} alt='Deletar tarefa' />
          </button>
        </div>
      </div>

      <div className={`${isExpanded ? 'block' : 'hidden'}`}>
        <div className="p-2 w-full bg-gray-300 mt-3 rounded">
          <p>{description}</p>
        </div>
        <div className='mt-2'>
          {dueDate && (
            <span className={`${deadlineHasPassed ? 'text-red-500' : ''}`}>
              Prazo para realizar a tarefa: {formatDate(dueDate)}
            </span>
          )}
        </div>
      </div>

      <hr className="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700" />
    </div>
  );
};

export default TodoDetailComponent;

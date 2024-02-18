import TodoCreationRequestDTO from "../../dtos/requests/todos/todo-creation-request.dto";
import ResponseDTO from "../../dtos/responses/response.dto";
import TodoDetailsResponseDTO from "../../dtos/responses/todos/todo-details-response.dto";
import apiService from "../api.service";
import { getToken } from "../authentication/user-authentication.service";

const handleTodoCreation = (content: TodoCreationRequestDTO) => {
  const BASE_URL = import.meta.env.VITE_SIMPLE_TODO_API_BASE_URL;
  const endpoint =  `${BASE_URL}/todo/create`;
  const token = getToken();
  return apiService<TodoCreationRequestDTO, ResponseDTO<TodoDetailsResponseDTO>>(
    endpoint,
    'POST',
    content,
    {
      'Authorization': `Bearer ${token}`
    }
  );
}

export default handleTodoCreation;

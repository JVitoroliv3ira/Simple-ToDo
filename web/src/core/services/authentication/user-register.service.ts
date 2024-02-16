import UserAuthenticationRequestDTO from '../../dtos/requests/authentication/user-authentication-request.dto';
import AuthenticatedUserRequestDTO from '../../dtos/responses/authentication/authenticated-user-response.dto';
import ResponseDTO from '../../dtos/responses/response.dto';
import apiService from '../api.service';

const handleUserRegistration = (content: UserAuthenticationRequestDTO) => {
  const BASE_URL = import.meta.env.VITE_SIMPLE_TODO_API_BASE_URL;
  const endpoint =  `${BASE_URL}/auth/register`;
  return apiService<UserAuthenticationRequestDTO, ResponseDTO<AuthenticatedUserRequestDTO>>(
    endpoint,
    'POST',
    content
  );
}

export default handleUserRegistration;

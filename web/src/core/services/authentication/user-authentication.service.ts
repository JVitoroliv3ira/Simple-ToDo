import UserAuthenticationRequestDTO from '../../dtos/requests/authentication/user-authentication-request.dto';
import AuthenticatedUserRequestDTO from '../../dtos/responses/authentication/authenticated-user-response.dto';
import ResponseDTO from '../../dtos/responses/response.dto';
import { set, get, remove } from '../../utils/localstorage.util';
import apiService from '../api.service';

const handleUserAuthentication = (content: UserAuthenticationRequestDTO) => {
  const BASE_URL = import.meta.env.VITE_SIMPLE_TODO_API_BASE_URL;
  const endpoint =  `${BASE_URL}/auth/login`;
  return apiService<UserAuthenticationRequestDTO, ResponseDTO<AuthenticatedUserRequestDTO>>(
    endpoint,
    'POST',
    content
  );
}

const AUTHENTICATION_TOKEN = 'AUTHENTICATION_TOKEN';
const USER_EMAIL = 'USER_EMAIL';

const saveToken = (token: string): void => set<string>(AUTHENTICATION_TOKEN, token);

const getToken = (): string | null => get<string>(AUTHENTICATION_TOKEN);

const removeToken = (): void => remove(AUTHENTICATION_TOKEN);

const saveEmail = (email: string): void => set<string>(USER_EMAIL, email);

const getEmail = (): string | null => get<string>(USER_EMAIL);

const removeEmail = (): void => remove(USER_EMAIL);

export { saveToken, getToken, removeToken, saveEmail, getEmail, removeEmail };
export default handleUserAuthentication;

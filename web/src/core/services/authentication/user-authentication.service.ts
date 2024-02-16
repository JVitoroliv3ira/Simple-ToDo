import { set, get, remove } from '../../utils/localstorage.util';

const AUTHENTICATION_TOKEN = 'AUTHENTICATION_TOKEN';
const USER_EMAIL = 'USER_EMAIL';

const saveToken = (token: string): void => set<string>(AUTHENTICATION_TOKEN, token);

const getToken = (): string | null => get<string>(AUTHENTICATION_TOKEN);

const removeToken = (): void => remove(AUTHENTICATION_TOKEN);

const saveEmail = (email: string): void => set<string>(USER_EMAIL, email);

const getEmail = (): string | null => get<string>(USER_EMAIL);

const removeEmail = (): void => remove(USER_EMAIL);

export { saveToken, getToken, removeToken, saveEmail, getEmail, removeEmail };

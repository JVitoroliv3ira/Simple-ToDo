type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

const apiService = async <C, R> (
  endpoint: string,
  method: HttpMethod = 'GET',
  content ?: C,
  headers ?: HeadersInit
): Promise<R> => {
  const config: RequestInit = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers
    }
  };

  if (content) {
    config.body = JSON.stringify(content);
  }

  const response = await fetch(endpoint, config);

  return response.json();
}

export default apiService;

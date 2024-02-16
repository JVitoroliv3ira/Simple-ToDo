interface ResponseDTO<C> {
  content: C;
  message: string;
  errors: string[];
  hasErrors: boolean;
}

export default ResponseDTO;

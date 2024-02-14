interface ResponseDTO<C> {
  content: C;
  message: string;
  errors: string[];
}

export default ResponseDTO;

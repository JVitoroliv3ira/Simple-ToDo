interface TodoCreationRequestDTO {
  title: string;
  description?: string;
  isPriority?: boolean;
  dueDate?: Date;
};

export default TodoCreationRequestDTO;

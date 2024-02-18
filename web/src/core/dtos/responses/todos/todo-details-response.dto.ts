interface TodoDetailsResponseDTO {
  id?: number;
  title?: string;
  description?: string;
  isFinished?: boolean;
  isPriority?: boolean;
  dueDate?: Date;
  deadlineHasPassed?: boolean;
}

export default TodoDetailsResponseDTO;

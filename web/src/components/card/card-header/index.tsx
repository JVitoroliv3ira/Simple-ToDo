import { ReactNode } from 'react';

type CardHeaderComponentProps = {
  title: string;
  children: ReactNode;
};

const CardHeaderComponent = ({ title, children }: CardHeaderComponentProps) => {
  return (
    <div className="flex align-center justify-between">
      <h3 className="text-lg font-bold">{title}</h3>
      {children}
    </div>
  );
}

export default CardHeaderComponent;

import { ReactNode } from 'react';

type CardBodyComponentProps = {
  children: ReactNode
};

const CardBodyComponent = ({children}: CardBodyComponentProps) => {
  return (
    <div className="flex flex-col">
      {children}
    </div>
  );
}

export default CardBodyComponent;

import { ReactNode } from "react";

type CardComponentProps = {
  children: ReactNode;
};

const CardComponent = ({ children }: CardComponentProps) => {
  return (
    <div className="bg-slate-200 p-10 rounded shadow-lg w-full max-w-3xl">
      {children}
    </div>
  );
}

export default CardComponent;
import React, { ReactNode } from 'react';

type HeaderComponentProps = {
  children: ReactNode;
};

const HeaderComponent = ({ children }: HeaderComponentProps) => {
  return (
    <header 
      className='
        flex
        items-center
        justify-between
        w-full
        h-20
        p-5
        bg-slate-200
      '
    >
      <a
        className='text-xl font-light'
        href='/app/home'
      >
        Simple ToDo
      </a>
      <div className='flex space-x-4'>
        {children}
      </div>
    </header>
  );
}

export default HeaderComponent;

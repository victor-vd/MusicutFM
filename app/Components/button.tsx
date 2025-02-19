"use client";

import React from "react";

type ButtonProps = {
  children: React.ReactNode;
  onClick: () => void;
};

const Button = ({ children }: ButtonProps) => {
  return (
    <button
      className="bg-secondary-color py-4 px-14 rounded text-white"
      onClick={() => console.log('hello from child')}
    >
      {children}
    </button>
  );
};

export default Button;
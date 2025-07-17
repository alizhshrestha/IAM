// components/ui/Tabs.tsx
import React, { useState } from "react";

interface Tab {
  label: string;
  value: string;
}

interface TabsProps {
  tabs: Tab[];
  onChange?: (value: string) => void;
  defaultValue?: string;
}

export const Tabs: React.FC<TabsProps> = ({ tabs, onChange, defaultValue }) => {
  const [active, setActive] = useState(defaultValue ?? tabs[0]?.value);

  const handleClick = (value: string) => {
    setActive(value);
    onChange?.(value);
  };

  return (
    <div className="flex space-x-4 border-b border-gray-200">
      {tabs.map((tab) => (
        <button
          key={tab.value}
          onClick={() => handleClick(tab.value)}
          className={`pb-2 text-sm font-medium ${
            active === tab.value
              ? "border-b-2 border-blue-500 text-blue-600"
              : "text-gray-500 hover:text-blue-600"
          }`}
        >
          {tab.label}
        </button>
      ))}
    </div>
  );
};

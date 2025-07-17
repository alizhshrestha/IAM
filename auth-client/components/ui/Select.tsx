// components/ui/Select.tsx
import React from "react";

interface SelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
  label?: string;
  options: { label: string; key: string; value: string }[];
}

export const Select: React.FC<SelectProps> = ({ label, options, ...props }) => {
  return (
    <div className="w-full">
      {label && (
        <label className="block mb-1 text-sm font-medium text-gray-700">{label}</label>
      )}
      <select
        {...props}
        className="w-full px-4 py-2 text-sm rounded-lg border border-gray-300 bg-white text-gray-900 focus:outline-none focus:ring-2 focus:ring-blue-500"
      >
        <option value="">-- Select --</option>
        {options.map((opt) => (
          <option key={opt.key} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
    </div>
  );
};

interface RoleSelectorProps {
  roles: string[];
  onSelect: (role: string) => void;
}

export default function RoleSelector({ roles, onSelect }: RoleSelectorProps) {
  return (
    <div className="p-6 max-w-xl mx-auto">
      <h2 className="text-lg font-semibold mb-4">Select your role</h2>
      <div className="grid gap-3">
        {roles.map((role) => (
          <button
            key={role}
            className="w-full px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700 transition"
            onClick={() => onSelect(role)}
          >
            {role.replace("ROLE_", "").toUpperCase()}
          </button>
        ))}
      </div>
    </div>
  );
}

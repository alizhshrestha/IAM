import { Tenant } from "@/types";

export default function TenantSelector({ tenants, onSelect }:{
    tenants: Tenant[], onSelect: (selected: any) => void;
}){
    return (
        <div>
            <label className="block mb-1">Select Tenant</label>
            <select
                className="shadow-md px-3 py-2 rounded"
                onChange={(e) => {
                    const selected = tenants.find(t => t.id === e.target.value);
                    onSelect(selected);
                }}
            >
                <option value="">Select Tenant</option>
                {tenants.map(t => (
                    <option key={t.id} value={t.id}>{t.name}</option>
                ))}

            </select>
        </div>
    )
}
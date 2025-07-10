import { ClientInfo } from "@/types";

export default function ClientSelector({ clients, onSelect }: {
    clients: ClientInfo[], onSelect: (selected: any) => void;
}) {
    return (
        <div className="mt-4">
            <label className="block mb-1">Select Role / Client</label>
            <select
                className="border px-4 py-2 rounded"
                onChange={(e) => {
                    const selected = clients.find(c => c.clientId === e.target.value);
                    onSelect(selected);
                }}
            >
                <option value="">Choose Client</option>
                {clients.map(c => (
                    <option key={c.clientId} value={c.clientId}>
                        {c.clientName}
                    </option>
                ))}
            </select>
        </div>
    )
}
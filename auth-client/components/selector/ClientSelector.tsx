import { ClientInfo } from "@/types";

interface ClientSelectorProps {
    clients: ClientInfo[];
    onSelect: (client: ClientInfo) => void;
}

export default function ClientSelector({ clients, onSelect}: ClientSelectorProps){
    return (
        <div>
            <h2 className="text-lg font-semibold mb-2">Select a client</h2>
            <div className="grid gap-2">
                {clients.map((client) => (
                    <button
                        key={client.clientId}
                        onClick={() => onSelect(client)}
                        className="px-4 py-2 bg-purple-600 text-white rounded hover:bg-purple-700 transition"
                    >
                        {client.clientName}
                    </button>
                ))}

            </div>
        </div>
    )
}
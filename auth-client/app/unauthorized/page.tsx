export default function UnauthorizedPage() {
    return (
        <main className="flex items-center justify-center h-screen text-center">
            <div>
                <h1 className="text-4xl font-bold text-red-600 mb-4">403 - Unauthorized</h1>
                <p className="text-gray-700">You do not have access to this page.</p>
            </div>
        </main>
    )
}
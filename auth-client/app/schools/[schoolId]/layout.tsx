import { SchoolProvider } from "@/context/SchoolContext";

export default function SchoolLayout({children,}:{children: React.ReactNode;}){
    return (
        <SchoolProvider>
            {children}
        </SchoolProvider>
    )
}
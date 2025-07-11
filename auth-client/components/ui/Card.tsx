'use client';

import { cn } from "@/utils/cn";
import { HTMLAttributes, ReactNode } from "react";

type CardProps = HTMLAttributes<HTMLDivElement> & {
    children: ReactNode;
};

type CardContentProps = HTMLAttributes<HTMLDivElement> & {
    children: ReactNode;
};

export function Card({ children, className, ...props }: CardProps){
    return (
        <div
            className={cn(
                'bg-white dark:bg-gray-900 rounded-2xl shadow-md border border-gray-200 dark:border-gray-700 transition-all',
                className
            )}
            {...props}
        >
            {children}
        </div>
    )
}

export function CardContent({ children, className, ...props }: CardContentProps){
    return (
        <div className={cn('p-6', className)} {...props}>
            {children}
        </div>
    )
}
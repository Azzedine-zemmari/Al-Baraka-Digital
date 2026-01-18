import { Operation } from "./Operation";
export interface Document {
    id: number;
    fileName: string;
    fileType: string;
    storagePath: string;
    uploadedAt: string;
    operation: Operation;
}
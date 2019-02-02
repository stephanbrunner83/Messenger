export interface IMessage {
    id?: number;
    content?: string;
    senderId?: number;
    receiverId?: number;
    conversationId?: number;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public content?: string,
        public senderId?: number,
        public receiverId?: number,
        public conversationId?: number
    ) {}
}

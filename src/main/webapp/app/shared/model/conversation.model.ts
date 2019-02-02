import { IMessage } from 'app/shared/model/message.model';

export interface IConversation {
    id?: number;
    topic?: string;
    messages?: IMessage[];
}

export class Conversation implements IConversation {
    constructor(public id?: number, public topic?: string, public messages?: IMessage[]) {}
}

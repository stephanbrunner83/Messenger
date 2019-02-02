export interface IParticipant {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
}

export class Participant implements IParticipant {
    constructor(public id?: number, public firstName?: string, public lastName?: string, public email?: string) {}
}

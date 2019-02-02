import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'conversation',
                loadChildren: './conversation/conversation.module#MessangerConversationModule'
            },
            {
                path: 'participant',
                loadChildren: './participant/participant.module#MessangerParticipantModule'
            },
            {
                path: 'message',
                loadChildren: './message/message.module#MessangerMessageModule'
            },
            {
                path: 'conversation',
                loadChildren: './conversation/conversation.module#MessangerConversationModule'
            },
            {
                path: 'participant',
                loadChildren: './participant/participant.module#MessangerParticipantModule'
            },
            {
                path: 'message',
                loadChildren: './message/message.module#MessangerMessageModule'
            },
            {
                path: 'conversation',
                loadChildren: './conversation/conversation.module#MessangerConversationModule'
            },
            {
                path: 'participant',
                loadChildren: './participant/participant.module#MessangerParticipantModule'
            },
            {
                path: 'message',
                loadChildren: './message/message.module#MessangerMessageModule'
            },
            {
                path: 'conversation',
                loadChildren: './conversation/conversation.module#MessangerConversationModule'
            },
            {
                path: 'participant',
                loadChildren: './participant/participant.module#MessangerParticipantModule'
            },
            {
                path: 'message',
                loadChildren: './message/message.module#MessangerMessageModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MessangerEntityModule {}

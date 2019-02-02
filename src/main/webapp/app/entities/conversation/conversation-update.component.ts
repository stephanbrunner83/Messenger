import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IConversation } from 'app/shared/model/conversation.model';
import { ConversationService } from './conversation.service';

@Component({
    selector: 'jhi-conversation-update',
    templateUrl: './conversation-update.component.html'
})
export class ConversationUpdateComponent implements OnInit {
    conversation: IConversation;
    isSaving: boolean;

    constructor(protected conversationService: ConversationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conversation }) => {
            this.conversation = conversation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conversation.id !== undefined) {
            this.subscribeToSaveResponse(this.conversationService.update(this.conversation));
        } else {
            this.subscribeToSaveResponse(this.conversationService.create(this.conversation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversation>>) {
        result.subscribe((res: HttpResponse<IConversation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParticipant } from 'app/shared/model/participant.model';
import { AccountService } from 'app/core';
import { ParticipantService } from './participant.service';

@Component({
    selector: 'jhi-participant',
    templateUrl: './participant.component.html'
})
export class ParticipantComponent implements OnInit, OnDestroy {
    participants: IParticipant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected participantService: ParticipantService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.participantService
            .query()
            .pipe(
                filter((res: HttpResponse<IParticipant[]>) => res.ok),
                map((res: HttpResponse<IParticipant[]>) => res.body)
            )
            .subscribe(
                (res: IParticipant[]) => {
                    this.participants = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParticipants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParticipant) {
        return item.id;
    }

    registerChangeInParticipants() {
        this.eventSubscriber = this.eventManager.subscribe('participantListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

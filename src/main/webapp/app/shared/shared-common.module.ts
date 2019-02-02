import { NgModule } from '@angular/core';

import { MessangerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MessangerSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MessangerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MessangerSharedCommonModule {}

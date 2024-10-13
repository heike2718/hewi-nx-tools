import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { #dto-name# } from '#lib-alias-prefix#/#feature-name#/model';

export const #feature-name#Actions = createActionGroup({
    source: '#feature-name#',
    events: {
        'LOAD_ALL_DTOS': emptyProps(),
        'ALL_DTOS_LOADED': props<{ dtos: #dto-name#[] }>()
    }
});

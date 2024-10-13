import { createFeature, createReducer, on } from '@ngrx/store';
import { #dto-name# } from '#lib-alias-prefix#/#feature-name#/model';
import { #feature-name#Actions } from './#feature-name#.actions';

export interface #feature-name-cc#State {
    readonly loaded: boolean;
    readonly dtos: #dto-name#[];
};

const initial#feature-name-cc#State: #feature-name-cc#State = {
    loaded: false,
    dtos: [],
};

export const #feature-name#Feature = createFeature({
    name: '#feature-name#',
    reducer: createReducer(
        initial#feature-name-cc#State,
        on(#feature-name#Actions.aLL_DTOS_LOADED, (state, action) => {
            return {
                ...state,
                dtos: action.dtos
                loaded: true
            };
        }),
    )
});


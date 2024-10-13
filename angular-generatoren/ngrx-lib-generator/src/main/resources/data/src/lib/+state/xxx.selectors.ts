import { createSelector } from '@ngrx/store';
import { #feature-name#Feature } from './#feature-name#.reducer';


const { select#feature-name-cc#State: select#feature-name-cc#State} = #feature-name#Feature;

const isLoaded = createSelector(
    select#feature-name-cc#State,
    state => state.loaded
)

const dtos = createSelector(
    select#feature-name-cc#State,
    state => state.dtos
)


export const from#feature-name-cc# = {
    isLoaded,
    dtos,
};

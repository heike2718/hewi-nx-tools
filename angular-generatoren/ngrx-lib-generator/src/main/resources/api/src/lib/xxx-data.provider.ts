import { provideEffects } from '@ngrx/effects';
import { provideState } from '@ngrx/store';
import { #feature-name-cc#Effects, #feature-name#Feature } from '#lib-alias-prefix#/#feature-name#/data';


export const #feature-name#DataProvider = [
    provideState(#feature-name#Feature),
    provideEffects(#feature-name-cc#Effects)
];

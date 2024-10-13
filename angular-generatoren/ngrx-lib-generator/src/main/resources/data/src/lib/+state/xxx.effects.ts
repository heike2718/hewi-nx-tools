import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { #feature-name-cc#HttpService } from '../#feature-name#-http.service';
import { #feature-name#Actions } from './#feature-name#.actions';
import { map, switchMap } from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class #feature-name-cc#Effects {

    #actions = inject(Actions);
    ##feature-name#HttpService = inject(#feature-name-cc#HttpService);

    loadDtos$ = createEffect(() => {
        return this.#actions.pipe(
            ofType(#feature-name#Actions.lOAD_ALL_DTOS),
            switchMap((action) => this.##feature-name#HttpService.loadDtos()),
            map((dtos) => #feature-name#Actions.aLL_DTOS_LOADED({ dtos }))
        );
    })
}
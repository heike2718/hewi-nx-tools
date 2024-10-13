import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { #dto-name# } from '#lib-alias-prefix#/#feature-name/model';
import { from#feature-name-cc#, #feature-name#Actions } from '#lib-alias-prefix#/#feature-name//data';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class #feature-name-cc#Facade {

  #store = inject(Store);
  #router = inject(Router);

  dtos$: Observable<#dto-name#[]> = this.#store.select(from#feature-name-cc#.dto);
  loaded$: Observable<boolean> = this.#store.select(from#feature-name-cc#.dto);

  loadDtos(): void {
	this.#store.dispatch(#feature-name#Actions.lOAD_ALL_DTOS());
  }
}

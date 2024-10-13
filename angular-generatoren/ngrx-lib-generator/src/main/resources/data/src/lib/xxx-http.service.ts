import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { #dto-name# } from '#lib-alias-prefix#/#feature-name#/model';
import { Observable } from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class #feature-name-cc#HttpService {

    #http = inject(HttpClient);
    #url = '/api/#feature-name#';


    loadDtos(): Observable<#dto-name#[]> {

        const url = this.#url + '/v1';
        const headers = new HttpHeaders().set('Accept', 'application/json');

        return this.#http.get<#dto-name#[]>(url, { headers });
    }
}
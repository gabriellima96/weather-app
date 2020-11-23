import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { City } from '../models/city';
import { PageCity } from '../models/pageCity';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  private path = `${environment.api}/api/v1/cities`;

  constructor(private http: HttpClient) { }

  public findAll(params: Params): Observable<PageCity> {
    return this.http.get<PageCity>(this.path, { params });
  }

  public save(name: string): Observable<City> {
    return this.http.post<City>(this.path, { name });
  }
}

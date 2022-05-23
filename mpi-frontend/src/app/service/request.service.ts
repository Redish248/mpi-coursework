import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TripRequest } from '../entity/TripRequest';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient, private config: ConfigService) { }

  public createRequest(req: TripRequest) : Observable<void> {
    return this.http.post<void>(this.config.baseUrl+'/request/create', req);
  }

}

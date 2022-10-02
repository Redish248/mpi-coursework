import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { TripRequest } from '../entity/TripRequest'
import { ConfigService } from './config.service'

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient, private config: ConfigService) {
  }

  public createRequest(req: TripRequest): Observable<void> {
    return this.http.post<void>(this.config.baseUrl + '/request/create', req)
  }

  public getPendingRequests(): Observable<TripRequest[]> {
    return this.http.get<TripRequest[]>(this.config.baseUrl + '/request/pending')
  }

  public getCompleteRequests(): Observable<TripRequest[]> {
    return this.http.get<TripRequest[]>(this.config.baseUrl + '/request/complete')
  }

  public getCancelledRequests(): Observable<TripRequest[]> {
    return this.http.get<TripRequest[]>(this.config.baseUrl + '/request/cancelled')
  }

  public getEndedRequests(): Observable<TripRequest[]> {
    return this.http.get<TripRequest[]>(this.config.baseUrl + '/request/ended')
  }

  public cancelRequest(request: TripRequest) {
    return this.http.post(this.config.baseUrl + '/request/cancel', request)
  }

  public endRequest(request: TripRequest) {
    return this.http.post(this.config.baseUrl + '/request/end', request)
  }

  public rejectRequest(request: TripRequest) {
    return this.http.post(this.config.baseUrl + '/request/reject', request)
  }

  public approveRequest(request: TripRequest) {
    return this.http.post(this.config.baseUrl + '/request/approve', request)
  }

  public deleteReqeust(request: TripRequest) {
    return this.http.delete(this.config.baseUrl + '/request', {body: request})
  }

  public rateTrip(request: any) {
    return this.http.post(this.config.baseUrl + '/request/crew/rate', JSON.stringify(request), {headers: {'Content-Type': 'application/json'}})
  }

}

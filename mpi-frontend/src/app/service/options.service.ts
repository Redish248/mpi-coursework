import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Option } from '../entity/Option';
import { TripRequestDto } from '../entity/TripRequestDto';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class OptionsService {

  public request: TripRequestDto | undefined;

  constructor(private http: HttpClient, private configService: ConfigService) { }

  public getOptions(): Observable<Option[]> {
    return this.http.post<Option[]>(this.configService.baseUrl+'/request/options', this.request);
  }

}

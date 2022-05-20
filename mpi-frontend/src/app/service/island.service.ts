import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Island } from '../entity/island';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class IslandService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  public loadIslands(): Observable<Island[]> {
    return this.http.get<Island[]>(this.configService.baseUrl+'/island');
  }

}

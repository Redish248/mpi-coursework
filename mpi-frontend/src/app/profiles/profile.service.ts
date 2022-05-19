import { Injectable } from '@angular/core'
import { ConfigService } from '../service/config.service'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Profile } from './model/Profile'


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private configService: ConfigService, private http: HttpClient) {
  }

  private get apiUrl(): string {
    return `${this.configService.appUrl}/profiles`
  }

  getShips(): Observable<Profile[]> {
    return this.http.get<Profile[]>(`${this.apiUrl}/ships`)
  }

  getCrews(): Observable<Profile[]> {
    return this.http.get<Profile[]>(`${this.apiUrl}/crews`)
  }
}

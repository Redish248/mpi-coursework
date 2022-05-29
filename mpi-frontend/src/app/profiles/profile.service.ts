import { Injectable } from '@angular/core'
import { ConfigService } from '../service/config.service'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Crew, CrewProfile } from './model/CrewProfile'
import { UserProfile } from './model/UserProfile'
import { ShipProfile } from './model/ShipProfile'


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private configService: ConfigService, private http: HttpClient) {
  }

  private get apiUrl(): string {
    return `${this.configService.appUrl}/profiles`
  }

  getCurrentUserInfo(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/`)
  }

  getCurrentUserCrew(): Observable<Crew> {
    return this.http.get<Crew>(`${this.apiUrl}/crew`)
  }

  getShips(): Observable<ShipProfile[]> {
    return this.http.get<ShipProfile[]>(`${this.apiUrl}/ships`)
  }

  getCrews(): Observable<CrewProfile[]> {
    return this.http.get<CrewProfile[]>(`${this.apiUrl}/crews`)
  }
}

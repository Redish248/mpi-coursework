import {Injectable} from '@angular/core'
import {ConfigService} from '../service/config.service'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {Crew, CrewProfile} from './model/CrewProfile'
import {UserProfile} from './model/UserProfile'
import {Ship, ShipProfile} from './model/ShipProfile'


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

    getCurrentUserShip(): Observable<Ship> {
        return this.http.get<Ship>(`${this.apiUrl}/ship`)
    }

    makeUserVip() {
        return this.http.post(`${this.apiUrl}/makevip`, null)
    }

    revertVip() {
        return this.http.post(`${this.apiUrl}/revertVip`, null)
    }

    getShips(): Observable<ShipProfile[]> {
        return this.http.get<ShipProfile[]>(`${this.apiUrl}/ships`)
    }

    getCrews(): Observable<CrewProfile[]> {
        return this.http.get<CrewProfile[]>(`${this.apiUrl}/crews`)
    }

    addCrew(newCrew: any): Observable<Crew> {
        return this.http.post<Crew>(`${this.apiUrl}/crew`, JSON.stringify(newCrew), {headers: {'Content-Type': 'application/json'}})
    }

    updateCrew(newCrew: any): Observable<Crew> {
        return this.http.post<Crew>(`${this.apiUrl}/crewinfo`, JSON.stringify(newCrew), {headers: {'Content-Type': 'application/json'}})
    }

    addShip(newShip: any): Observable<Ship> {
        return this.http.post<Ship>(`${this.apiUrl}/ship`, JSON.stringify(newShip), {headers: {'Content-Type': 'application/json'}})
    }

    updateShip(newShip: any): Observable<Ship> {
        return this.http.post<Ship>(`${this.apiUrl}/shipinfo`, JSON.stringify(newShip), {headers: {'Content-Type': 'application/json'}})
    }

    updateUser(userInfo: any): Observable<UserProfile> {
        console.log(userInfo)
        return this.http.post<UserProfile>(`${this.apiUrl}/userinfo`, JSON.stringify(userInfo), {headers: {'Content-Type': 'application/json'}})
    }
}

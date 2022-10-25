import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { ConfigService } from '../service/config.service'
import { Crew } from './model/crew'
import { Observable } from 'rxjs'
import {Island} from "./model/island";

@Injectable({
  providedIn: 'root'
})
export class FsbService {

  constructor(private http: HttpClient, private config: ConfigService) {
  }

  private get url() {
    return this.config.baseUrl + "/mpi/fsb"
  }

  getCrews(): Observable<Crew[]> {
    return this.http.get<Crew[]>(`${this.url}/crews`)
  }

  getIslands(): Observable<Island[]> {
    return this.http.get<Island[]>(`${this.url}/islands`)
  }

  markCrewAsPirate(crewId: number) {
    return this.http.put(`${this.url}/crew/${crewId}/pirate/true`, null)
  }

  markCrewAsGoodPerson(crewId: number) {
    return this.http.put(`${this.url}/crew/${crewId}/pirate/false`, null)
  }

  markIslandAsDangerous(islandId: number) {
    return this.http.put(`${this.url}/island/${islandId}/pirate/true`, null)
  }

  markIslandAsSafe(islandId: number) {
    return this.http.put(`${this.url}/island/${islandId}/pirate/false`, null)
  }
}

import { Injectable } from '@angular/core'
import { ConfigService } from '../service/config.service'
import { HttpClient } from '@angular/common/http'
import {User} from "../entity/User";

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private appConfig: ConfigService,
              private http: HttpClient) {
  }

  private get apiUrl(): String {
    return this.appConfig.appUrl
  }

  signup(newUser: User) {
    return this.http.post(`${this.apiUrl}/signup`, JSON.stringify(newUser), {headers: {'Content-Type': 'application/json'}})
  }
}
import { Injectable } from '@angular/core'
import { User } from './User'

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor() {
  }

  getUser(): User {
    return {nickname: "test"} // TODO
  }

  login() {
  }

  logout() {

  }

}

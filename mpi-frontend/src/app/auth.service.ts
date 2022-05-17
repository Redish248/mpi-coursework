import { Injectable } from '@angular/core'
import { Permissions, User } from './User'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Router } from '@angular/router'
import { ConfigService } from './config.service'
import { map, Observable, Subscription } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private configService: ConfigService,
              private http: HttpClient,
              private router: Router) {
  }

  getUser(): User | null {
    const nickname = localStorage.getItem("nick")
    return nickname ? {nickname} : null
  }

  getAuthData() {
    return localStorage.getItem("authData")
  }

  private get apiUrl(): string {
    return this.configService.appUrl
  }

  private static saveData(nick: string, pswd: string) {
    localStorage.setItem("nick", nick)
    const authToken = window.btoa(`${nick}:${pswd}`)
    localStorage.setItem("authData", authToken)
  }

  private static deleteData() {
    localStorage.removeItem("nick")
    localStorage.removeItem("authData")
    localStorage.removeItem("perm")
  }

  login(credentials: { nick: string, pswd: string }): Observable<Subscription> {
    const sendParams = new HttpParams()
      .append('username', credentials.nick)
      .append('password', credentials.pswd)
    return this.http.post<User>(`${this.apiUrl}/login`, null, {
      params: sendParams,
      withCredentials: true,
      observe: 'response'
    }).pipe(
      map((user) => {
        console.log('sign in user', user)
        AuthService.saveData(credentials.nick, credentials.pswd)
        return this.http.get<Permissions[]>(`${this.apiUrl}/databases/permissions`).subscribe(
          permissions => {
            localStorage.setItem('perm', JSON.stringify(permissions))
            return user
          }
        )
      })
    )
  }

  logout(): Subscription {
    return this.http.post(`${this.apiUrl}/logout`, null).subscribe(
      _ => {
        AuthService.deleteData()
        this.router.navigate(['/login'])
        return null
      }, err => {
        console.log("error during logout - " + err)
        return err
      }
    )
  }

  hasPermission(): boolean {
    // return JSON.parse(localStorage.getItem('perm')).includes(Permissions.)
    return true
  }

}

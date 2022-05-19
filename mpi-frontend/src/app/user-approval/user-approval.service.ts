import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ConfigService} from "../service/config.service";
import {UserInfo} from "../entity/UserInfo";
import {map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserApprovalService {
  constructor(private configService: ConfigService,
              private http: HttpClient) {
  }

  private get apiUrl() {
    return `${this.configService.appUrl}/admin`
  }

  public processUser(nick: string, status: boolean) {
    const sendParams = new HttpParams()
      .append('nick', nick)
      .append('isActivated', status)
    this.http.post(`${this.apiUrl}/processUser`, null, {
      params: sendParams,
      withCredentials: true,
      observe: 'response'
    }).subscribe();
  }

  public getUsers() {
    return this.http.get<UserInfo[]>(this.apiUrl + '/notActivatedUsers')
  }
}

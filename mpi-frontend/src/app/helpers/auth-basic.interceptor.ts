import { Injectable } from '@angular/core'
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http'
import { Observable } from 'rxjs'
import { AuthService } from '../service/auth.service'
import { ConfigService } from '../service/config.service'

@Injectable()
export class AuthBasicInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private appService: ConfigService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.getUser() &&
      request.url.startsWith(this.appService.baseUrl)) {
      request = request.clone(
        {
          setHeaders: {
            Authorization: `Basic ${this.authService.getAuthData()}`
          },
          withCredentials: true
        }
      )
    }

    return next.handle(request)
  }
}

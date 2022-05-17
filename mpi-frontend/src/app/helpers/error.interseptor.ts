import { Injectable } from '@angular/core'
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http'
import { catchError, Observable, throwError } from 'rxjs'
import { AuthService } from '../auth.service'
import { ConfigService } from '../config.service'

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService, private appService: ConfigService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err) => {
        if (err.status === 401) {
          if (this.authService.getUser() &&
            req.url.startsWith(this.appService.baseUrl))
            this.authService.logout()
        }

        const error = err.error?.message || err.error['message'] || err.statusTex
        return throwError(error)
      })
    )
  }
}
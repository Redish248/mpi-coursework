import { Injectable } from '@angular/core'
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router'
import { Observable } from 'rxjs'
import { AuthService } from '../service/auth.service'

@Injectable({
    providedIn: 'root'
})
export class FsbAgentGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (!this.authService.getUser()) {
            this.router.navigate(['/login'], {
                queryParams: {returnUrl: state.url}
            })
            return false
        } else return this.authService.isFsbAgent()
    }
}

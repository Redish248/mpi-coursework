import { Component, OnInit } from '@angular/core'
import { AuthService } from '../service/auth.service'
import { Router } from '@angular/router'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  get user() {
    return this.authService.getUser()
  }

  logout() {
    this.authService.logout()
  }

  isTraveler(): boolean {
    return this.authService.isTraveler()
  }

  isShipOwner(): boolean {
    return this.authService.isShipOwner()
  }

  isCrewManager(): boolean {
    return this.authService.isCrewManager()
  }

  isAdmin(): boolean {
    return this.authService.isAdmin()
  }

  openUserProfile() {
    this.router.navigate(['/profile'])
  }
}

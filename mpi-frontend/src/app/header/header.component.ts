import { Component, OnInit } from '@angular/core'
import { AuthService } from '../service/auth.service'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService) {
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
    return this.authService.hasTravelerPermission()
  }

  isShipOwner(): boolean {
    return this.authService.hasShipOwnerPermissions()
  }

  isCrewManager(): boolean {
    return this.authService.hasCrewOwnerPermissions()
  }
}

import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { UserProfile } from '../../model/UserProfile'
import { AuthService } from '../../../service/auth.service'
import { ProfileService } from '../../profile.service'

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private profileService: ProfileService) {
    this.userProfileForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      birthDate: ['', Validators.required],
      shareContacts: [true, Validators.required],
      email: ['', [Validators.required, Validators.pattern('.+@.+')]],
      phone: ['', [Validators.required, Validators.pattern('\\d{3}-\\d{3}-\\d{2}-\\d{2}')]]
    })
  }

  userProfileForm: FormGroup
  user: UserProfile

  ngOnInit(): void {
  }

  getUserInfo() {
    this.profileService.getShips()

  }

  get shipOwner(): boolean {
    return this.authService.isShipOwner()
  }

  get crewManager(): boolean {
    return this.authService.isCrewManager()
  }


  updateUser() {
    console.log(`update user ${this.user.uid}, new data: `, this.userProfileForm.getRawValue())
  }
}

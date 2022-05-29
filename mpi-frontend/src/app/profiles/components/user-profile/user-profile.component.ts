import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { UserProfile } from '../../model/UserProfile'
import { AuthService } from '../../../service/auth.service'
import { ProfileService } from '../../profile.service'
import { Crew } from '../../model/CrewProfile'
import { Ship } from '../../model/ShipProfile'

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
  openCrewProfileModal = false
  crew: Crew | undefined = undefined
  openShipProfile = false
  ship: Ship | undefined = undefined

  loading = false
  errorMessage: string | undefined = undefined

  ngOnInit(): void {
    this.getUserInfo()
  }

  getUserInfo() {
    this.loading = true
    this.errorMessage = undefined

    this.profileService.getCurrentUserInfo().subscribe(
      (data) => {
        this.loading = false
        this.user = data
        this.fillForm()
      },
      (err) => {
        this.loading = false
        this.errorMessage = err
      }
    )
  }

  private fillForm() {
    this.userProfileForm.reset({
      name: this.user.name,
      surname: this.user.surname,
      email: this.user.email,
      phone: this.user.phone,
      shareContacts: this.user.shareContactInfo,
      birthDate: this.user.birthDate
    })
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

  openCrewProfile() {
    this.loading = true
    this.profileService.getCurrentUserCrew().subscribe(
      (data) => {
        this.loading = false
        this.crew = data
        this.openCrewProfileModal = true
      },
      err => { // check 404
        this.loading = false
        this.crew = undefined
        this.openCrewProfileModal = true // register new crew
      }
    )
  }
}

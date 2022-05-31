import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { ProfileService } from '../../profile.service'
import { Ship } from '../../model/ShipProfile'

@Component({
  selector: 'app-edit-ship-profile',
  templateUrl: './edit-ship-profile.component.html',
  styleUrls: ['./edit-ship-profile.component.css']
})
export class EditShipProfileComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private profileService: ProfileService) {
    this.shipProfileForm = formBuilder.group({
      name: ['', Validators.required],
      speed: ['', Validators.required],
      capacity: ['', Validators.required],
      fuelConsumption: ['', Validators.required],
      length: ['', Validators.required],
      width: ['', Validators.required],
      pricePerDay: ['', Validators.required],
      photo: ['', Validators.required],
      description: ['', Validators.required]
    })
  }

  @Input() modalOpen: boolean
  @Input() shipProfile: Ship | undefined // obj or null (create new)
  @Output() modalClose = new EventEmitter()

  shipProfileForm: FormGroup

  ngOnInit(): void {
    if (this.shipProfile) this.fillForm()
  }

  fillForm() {
    console.log(this.shipProfile)
    this.shipProfileForm.reset({
      name: this.shipProfile?.title,
      speed: this.shipProfile?.speed,
      capacity: this.shipProfile?.capacity,
      fuelConsumption: this.shipProfile?.fuelConsumption,
      length: this.shipProfile?.length,
      width: this.shipProfile?.width,
      pricePerDay: this.shipProfile?.pricePerDay,
      photo: this.shipProfile?.photo,
      description: this.shipProfile?.description
    })
  }

  updateShipProfile() {
    if (!this.shipProfile) return
    console.log(`Update crew to new data: `, this.shipProfileForm.getRawValue())
  }

  registerNewShip() {
    if (this.shipProfile) return
    this.profileService.addShip(this.shipProfileForm.getRawValue()).subscribe(
      data => {
        this.shipProfile = data
        this.fillForm()
      }, error => console.log(error)
    )
  }

  get photo(): string {
    return this.shipProfileForm.getRawValue().photo
  }
}

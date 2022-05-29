import { Component, Input, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'

@Component({
  selector: 'app-edit-crew-profile',
  templateUrl: './edit-crew-profile.component.html',
  styleUrls: ['./edit-crew-profile.component.css']
})
export class EditCrewProfileComponent implements OnInit {
  constructor(private formBuilder: FormBuilder) {
    this.crewProfileForm = formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      shareContacts: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('.+@.+')]],
      phone: ['', [Validators.required, Validators.pattern('\\d{3}-\\d{3}-\\d{2}-\\d{2}')]],

      crew: formBuilder.group({
        teamName: ['', Validators.required],
        photo: ['', Validators.required],
        description: ['', Validators.required],
        pricePerDay: ['', Validators.required],
        members: ['', Validators.required],
        membersNumber: formBuilder.array([this.crewMember])
      })
    })
  }

  @Input() crewProfile: any

  crewProfileForm: FormGroup

  ngOnInit(): void {
    this.fillForm()
  }

  private fillForm() {
    this.crewProfileForm.reset({
      name: this.crewProfile.name,
      surname: this.crewProfile.surname,
      shareContacts: this.crewProfile.shareContacts,
      email: this.crewProfile.email,
      phone: this.crewProfile.phone,
      crew: this.formBuilder.group({
        uid: this.crewProfile.crew.uid,
        teamName: this.crewProfile.crew.teamName,
        photo: this.crewProfile.crew.photo,
        description: this.crewProfile.crew.description,
        pricePerDay: this.crewProfile.crew.pricePerDay,
        membersNumber: this.crewProfile.crew.membersNumber,
        members: this.formBuilder.array([this.fillCrewMembers()])
      })
    })
    this.crewProfileForm.disable()
  }

  private get crewMember(): FormGroup {
    return this.formBuilder.group({
      uid: [''],
      fullName: ['', Validators.required],
      experience: ['', Validators.required]
    })
  }

  private fillCrewMembers() {
    const members = this.crewProfile.crew.members
    return this.formBuilder.array(
      members.map((m) => this.formBuilder.group({
        uid: m.uid,
        fullName: m.fullName,
        experience: m.experience
      }))
    )

  }
}

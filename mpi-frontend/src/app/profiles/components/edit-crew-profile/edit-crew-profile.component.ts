import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms'
import { Crew, CrewMember } from '../../model/CrewProfile'
import { ProfileService } from '../../profile.service'


@Component({
    selector: 'app-edit-crew-profile',
    templateUrl: './edit-crew-profile.component.html',
    styleUrls: ['../../profiles.css', './edit-crew-profile.component.css']
})
export class EditCrewProfileComponent implements OnInit {
    constructor(private formBuilder: FormBuilder,
                private profileService: ProfileService) {
        this.infoUpdated = ""
        this.crewProfileForm = formBuilder.group({
            teamName: ['', Validators.required],
            photo: [''],
            description: ['', Validators.required],
            pricePerDay: ['', Validators.required],
            members: formBuilder.array([this.crewMember])
        })
    }

    @Input() modalOpen: boolean
    @Input() crewProfile: Crew | undefined // obj or null (create new)
    @Output() modalClose = new EventEmitter()

    infoUpdated: string
    crewProfileForm: FormGroup
    afraidPirates: boolean = false

    ngOnInit(): void {
        if (this.crewProfile) this.fillForm()
        this.getAfraid()
    }

    private getAfraid() {
        this.profileService.getAfraid().subscribe(
            afraid => this.afraidPirates = afraid,
            err => console.log(err)
        )
    }

    updateAfraid(value: boolean) {
        this.profileService.updateCrewAfraid(value).subscribe(
            _ => {
                this.getAfraid()
            },
            err => {
                console.log(err)
            }
        )
    }

    private fillForm() {
        this.crewProfileForm.reset({
            teamName: this.crewProfile?.teamName,
            photo: this.crewProfile?.photo,
            description: this.crewProfile?.description,
            pricePerDay: this.crewProfile?.pricePerDay
        })

        this.crewProfileForm.setControl("members", this.fillCrewMembers())
    }

    private get crewMember(): FormGroup {
        return this.formBuilder.group({
            fullName: ['', Validators.required],
            experience: ['', Validators.required]
        })
    }

    private fillCrewMembers(): FormArray {
        const members = this.crewProfile?.members || []
        return this.formBuilder.array(
            members.map((m) => this.formBuilder.group({
                fullName: m.fullName,
                experience: m.experience
            }))
        )
    }

    getMembersArr(): FormArray {
        return this.crewProfileForm.get('members') as FormArray
    }

    addNewMember() {
        this.getMembersArr().push(this.crewMember)
    }

    deleteCrewMember(index: number) {
        if (this.getMembersArr().length > 1)
            this.getMembersArr().removeAt(index)
    }

    updateCrewProfile() {
        this.profileService.updateCrew(this.crewProfileForm.getRawValue()).subscribe(
            data => {
                this.crewProfile = data
                console.log(`Update crew to new data: `, this.crewProfileForm.getRawValue())
                this.infoUpdated = "Данные экипажа обновлены"
            },
            err => console.log(err)
        )
    }

    registerNewCrew() {
        if (this.crewProfile) return
        this.profileService.addCrew(this.crewProfileForm.getRawValue()).subscribe(
            data => {
                this.crewProfile = data
                this.fillForm()
            },
            err => console.log(err)
        )
        console.log(`Update crew to new data: `, this.crewProfileForm.getRawValue())
    }

    get photo(): string {
        return this.crewProfileForm.getRawValue().photo
    }

}

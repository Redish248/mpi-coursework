import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { CrewProfile } from '../../model/CrewProfile'

@Component({
  selector: 'app-view-crew-profile',
  templateUrl: './view-crew-profile.component.html',
  styleUrls: ['../../profiles.css', './view-crew-profile.component.css']
})
export class ViewCrewProfileComponent implements OnInit {

  constructor() {
  }

  @Input() modalOpen: boolean
  @Input() crewProfile: CrewProfile
  @Output() closeModal = new EventEmitter()

  defaultCrewPhotoUrl = "https://avatars.mds.yandex.net/get-ott/2385704/2a000001720da5ff01ebaf599f6af08d0832/1344x756"

  ngOnInit(): void {
  }

}

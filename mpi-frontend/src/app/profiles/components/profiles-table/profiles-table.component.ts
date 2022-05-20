import { Component, Input, OnInit } from '@angular/core'
import { Profile } from '../../model/Profile'
import { Permissions } from '../../../entity/User'

@Component({
  selector: 'app-profiles-table',
  templateUrl: './profiles-table.component.html',
  styleUrls: ['./profiles-table.component.css']
})
export class ProfilesTableComponent implements OnInit {

  constructor() {
  }

  @Input() filteredProfiles: Profile[] // already filtered profiles
  @Input() defaultPhotoUrl: string
  @Input() type: Permissions

  profileOpen: boolean = false

  ngOnInit(): void {
  }

}

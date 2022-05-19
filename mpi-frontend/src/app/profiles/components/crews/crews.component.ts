import { Component, OnInit } from '@angular/core'
import { ProfileService } from '../../profile.service'
import { Profile } from '../../model/Profile'

@Component({
  selector: 'app-crews',
  templateUrl: './crews.component.html',
  styleUrls: ['./crews.component.css']
})
export class CrewsComponent implements OnInit {

  constructor(private profilesService: ProfileService) {
  }

  crews: Profile[] = []
  loading: boolean = false
  errorMessage: string | undefined = undefined

  ngOnInit(): void {
    this.getCrews()
  }

  getCrews() {
    this.loading = true
    this.errorMessage = undefined
    this.profilesService.getCrews().subscribe(
      data => {
        this.loading = false
        this.crews = data
      },
      err => {
        this.loading = false
        this.errorMessage = err
      }
    )
  }
}

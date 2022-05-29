import { Component, OnInit } from '@angular/core'
import { ProfileService } from '../../profile.service'
import { CrewProfile } from '../../model/CrewProfile'
import { CommonService } from '../../../service/commonService'

@Component({
  selector: 'app-crews',
  templateUrl: './crews.component.html',
  styleUrls: ['../../profiles.css', './crews.component.css']
})
export class CrewsComponent implements OnInit {

  constructor(private profilesService: ProfileService) {
  }

  selectedProfileId: CrewProfile | undefined = undefined

  defaultCrewPhotoUrl = "https://avatars.mds.yandex.net/get-ott/2385704/2a000001720da5ff01ebaf599f6af08d0832/1344x756"
  crews: CrewProfile[] = []
  filteredProfiles: CrewProfile[] = []

  minMembersNumber: number = 0
  maxMemberNumber: number = 0
  maxTripNumber: number = 0
  minTripNumber: number = 0
  maxRates: number = 5
  minRates: number = 0

  filter: {
    minMembersNumber: number
    maxMembersNumber: number
    // minTripNumber: number
    minRates: number
  }

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
        this.crews = data.sort((el1, el2) => el1.crew.rates > el2.crew.rates ? 1 : -1)
        this.filteredProfiles = this.crews
        this.countDefaultParams()
        this.initFilter()
      },
      err => {
        this.loading = false
        this.errorMessage = err
      }
    )
  }

  countDefaultParams() {
    this.maxMemberNumber = CommonService.getMax_2(this.crews, "crew", "membersNumber")
    this.minMembersNumber = CommonService.getMin_2(this.crews, "crew", "membersNumber")

    this.maxTripNumber = CommonService.getMax(this.crews, "tripNumber")
    this.minTripNumber = CommonService.getMin(this.crews, "tripNumber")

    this.maxRates = CommonService.getMax(this.crews, "rates")
    this.minRates = CommonService.getMin(this.crews, "rates")
  }

  initFilter() {
    this.filter = {
      maxMembersNumber: this.maxMemberNumber,
      minMembersNumber: this.minMembersNumber,
      // minTripNumber: this.minTripNumber,
      minRates: this.minRates
    }
  }

  filterProfiles() {
    this.filteredProfiles = this.crews.filter(el =>
      el.crew.rates > this.filter.minRates &&
      (el.crew?.membersNumber || -1) > this.filter.minMembersNumber &&
      (el.crew?.membersNumber || Number.MAX_SAFE_INTEGER) < this.filter.maxMembersNumber
    )
  }
}

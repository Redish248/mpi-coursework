import { Component, OnInit } from '@angular/core'
import { Crew } from './model/crew'
import { FsbService } from './fsb.service'
import { Island } from './model/island'

@Component({
  selector: 'app-fsb',
  templateUrl: './fsb.component.html',
  styleUrls: ['./fsb.component.css']
})
export class FsbComponent implements OnInit {
  crews: Crew[]
  islands: Island[]
  loading = false
  alert: string | undefined = undefined

  constructor(private fsbService: FsbService) {
  }

  ngOnInit(): void {
    this.getCrews()
    this.getIslands()
  }

  getCrews() {
    this.loading = true
    this.alert = undefined
    this.fsbService.getCrews().subscribe(
      data => {
        this.loading = false
        this.crews = data
      }, err => {
        this.loading = false
        this.alert = `unable to update crew list - ${err}`
      }
    )
  }

  getIslands() {
    this.loading = true
    this.alert = undefined
    this.fsbService.getIslands().subscribe(
      data => {
        this.loading = false
        this.islands = data
      }, err => {
        this.loading = false
        this.alert = `unable to update crew list - ${err}`
      }
    )
  }

  markCrewAsPirate(crewId: number) {
    this.alert = undefined
    this.loading = true
    this.fsbService.markCrewAsPirate(crewId).subscribe(
      _ => {
        this.loading = false
        this.getCrews()
      }
      ,
      err => {
        this.loading = false
        this.alert = `unable to mark crew as good person - ${err}`
      }
    )
  }

  markCrewAsGoodPerson(crewId: number) {
    this.alert = undefined
    this.loading = true
    this.fsbService.markCrewAsGoodPerson(crewId).subscribe(
      _ => {
        this.loading = false
        this.getCrews()
      }
      ,
      err => {
        this.loading = false
        this.alert = `unable to mark crew as good person - ${err}`
      }
    )
  }


  markIslandAsDangerous(islandId: number) {
    this.alert = undefined
    this.loading = true
    this.fsbService.markIslandAsDangerous(islandId).subscribe(
      _ => {
        this.loading = false
        this.getCrews()
      }
      ,
      err => {
        this.loading = false
        this.alert = `unable to mark island as dangerous - ${err}`
      }
    )
  }

  markIslandAsSafe(islandId: number) {
    this.alert = undefined
    this.loading = true
    this.fsbService.markIslandAsSafe(islandId).subscribe(
      _ => {
        this.loading = false
        this.getCrews()
      },
      err => {
        this.loading = false
        this.alert = `unable to mark island as safe - ${err}`
      }
    )
  }
}

import {Component, OnInit} from '@angular/core'
import {ProfileService} from '../../profile.service'
import {ShipProfile} from '../../model/ShipProfile'
import {CommonService} from '../../../service/commonService'

@Component({
    selector: 'app-ships',
    templateUrl: './ships.component.html',
    styleUrls: ['../../profiles.css', './ships.component.css']
})
export class ShipsComponent implements OnInit {

    constructor(private profilesService: ProfileService) {
        this.errorMessage = undefined
    }

    selectedProfileId: ShipProfile | undefined = undefined

    defaultShipPhotoUrl = "data:image/jpeg;base64,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"
    ships: ShipProfile[] = []
    filteredProfiles: ShipProfile[] = []

    minSpeed: number = 0
    minCapacity: number = 0
    maxCapacity: number = 0
    maxRates: number = 5
    minRates: number = 0

    filter: {
        capacity: number
        minSpeed: number
        minRates: number
    }

    loading: boolean = false
    errorMessage: string | undefined


    ngOnInit(): void {
        this.initFilter()
        this.getShipsProfiles()
    }

    getShipsProfiles() {
        this.loading = true
        this.errorMessage = undefined
        this.profilesService.getShips().subscribe(
            data => {
                this.loading = false
                this.errorMessage = undefined
                this.ships = data.sort((el1, el2) => el1.ship.rates > el2.ship.rates ? 1 : -1)
                this.filteredProfiles = this.ships
                if (this.ships.length > 0) this.countDefaultParams()
            },
            err => {
                this.loading = false
                this.errorMessage = err
            }
        )
    }

    countDefaultParams() {
        this.minSpeed = CommonService.getMin_2(this.ships, "ship", "speed")
        this.minCapacity = CommonService.getMin_2(this.ships, "ship", "capacity")
        this.maxCapacity = CommonService.getMax_2(this.ships, "ship", "capacity")
        this.minRates = CommonService.getMin_2(this.ships, "ship", "rates")
        this.maxRates = CommonService.getMax_2(this.ships, "ship", "rates")

        this.initFilter()
    }

    initFilter() {
        this.filter = {
            capacity: this.minCapacity,
            minSpeed: this.minSpeed,
            minRates: this.minRates
        }
    }

    filterProfiles() {
        this.filteredProfiles = this.ships.filter(sp =>
            sp.ship.speed >= this.filter.minSpeed &&
            sp.ship.capacity == this.filter.capacity &&
            sp.ship.rates >= this.filter.minRates
        )
    }
}


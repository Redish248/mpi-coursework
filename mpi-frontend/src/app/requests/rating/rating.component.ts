import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RequestService} from '../../service/request.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TripRequest} from "../../entity/TripRequest";

@Component({
    selector: 'app-trip-rating',
    templateUrl: './rating.component.html',
    styleUrls: ['./rating.component.css']
})
export class TripRatingComponent implements OnInit {

    @Input() modalOpened: boolean
    @Input() trip: TripRequest
    @Input() ratingMode: boolean
    @Output() closeModal = new EventEmitter<{}>()

    tripRatingForm: FormGroup
    errorMessage: string | undefined

    constructor(private formBuilder: FormBuilder, private requestService: RequestService) {
        this.errorMessage = undefined;
    }

    ngOnInit(): void {
        this.tripRatingForm = this.formBuilder.group({
            tripId: [this.trip.id],
            ship: ['', Validators.required],
            crew: ['', Validators.required]
        })
    }

    rateTrip() {
        this.requestService.rateTrip(this.tripRatingForm.getRawValue()).subscribe(_ => {
                this.trip.isRated = true;
                this.trip.ship.ratesNumber++;
                this.trip.ship.ratesAverage = (this.trip.ship.ratesAverage + this.tripRatingForm.getRawValue().ship) / this.trip.ship.ratesNumber;
                this.trip.crew.ratesNumber++;
                this.trip.crew.ratesAverage = (this.trip.crew.ratesAverage + this.tripRatingForm.getRawValue().crew) / this.trip.crew.ratesNumber;
                this.closeModal.emit();
            },
            err => {
                this.errorMessage = err
            }
        )
    }
}

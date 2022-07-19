import {Component, Input, OnInit} from '@angular/core'

@Component({
    selector: 'app-rating',
    templateUrl: './rating.component.html',
    styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

    constructor() {
    }

    @Input() rating: number

    maxRating: number = 5
    minRating: number = 0

    ngOnInit(): void {
    }

    get color(): string {
        const third = this.maxRating / 3
        switch (true) {
            case this.rating < this.minRating + third :
                return "label label-danger"
            case this.rating >= this.minRating + third && this.rating < this.minRating + 2 * third :
                return "label label-warning"
            case this.rating >= this.minRating + 2 * third:
                return "label label-success"
            default:
                return "label label-info"
        }
    }
}

import {Component, Input, OnInit} from '@angular/core'

@Component({
    selector: 'app-pirate-label',
    templateUrl: './pirate-label.component.html',
    styleUrls: ['./pirate-label.component.css']
})
export class PirateLabelComponent implements OnInit {

    constructor() {
    }

    @Input() isPirate: boolean | undefined

    ngOnInit(): void {
    }

}

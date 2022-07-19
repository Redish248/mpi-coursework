import {Component, Input} from '@angular/core'
import {ClrDatagridFilterInterface} from '@clr/angular'
import {Subject} from 'rxjs'

@Component({
    selector: 'app-select-filter',
    templateUrl: './select-filter.component.html',
    styleUrls: ['./select-filter.component.css']
})
export class SelectFilterComponent implements ClrDatagridFilterInterface<any> {
    private _types: string[] | undefined

    get types() {
        return this._types
    }

    // @ts-ignore
    @Input('clrFilterValue') field: string

    @Input('clrSelectedValue')
    set type(val: any) {
        this._types = this.toArray(val)
    }

    // @ts-ignore
    private toArray(enumme): string[] {
        return Object.keys(enumme)
            .map((key) => enumme[key])
    }

    selected: string[] = []
    changes = new Subject<any>()

    constructor() {
    }

    accepts(item: any): boolean {
        return this.selected.indexOf(item[this.field].toLocaleLowerCase()) > -1
    }

    toggleSelection(event: any) {
        if (event.target.checked) {
            this.selected.push(event.target.value)
        } else {
            const colorName = event.target.value
            const index = this.selected.indexOf(colorName)
            if (index > -1) {
                this.selected.splice(index, 1)
            }
        }
        this.changes.next(true)
    }

    isActive(): boolean {
        return this.selected.length > 0
    }
}

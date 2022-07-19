import {Pipe, PipeTransform} from '@angular/core';
import {TripRequestStatus} from '../entity/TripRequestStatus';

@Pipe({
    name: 'statusPipe'
})
export class StatusPipe implements PipeTransform {

    transform(value: TripRequestStatus, ...args: unknown[]): string {

        if (value.toString() == 'PENDING') {
            return 'В ожидании';
        }
        if (value.toString() == 'APPROVED_BY_BOTH') {
            return 'Одобрено кораблем и командой';
        }
        if (value.toString() == 'APPROVED_BY_CREW') {
            return 'Одобрено командой';
        }
        if (value.toString() == 'APPROVED_BY_SHIP') {
            return 'Одобрено кораблем';
        }
        if (value.toString() == 'CANCELLED') {
            return 'Отменено';
        }
        if (value.toString() == 'COMPLETE') {
            return 'Завершено';
        }
        if (value.toString() == 'REJECTED') {
            return 'Отказано';
        }

        return '';
    }

}

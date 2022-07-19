import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'islandPipe'
})
export class IslandPipe implements PipeTransform {

    private translation = new Map([
        ["rodos", "Родос"],
        ["cyprus", "Кипр"],
        ["circle", "Шар"],
        ["crete", "Крит"],
        ["pear", "Груша"],
        ["kefalonia", "Кефалония"],
        ["cuba", "Куба"],
        ["jamaica", "Ямайка"]
    ]);

    transform(value: string, args?: any): string {
        if (this.translation.has(value)) {
            return this.translation.get(value)!;
        }
        return "ошибка";
    }
}

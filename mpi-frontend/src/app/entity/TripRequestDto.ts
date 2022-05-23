import {Island} from './island';

export class TripRequestDto {
    public from: Island;
    public to: Island;
    public budget: number;
    public startDate: string;
}
import { Crew } from "./Crew";
import { Ship } from "./Ship";

export class Option {
    public ship: Ship;
    public crew: Crew;
    public price: number;
    public startDate: Date;
    public finishDate: Date;
}
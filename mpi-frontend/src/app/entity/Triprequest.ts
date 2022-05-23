import { Crew } from "./Crew";
import { Island } from "./island";
import { Ship } from "./Ship";
import { TripRequestStatus } from "./TripRequestStatus";
import { User } from "./User";

export class TripRequest {
    public id?: number;
    public traveler?: User;
    public dateStart: Date;
    public dateEnd: Date;
    public islandStart: Island;
    public islandEnd: Island;
    public status?: TripRequestStatus;
    public ship: Ship;
    public crew: Crew;
    public cost: number;
}
import { Crew } from "./Crew";
import { Island } from "./island";
import { Ship } from "./Ship";
import { TripRequestStatus } from "./TripRequestStatus";
import { UserInfo } from "./UserInfo";

export class TripRequest {
    public id?: number;
    public traveler?: UserInfo;
    public dateStart: Date;
    public dateEnd: Date;
    public islandStart: Island;
    public islandEnd: Island;
    public status: TripRequestStatus;
    public ship: Ship;
    public crew: Crew;
    public cost: number;
    public isRated: boolean;
}

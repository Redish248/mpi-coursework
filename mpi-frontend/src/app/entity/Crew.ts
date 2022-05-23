import { User } from "./User";

export class Crew {
    public id?: number;
    public teamName: string;
    public crewOwner: User;
    public pricePerDay: number;
    public ratesNumber: number;
    public ratesAverage: number;
}
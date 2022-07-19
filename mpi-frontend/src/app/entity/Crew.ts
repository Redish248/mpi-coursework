import {UserInfo} from './UserInfo';

export class Crew {
    public id?: number;
    public teamName: string;
    public crewOwner: UserInfo;
    public pricePerDay: number;
    public ratesNumber: number;
    public ratesAverage: number;
}

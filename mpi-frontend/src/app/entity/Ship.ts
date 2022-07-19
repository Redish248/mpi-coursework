import {UserInfo} from './UserInfo';

export class Ship {
    public id?: number;
    public name: string;
    public owner: UserInfo;
    public description: string;
    public speed: number;
    public capacity: number;
    public fuelConsumption: number;
    public length: number;
    public width: number;
    public pricePerDay: number;
    public ratesNumber: number;
    public ratesAverage: number;

}

import { Injectable } from '@angular/core';
import { TripRequestDto } from '../entity/Triprequest';

@Injectable({
  providedIn: 'root'
})
export class OptionsService {

  public request: TripRequestDto;

  constructor() { }

}

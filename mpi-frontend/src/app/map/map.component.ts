import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Island } from '../entity/island';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  expeditionControl = new FormGroup({
    from: new FormControl(''),
    to: new FormControl(''),
    budget: new FormControl('')
  });

  public islands: Island[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}

import { Component, OnInit } from '@angular/core';
import { OptionsService } from '../service/options.service';

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent implements OnInit {

  constructor(private optionsService: OptionsService) { }

  ngOnInit(): void {

    console.log(this.optionsService.request);

  }

}

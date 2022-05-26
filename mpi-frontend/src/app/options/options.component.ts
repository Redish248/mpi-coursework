import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Island } from '../entity/island';
import { Option } from '../entity/Option';
import { TripRequest } from '../entity/TripRequest';
import { OptionsService } from '../service/options.service';
import { RequestService } from '../service/request.service';

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent implements OnInit {

  public loading = true;
  public options: Option[];

  private from: Island;
  private to: Island;

  constructor(private optionsService: OptionsService, private router: Router, private requestService: RequestService) { }

  ngOnInit(): void {
    this.options = [];
    if (this.optionsService.request == undefined) {
      this.router.navigateByUrl('/map');
    }
    this.loadOptions();
  }

  loadOptions() {
    this.optionsService.getOptions().subscribe(res => {
      console.log(res);
      this.options = res;
      this.loading = false;

      this.from = this.optionsService.request!.from;
      this.to = this.optionsService.request!.to;
      this.optionsService.request = undefined;
    });
  }

  submit(option: Option) {
    let tripRequest = new TripRequest();
    tripRequest.cost = option.price;
    tripRequest.crew = option.crew;
    tripRequest.dateEnd = option.finishDate;
    tripRequest.dateStart = option.startDate;
    tripRequest.islandEnd = this.to;
    tripRequest.islandStart = this.from;
    tripRequest.ship = option.ship;

    this.requestService.createRequest(tripRequest).subscribe(res => {
      this.router.navigateByUrl('/requests');
    });
  }

}

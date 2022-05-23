import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { Island } from '../entity/island';
import { OptionsService } from '../service/options.service';
import { IslandService } from '../service/island.service';
import { TripRequestDto } from '../entity/TripRequestDto';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  expeditionControl = new FormGroup({
    from: new FormControl(''),
    to: new FormControl(''),
    budget: new FormControl(''),
    startDate: new FormControl('')
  });

  public islands: Island[] = [];
  private clickStatus: number = 0;

  constructor(private islandService: IslandService, private router: Router, private options: OptionsService,
    private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.islandService.loadIslands().subscribe((data:Island[]) => {
      this.islands = data;
    });
  }

  onIslandClick(islandId: number) {
    if (this.clickStatus == 1) {
      this.expeditionControl.get('to')?.setValue(islandId+1);
    } else {
      this.expeditionControl.get('from')?.setValue(islandId+1);
    }
    this.incrementClickStatus();
    this.updateIslands();
  }

  onChangeFrom() {
    this.updateIslands();
  }

  onChangeTo() {
    this.updateIslands();
  }

  submit() {
    let from = this.selectedFromIsland();
    let to = this.selectedToIsland();
    let budget = this.expeditionControl.get('budget')?.value;
    let startingDate = this.expeditionControl.get('startDate')?.value;

    if (from == null || to == null || budget == '' || startingDate == '') {
      this.showAlert("Заполните все поля");
    }
    else if (from == to) {
      this.showAlert("Выберите разные острова");
    }
    else {
      this.options.request = this.buildRequestDto(from, to, budget, startingDate);
      this.router.navigateByUrl('/options')
    }
  }

  buildRequestDto(from: Island, to: Island, budget: number, startingDate: Date): TripRequestDto {
    let request = new TripRequestDto();
    request.budget = budget;
    request.from = from;
    request.to = to;
    request.startDate = this.datePipe.transform(startingDate, 'yyyy-MM-dd')!;
    return request;
  }

  showAlert(text: string) {
    document.getElementById('alertText')!.innerText = text;
    document.getElementById('alert')!.style.display='flex';
  }

  hideAlert() {
    document.getElementById('alert')!.style.display='none';
  }

  updateIslands() {
    this.islands.forEach(island => {
      if (this.selectedToIsland()?.name == island.name || this.selectedFromIsland()?.name == island.name) {
        this.selectIsland(island.name);
      } else {
        this.releaseIsland(island.name);
      }
    })
  }

  selectedFromIsland(): Island | null {
    if (this.expeditionControl.get('from') == null || this.expeditionControl.get('from')!.value == '') {
      return null;
    }
    let island: number = this.expeditionControl.get('from')!.value - 1;
    return this.islands[island];
  }

  selectedToIsland(): Island | null {
    if (this.expeditionControl.get('to') == null || this.expeditionControl.get('to')!.value == '') {
      return null;
    }
    let island:number = this.expeditionControl.get('to')!.value - 1;
    return this.islands[island];
  }
 
  selectIsland(islandName: string) {
    document.getElementById(islandName)?.classList.add('selected');
  }

  releaseIsland(islandName: string) {
    document.getElementById(islandName)?.classList.remove('selected');
  }

  incrementClickStatus() {
    this.clickStatus = (this.clickStatus + 1)%2;
  }

}

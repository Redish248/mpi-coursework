import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectFilterComponent } from './select-filter/select-filter.component';
import { ClrCheckboxModule } from '@clr/angular'



@NgModule({
  declarations: [
    SelectFilterComponent
  ],
  imports: [
    CommonModule,
    ClrCheckboxModule
  ]
})
export class FiltersModule { }

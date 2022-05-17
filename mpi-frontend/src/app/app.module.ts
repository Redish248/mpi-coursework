import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppComponent } from './app.component'
import { LoginComponent } from './login/login.component'
import { MapComponent } from './map/map.component'
import { RouterModule, Routes } from '@angular/router'
import { HeaderComponent } from './header/header.component'
import { ClarityModule } from '@clr/angular'
import { ReactiveFormsModule } from '@angular/forms'
import { ShipHistoryComponent } from './history/ship-history/ship-history.component'
import { HttpClientModule } from '@angular/common/http'
import { CrewHistoryComponent } from './history/crew-history/crew-history.component'
import { TravelerHistoryComponent } from './history/traveler-history/traveler-history.component'
import { ProfileComponent } from './profiles/components/profile/profile.component'
import { ShipsComponent } from './profiles/components/ships/ships.component'
import { CrewsComponent } from './profiles/components/crews/crews.component'

const appRoutes: Routes = [
  {path: '', component: MapComponent},
  {path: 'map', component: MapComponent},
  {path: 'login', component: LoginComponent},
  {path: 'ships', component: ShipsComponent},
  {path: 'crews', component: CrewsComponent},
  {path: 'crew-history', component: CrewHistoryComponent},
  {path: 'traveler-history', component: TravelerHistoryComponent},
  {path: 'ship-history', component: ShipHistoryComponent},
  {path: '**', redirectTo: '/'}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MapComponent,
    HeaderComponent,
    ShipHistoryComponent,
    CrewHistoryComponent,
    TravelerHistoryComponent,
    ProfileComponent,
    ShipsComponent,
    CrewsComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    ClarityModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppComponent } from './app.component'
import { LoginComponent } from './login/login.component'
import { MapComponent } from './map/map.component'
import { RouterModule, Routes } from '@angular/router'
import { HeaderComponent } from './header/header.component'
import { ClarityModule } from '@clr/angular'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { ShipHistoryComponent } from './history/ship-history/ship-history.component'
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http'
import { CrewHistoryComponent } from './history/crew-history/crew-history.component'
import { TravelerHistoryComponent } from './history/traveler-history/traveler-history.component'
import { ProfileComponent } from './profiles/components/profile/profile.component'
import { ShipsComponent } from './profiles/components/ships/ships.component'
import { CrewsComponent } from './profiles/components/crews/crews.component'
import { AuthGuard } from './helpers/auth.guard'
import { AuthBasicInterceptor } from './helpers/auth-basic.interceptor'
import { ErrorInterceptor } from './helpers/error.interseptor'
import { SignupComponent } from "./signup/signup.component"
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AlertComponent } from './alert/alert.component'
import { ProfilesTableComponent } from './profiles/components/profiles-table/profiles-table.component'
import { RatingComponent } from './helpers/rating/rating.component'
import { UserApprovalComponent } from './user-approval/user-approval.component'
import { IslandPipe } from './service/island.pipe';
import { OptionsComponent } from './options/options.component'
import { DatePipe, DecimalPipe } from '@angular/common';
import { RequestsComponent } from './requests/requests.component';
import { StatusPipe } from './service/status.pipe';
import { CdsModule } from '@cds/angular';
import '@cds/core/alert/register.js';
import { CrewProfileComponent } from './profiles/components/crew-profile/crew-profile.component';

const appRoutes: Routes = [
  {path: '', component: MapComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'map', component: MapComponent, canActivate: [AuthGuard]},
  {path: 'approvals', component: UserApprovalComponent, canActivate: [AuthGuard]},
  {path: 'options', component: OptionsComponent, canActivate: [AuthGuard]},
  {path: 'requests', component: RequestsComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: '/'}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MapComponent,
    HeaderComponent,
    ShipHistoryComponent,
    CrewHistoryComponent,
    TravelerHistoryComponent,
    ProfileComponent,
    ShipsComponent,
    CrewsComponent,
    AlertComponent,
    ProfilesTableComponent,
    RatingComponent,
    UserApprovalComponent,
    IslandPipe,
    OptionsComponent,
    RequestsComponent,
    StatusPipe,
    CrewProfileComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    ClarityModule,
    CdsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthBasicInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    DatePipe,
    DecimalPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

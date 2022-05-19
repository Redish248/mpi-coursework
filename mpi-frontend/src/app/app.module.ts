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
import { AuthGuard } from './helpers/auth.guard'
import { AuthBasicInterceptor } from './helpers/auth-basic.interceptor'
import { ErrorInterceptor } from './helpers/error.interseptor'
import {SignupComponent} from "./signup/signup.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'

const appRoutes: Routes = [
  {path: '', component: MapComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'map', component: MapComponent, canActivate: [AuthGuard]},
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
    CrewsComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    ClarityModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthBasicInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

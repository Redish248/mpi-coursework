import {LOCALE_ID, NgModule} from '@angular/core'
import {BrowserModule} from '@angular/platform-browser'

import {AppComponent} from './app.component'
import {LoginComponent} from './login/login.component'
import {MapComponent} from './map/map.component'
import {RouterModule, Routes} from '@angular/router'
import {HeaderComponent} from './header/header.component'
import {ClarityModule} from '@clr/angular'
import {FormsModule, ReactiveFormsModule} from '@angular/forms'
import {ShipHistoryComponent} from './history/ship-history/ship-history.component'
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http'
import {CrewHistoryComponent} from './history/crew-history/crew-history.component'
import {TravelerHistoryComponent} from './history/traveler-history/traveler-history.component'
import {ShipsComponent} from './profiles/components/ships/ships.component'
import {CrewsComponent} from './profiles/components/crews/crews.component'
import {AuthGuard} from './helpers/auth.guard'
import {AuthBasicInterceptor} from './helpers/auth-basic.interceptor'
import {ErrorInterceptor} from './helpers/error.interseptor'
import {SignupComponent} from "./signup/signup.component"
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import {AlertComponent} from './alert/alert.component'
import {RatingComponent} from './helpers/rating/rating.component'
import {UserApprovalComponent} from './user-approval/user-approval.component'
import {IslandPipe} from './service/island.pipe'
import {OptionsComponent} from './options/options.component'
import {DatePipe, DecimalPipe} from '@angular/common'
import {RequestsComponent} from './requests/requests.component'
import {StatusPipe} from './service/status.pipe'
import {CdsModule} from '@cds/angular'
import '@cds/core/alert/register.js'
import {ViewCrewProfileComponent} from './profiles/components/view-crew-profile/view-crew-profile.component'
import {EditCrewProfileComponent} from './profiles/components/edit-crew-profile/edit-crew-profile.component'
import {PirateLabelComponent} from './helpers/pirate-label/pirate-label.component'
import {UserProfileComponent} from './profiles/components/user-profile/user-profile.component'
import {AuthAdminGuard} from './helpers/auth-admin.guard';
import {EditShipProfileComponent} from './profiles/components/edit-ship-profile/edit-ship-profile.component';
import {ViewShipProfileComponent} from './profiles/components/view-ship-profile/view-ship-profile.component'
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/ru';
registerLocaleData(localeFr);

const appRoutes: Routes = [
    {path: '', component: MapComponent, canActivate: [AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: 'map', component: MapComponent, canActivate: [AuthGuard]},
    {path: 'approvals', component: UserApprovalComponent, canActivate: [AuthAdminGuard]},
    {path: 'options', component: OptionsComponent, canActivate: [AuthGuard]},
    {path: 'requests', component: RequestsComponent, canActivate: [AuthGuard]},
    {path: 'profile', component: UserProfileComponent, canActivate: [AuthGuard]},
    {path: 'ships', component: ShipsComponent, canActivate: [AuthGuard]},
    {path: 'crews', component: CrewsComponent, canActivate: [AuthGuard]},
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
        ShipsComponent,
        CrewsComponent,
        AlertComponent,
        RatingComponent,
        UserApprovalComponent,
        IslandPipe,
        OptionsComponent,
        RequestsComponent,
        StatusPipe,
        ViewCrewProfileComponent,
        EditCrewProfileComponent,
        PirateLabelComponent,
        UserProfileComponent,
        EditShipProfileComponent,
        ViewShipProfileComponent
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
        AuthAdminGuard,
        {provide: HTTP_INTERCEPTORS, useClass: AuthBasicInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
        { provide: LOCALE_ID, useValue: 'ru-RU'},
        DatePipe,
        DecimalPipe
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppComponent } from './app.component'
import { LoginComponent } from './login/login.component'
import { MapComponent } from './map/map.component'
import { RouterModule, Routes } from '@angular/router'
import { HeaderComponent } from './header/header.component'
import { ClarityModule } from '@clr/angular'
import { ReactiveFormsModule } from '@angular/forms'
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http'
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
    HeaderComponent
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

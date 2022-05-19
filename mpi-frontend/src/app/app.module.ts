import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppComponent } from './app.component'
import { LoginComponent } from './login/login.component'
import { MapComponent } from './map/map.component'
import { RouterModule, Routes } from '@angular/router'
import { HeaderComponent } from './header/header.component'
import { ClarityModule } from '@clr/angular'
import { ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import {SignupComponent} from "./signup/signup.component";

const appRoutes: Routes = [
  {path: '', component: MapComponent},
  {path: 'map', component: MapComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
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
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

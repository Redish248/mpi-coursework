import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
class ConfigService {

  constructor() {
  }

  get baseUrl(): string {
    return 'http://localhost:8088'
  }

  get appUrl(): string {
    return 'http://localhost:8088/app' // fixme later
  }
}
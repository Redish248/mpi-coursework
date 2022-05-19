import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() {
  }

  get baseUrl(): string {
    return 'http://localhost:8088'
  }

  get appUrl(): string {
    return 'http://localhost:8088/mpi' // fixme later
  }
}
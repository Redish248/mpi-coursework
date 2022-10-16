import { Injectable } from '@angular/core'

@Injectable({
    providedIn: 'root'
})
export class ConfigService {

    constructor() {
    }

    // private url = 'http://localhost:8088'
    private url = 'http://localhost:80'

    get baseUrl(): string {
        return this.url
    }

    get appUrl(): string {
        return `${this.url}/mpi`
    }
}

import {Component, OnInit} from '@angular/core'
import {FormBuilder, FormGroup, Validators} from '@angular/forms'
import {AuthService} from '../service/auth.service'
import {ActivatedRoute, Router} from '@angular/router'
import {first} from 'rxjs'

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    constructor(private formBuilder: FormBuilder,
                private authService: AuthService,
                private route: ActivatedRoute,
                private router: Router
    ) {
        this.loginForm = this.formBuilder.group({
            nick: ['', Validators.required],
            pswd: ['', Validators.required]
        })
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/'
        this.errorMessage = undefined
        this.submitMessage = undefined

        if (this.authService.getUser()) {
            this.router.navigate(['/'])
        }
    }

    loginForm: FormGroup
    returnUrl: string
    errorMessage: string | undefined
    submitMessage: string | undefined

    registrationMode: boolean = false

    ngOnInit(): void {
    }

    submitLogin() {
        this.errorMessage = undefined
        this.login(this.loginForm?.getRawValue())
    }

    login(data: { nick: string, pswd: string }) {
        this.submitMessage = undefined
        this.submitMessage = undefined
        this.authService.login(data)
            .pipe(first())
            .subscribe(
                _ => {
                    this.router.navigate([this.returnUrl])
                },
                _ => {
                    this.errorMessage = "Введены неправильные данные или пользователь ещё не был активирован администратором"
                }
            )
    }

    signup() {
        this.submitMessage = undefined
        this.registrationMode = false
        this.router.navigate(['/'])
    }

}

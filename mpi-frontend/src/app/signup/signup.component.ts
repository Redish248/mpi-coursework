import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import {FormBuilder, FormGroup, Validators} from '@angular/forms'
import {AuthService} from '../service/auth.service'
import {SignupService} from "./signup.service"
import {Permissions} from '../entity/User'

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
    @Input() modalOpened: boolean
    @Input() registrationMode: boolean
    @Input() alertLogin: boolean
    @Output() alertLoginChange = new EventEmitter<boolean>();
    @Output() closeModal = new EventEmitter<{ nick: string, pswd: string }>()

    constructor(private formBuilder: FormBuilder,
                private signupService: SignupService,
                private authService: AuthService
    ) {
        this.signupForm = this.formBuilder.group({
            name: ['', Validators.required],
            surname: ['', Validators.required],
            password: ['', Validators.required],
            nick: ['', [Validators.required, Validators.pattern("[A-Za-z0-9]*")]],
            email: ['', Validators.required],
            phone: ['', Validators.required],
            birthDate: ['', Validators.required],
            userType: ['TRAVELER', Validators.required]
        })
    }

    signupForm: FormGroup
    errorMessage: string | undefined
    loading: boolean = false
    Roles = Permissions

    ngOnInit(): void {
    }

    signup() {
        this.loading = true
        this.errorMessage = undefined
        this.alertLoginChange.emit(false)
        if (this.signupForm.getRawValue().birthDate === '' ||
            this.signupForm.getRawValue().email === '' ||
            this.signupForm.getRawValue().name === '' ||
            this.signupForm.getRawValue().nick === '' ||
            this.signupForm.getRawValue().password === '' ||
            this.signupForm.getRawValue().phone === '' ||
            this.signupForm.getRawValue().surname === ''
        ) {
            this.errorMessage = "Заполните все поля!"
        } else {
            this.signupService.signup(this.signupForm.getRawValue()).subscribe(
                _ => {
                    this.registrationMode = false
                    this.modalOpened = false
                    // login after success registration
                    if (this.signupForm.value.userType == 'TRAVELER') {
                        this.authService.login({
                            nick: this.signupForm.value.nick,
                            pswd: this.signupForm.value.password
                        }).subscribe(
                            _ => {
                                this.loading = false
                                this.closeModal.emit({
                                    nick: this.signupForm.value.nick,
                                    pswd: this.signupForm.value.password
                                })
                            },
                            _ => {
                                this.loading = false
                                this.errorMessage = "Ошибка входа в систему"
                            }
                        )
                    } else {
                        this.alertLoginChange.emit(true)
                    }
                }, error => {
                    this.loading = false
                    if (error.includes('register')) {
                        this.errorMessage = "Такой пользователь уже существует в системе"
                    } else {
                        this.errorMessage = "Ошибка регистрации"
                    }
                })
        }
    }

}

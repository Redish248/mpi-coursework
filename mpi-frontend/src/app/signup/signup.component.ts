import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import {FormBuilder, FormGroup, Validators} from '@angular/forms'
import {AuthService} from '../service/auth.service'
import {SignupService} from "./signup.service";
import {Role, Gender} from "../common_model";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  @Input() modalOpened: boolean
  @Output() closeModal = new EventEmitter()

  constructor(private formBuilder: FormBuilder,
              private signupService: SignupService,
              private authService: AuthService
  ) {
  }

  signupForm: FormGroup
  errorMessage: string = ""
  loading: boolean = false
  Role: Role
  /*Gender: Gender*/

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      password: ['', Validators.required],
      nick: ['', [Validators.required, Validators.pattern("[A-Za-z]")]],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      userRole: ['traveler', Validators.required]
    })
  }

  signup() {
    this.loading = true
    this.errorMessage = ""
    this.signupService.signup(this.signupForm.getRawValue()).subscribe(
      _ => {
        // login after success registration
        this.authService.login({
          nick: this.signupForm.value.nick,
          pswd: this.signupForm.value.password
        }).subscribe(
          _ => {
            this.loading = false
            this.closeModal.emit({
              username: this.signupForm.value.nick,
              password: this.signupForm.value.password
            })
            this.modalOpened = false
          },
          err => {
            this.loading = false
            this.errorMessage = err
          }
        )
      }, err => {
        this.loading = false
        this.errorMessage = err
      })
  }

}

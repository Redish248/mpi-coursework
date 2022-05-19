import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { AuthService } from '../service/auth.service'
import { SignupService } from "./signup.service"
import { Permissions } from '../entity/User'
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  @Input() modalOpened: boolean
  @Output() closeModal = new EventEmitter<{ nick: string, pswd: string }>()

  constructor(private formBuilder: FormBuilder,
              private signupService: SignupService,
              private authService: AuthService,
              private router: Router
  ) {
  }

  signupForm: FormGroup
  errorMessage: string = ""
  loading: boolean = false
  Roles = Permissions

  /*Gender: Gender*/

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      password: ['', Validators.required],
      nick: ['', [Validators.required, Validators.pattern("[A-Za-z]")]],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      birthDate: ['', Validators.required],
      userType: ['TRAVELER', Validators.required]
    })
  }

  signup() {
    this.loading = true
    this.errorMessage = ""
    this.signupService.signup(this.signupForm.getRawValue()).subscribe(
      _ => {
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
              this.modalOpened = false
            },
            err => {
              this.loading = false
              this.errorMessage = err
            }
          )
        } else {
          this.router.navigate(['/'])
        }
      }, err => {
        this.loading = false
        this.errorMessage = err
      })
  }

}

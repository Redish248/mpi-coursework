import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { AuthService } from '../auth.service'
import { ActivatedRoute, Router } from '@angular/router'
import { first } from 'rxjs'

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

    if (this.authService.getUser()) {
      this.router.navigate(['/'])
    }
  }

  loginForm: FormGroup
  returnUrl: string
  errorMessage: string | undefined

  ngOnInit(): void {
  }

  submitLogin() {
    this.errorMessage = undefined
    this.authService.login(this.loginForm?.getRawValue())
      .pipe(first())
      .subscribe(
        _ => {
          this.router.navigate([this.returnUrl])
        },
        err => this.errorMessage = err
      )
  }

}

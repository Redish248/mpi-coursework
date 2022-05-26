import { Component, OnInit } from '@angular/core';
import {UserInfo} from "../entity/UserInfo";
import {UserApprovalService} from "./user-approval.service";

@Component({
  selector: 'app-user-approval',
  templateUrl: './user-approval.component.html',
  styleUrls: ['./user-approval.component.css']
})
export class UserApprovalComponent implements OnInit {
  users: UserInfo[] = []
  loading: boolean = false
  errorMessage: string = ""

  constructor(public userApproval: UserApprovalService) { }

  ngOnInit(): void {
    this.getAllNotActivatedUsers()
  }

  getAllNotActivatedUsers() {
    this.loading = true
    this.errorMessage = ""

    this.userApproval.getUsers().subscribe(
      data => {
        this.loading = false
        this.users = data
      },
      err => {
        this.loading = false
        this.errorMessage = err
      }
    )
  }

  processUser(nick: string, status: boolean) {
    this.userApproval.processUser(nick, status).subscribe(_ =>
      this.getAllNotActivatedUsers()
    );
  }

  parseUserType(type: string) {
    switch (type) {
      case "CREW_MANAGER" : return "Владелец экипажа"
      case "SHIP_OWNER" : return "Капитан корабля"
    }
    return ""
  }

}

import { Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs';
import { TripRequest } from '../entity/TripRequest';
import { AuthService } from '../service/auth.service';
import { RequestService } from '../service/request.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  public activeRequests: TripRequest[];
  public completedRequests: TripRequest[];
  public cancelledRequests: TripRequest[];

  constructor(private requestService: RequestService, private auth: AuthService) { }

  ngOnInit(): void {
    this.refreshRequests();
  }

  reject(request: TripRequest) {
    this.requestService.rejectRequest(request).subscribe(res => {
      this.refreshRequests();
    });
  }

  approve(request: TripRequest) {
    this.requestService.approveRequest(request).subscribe(res => {
      this.refreshRequests();
    },
    err => {
      console.log("kek");
      this.showAlert('Это путешествие перескается по датам с другой одобренной или завершенной заявкой');
    });
  }

  deleteRequest(request: TripRequest) {
    this.requestService.deleteReqeust(request).subscribe(res => {
      this.refreshRequests();
    });
  }

  cancel(request: TripRequest) {
    this.requestService.cancelRequest(request).subscribe(res => {
      this.refreshRequests();
    })
  }

  refreshRequests() {
    this.requestService.getPendingRequests().subscribe(res => {
      this.activeRequests = res;
    });
    this.requestService.getCancelledRequests().subscribe(res => {
      this.cancelledRequests = res;
    })
    this.requestService.getCompleteRequests().subscribe(res => {
      this.completedRequests = res;
    });
  }

  canReject(request: TripRequest): boolean {
    if (this.auth.getUser()?.nickname == request.traveler?.nick) {
      return false;
    }
    return request.status.toString() != 'REJECTED' && request.status.toString() != 'CANCELLED';
  }

  canCancel(request: TripRequest): boolean {
    return this.auth.getUser()?.nickname == request.traveler?.nick && request.status.toString() != 'CANCELLED';
  }

  canDelete(request: TripRequest): boolean {
    return this.auth.getUser()?.nickname == request.traveler?.nick;
  }

  canApprove(request: TripRequest): boolean {
    return this.auth.getUser()?.nickname == request.traveler?.nick && request.status.toString() == 'APPROVED_BY_BOTH' ||
            this.auth.getUser()?.nickname == request.crew.crewOwner.nick && this.canBeApprovedByCrew(request) ||
            this.auth.getUser()?.nickname == request.ship.owner.nick && this.canBeApprovedByShip(request);
  }

  canBeApprovedByCrew(request: TripRequest): boolean {
    return request.status.toString() == 'PENDING' || request.status.toString() == 'APPROVED_BY_SHIP';
  }

  canBeApprovedByShip(request: TripRequest): boolean {
    return request.status.toString() == 'PENDING' || request.status.toString() == 'APPROVED_BY_CREW';
  }

  showAlert(text: string) {
    document.getElementById('alertText')!.innerText = text;
    document.getElementById('alert')!.style.display='flex';
  }

  hideAlert() {
    document.getElementById('alert')!.style.display='none';
  }

}

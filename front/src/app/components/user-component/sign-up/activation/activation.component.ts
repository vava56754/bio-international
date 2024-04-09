import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.scss']
})
export class ActivationComponent {
  code: string = '';

  constructor(private userService: UserService, private router: Router){

  }

  activate() {
    this.userService.activate(this.code).subscribe({
      next: ()=> {
        this.router.navigate(['/login']);
      },
      error: (e) => console.log(LOG_MESSAGES.code.error, e)
    })
  }
}

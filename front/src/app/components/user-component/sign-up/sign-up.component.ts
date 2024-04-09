import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { FRANCE } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { CountryService } from 'src/app/services/country/country.service';
import { User } from 'src/app/models/user/user.model';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit, OnDestroy{
  user: User = {} as User;
  username: string="";
  password: string="";
  confirmPassword: string="";
  errorMessage: string = ""; 
  countries: string[] = [];
  cities: string[] = [];
  subscriptions : Subscription[] = [];
  loading: boolean = false;
  constructor(private userService: UserService, private router: Router, private countryService: CountryService) {

  }

  ngOnInit(): void {
    this.subscriptions.push( this.getCountry(), this.getCities(FRANCE))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getCities(country: string): Subscription {
    this.loading = true;
    return this.countryService.getCity(country).subscribe({
      next: (data) => {
        this.loading = false;
        this.cities = data
      },
      error: (e) => console.error(LOG_MESSAGES.cityRetrieval.error, e)
    })
  }

  getCountry(): Subscription {
    return this.countryService.getCountries().subscribe({
      next: (res) => {
        this.countries = res
      },
      error: (e) => console.error(LOG_MESSAGES.countryRetrieval.error, e)
    });
  }

  sign(form: NgForm): void {
    if(form.valid){
      this.loading = true;
      this.userService.sign(this.user).subscribe({
        next: () => {
          this.router.navigate(['/activation']);
        },
        error: (e) => {
          console.error(LOG_MESSAGES.sign.error, e);
          this.errorMessage = MESSAGE.SIGN.FAIL;
          this.loading = false;
        },complete: () => {
          this.loading = false;
          
        }
      });
    } else {
      this.errorMessage = MESSAGE.SIGN.INVALID_FORM;
    }
  }
}

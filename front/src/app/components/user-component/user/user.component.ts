import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FRANCE, PASSWORD, UPDATE_PASSWORD } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { User } from 'src/app/models/user/user.model';
import { CountryService } from 'src/app/services/country/country.service';
import { AuthService } from 'src/app/services/login/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy{
  user: User = {} as User;
  userUpdate: User = {} as User;
  userCurrentPassword: string = "";
  userCurrentMail: string = "";
  userPasswordUpdate: string = "";
  confirmPassword: string = "";
  displayUserName: boolean = false;
  displayUserFirstName: boolean = false;
  displayUserPhone: boolean = false;
  displayUserMail: boolean = false;
  displayUserAdress: boolean = false;
  displayUserPassword: boolean = false;
  loading: boolean = false;
  countries: string[] = [];
  cities: string[] = [];
  private subscriptions : Subscription[] = []
  errorMessage: string = ""; 

  constructor(private userService: UserService,  private countryService: CountryService, private authService: AuthService, private router: Router){

  }

  ngOnInit(): void {
    this.subscriptions.push(this.getCities(FRANCE), this.getCountry());
    this.subscriptions.push(this.getUser());

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getUser(): Subscription {
    return this.userService.getUserById().subscribe({
      next: (user) => {
        this.user = user;
        this.userUpdate.userCity = this.user.userCity
        this.userUpdate.userCountry = this.user.userCountry
        this.userCurrentMail = this.user.userMail;
      },
      error: (e) => {
        console.error(LOG_MESSAGES.userRetrieval.error, e)
      }
    })
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

  toggleDisplayProperty(propertyName: string): void {
    (this as any)[propertyName] = !(this as any)[propertyName];
  }

  setUserProperty(id: string, property: string, value: any): void {
    if (Object.prototype.hasOwnProperty.call(this.user, property)) {
      (this.user as any)[property as keyof User] = value;
      this.disableEditing(id);
      this.updateUser();
    } else {
      console.error(`La propriété ${property} n'existe pas dans l'objet User.`);
    }
  }

  resetUserProperty(property: string): void {
    (this.userUpdate as any)[property as keyof User] = (this.user as any)[property as keyof User];
  }

  displayUserAdressEditButton(): void {
    this.displayUserAdress = !this.displayUserAdress;

  }

  setUserAdress(id: string): void {
    this.user.userAddress =  this.userUpdate.userAddress;
    this.user.userPostalCode =  this.userUpdate.userPostalCode;
    this.user.userCity =  this.userUpdate.userCity;
    this.user.userCountry =  this.userUpdate.userCountry;
    this.disableEditing(id)
    this.updateUser();
  }

  resetUserAdress(): void {
    this.userUpdate.userAddress = this.user.userAddress;
    this.userUpdate.userPostalCode = this.user.userPostalCode;
    this.userUpdate.userCity = this.user.userCity;
    this.userUpdate.userCountry = this.user.userCountry;
  }


  enableEditing(id: string): void {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.removeAttribute("readonly");
    }

  }

  enableAllEditing(inputElements: HTMLInputElement[]): void {
    inputElements.forEach(inputElement => {
        if (inputElement != null) {
            inputElement.removeAttribute("readonly");
        }
    });
  }

  disableAllEditing(inputElements: HTMLInputElement[]): void {
    inputElements.forEach(inputElement => {
        if (inputElement != null) {
            inputElement.setAttribute("readonly", "true")
        }
    });
  }

  disableEditing(id: string) {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.setAttribute("readonly", "true")
    }
  }

  updateUser() {
    this.userService.updateUserInfo(this.user).subscribe({
      next: (res) => {
        this.getUser();
        if(this.userCurrentMail != this.user.userMail) {
          this.authService.logout().subscribe({
            next: (response) => {
              console.log('Logout Successful:', response);
              localStorage.clear()
            },
            error: (e) => {
              console.error('Logout Error:', e)
              localStorage.clear()
              this.router.navigate(['/'])
            },
            complete: () => {
              this.router.navigate(['/'])
            }
          });
        }       
      },
      error: (e) => console.log(LOG_MESSAGES.update.error, e)
    })
  }

  updatePassword(oldPassword: string, newPassword: string) {
    let data = new FormData();
    data.append(PASSWORD, oldPassword)
    data.append(UPDATE_PASSWORD, newPassword)
    this.userService.updatePassword(data).subscribe({
      next: (res) => {
        this.getUser();         
      },
      error: (e) => {
        console.log(LOG_MESSAGES.update.error, e)
        this.errorMessage="mot de passe incorrecte"
      }
      });
    }
}

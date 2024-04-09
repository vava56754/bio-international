export class User {
    userId: number;
    userName: string;
    userPhone: string;
    userFirstName: string;
    userAddress: string;
    userPostalCode: string;
    userCity: string;
    userCountry: string;
    userMail: string;
    userPassword: string;


    constructor(id: number, name: string, phone: string, firstName: string, address: string, postalCode: string, city: string, country: string, mail: string, password: string) {
        this.userId = id;
        this.userName = name;
        this.userPhone = phone;
        this.userFirstName = firstName;
        this.userAddress = address;
        this.userPostalCode = postalCode;
        this.userCity = city;
        this.userCountry = country;
        this.userMail = mail;
        this.userPassword = password;
    }
}

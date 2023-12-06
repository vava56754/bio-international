import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HouseComponent } from './components/house-component/house/house.component';
import { WelcomeComponent } from './welcome/welcome.component';

import { FooterComponent } from './components/footer/footer.component';
import { ProductComponent } from './components/product-component/product/product.component';
import { UserComponent } from './components/user-component/user/user.component';
import { ProcurementComponent } from './components/procurement-component/procurement/procurement.component';
import { WhoComponent } from './components/who/who.component';
import { ContactComponent } from './components/static/contact/contact.component';
import { LegalMentionComponent } from './components/static/legal-mention/legal-mention.component';
import { ForWhoComponent } from './components/for-who/for-who.component';
import { ProductDetailComponent } from './components/product-component/product-detail/product-detail.component';
import { HouseDetailComponent } from './components/admin-component/admin-house/house-detail/house-detail.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/user-component/login/login.component';
import { AuthGuard } from './auth.guard';
import { AdminComponent } from './components/admin-component/admin/admin.component';
import { AdminProductComponent } from './components/admin-component/admin-product/admin-product.component';
import { AdminHouseComponent } from './components/admin-component/admin-house/admin-house.component';
import { AdminTypeBodyComponent } from './components/admin-component/admin-type-body/admin-type-body.component';
import { HouseCreateComponent } from './components/admin-component/admin-house/house-create/house-create.component';
import { AdminProductDetailComponent } from './components/admin-component/admin-product/admin-product-detail/admin-product-detail.component';
import { ProductCreateComponent } from './components/admin-component/admin-product/product-create/product-create.component';
import { SignUpComponent } from './components/user-component/sign-up/sign-up.component';
import { ActivationComponent } from './components/user-component/sign-up/activation/activation.component';
import { ProductSearchComponent } from './components/product-component/product-search/product-search.component';
import { BasketComponent } from './components/procurement-component/basket/basket.component';
import { AdminProcurementComponent } from './components/admin-component/admin-procurement/admin-procurement.component';
import { AdminProcurementDetailComponent } from './components/admin-component/admin-procurement/admin-procurement-detail/admin-procurement-detail.component';
import { MenuComponent } from './components/menu/menu.component';
import { PictureContentComponent } from './components/picture-content/picture-content.component';
import { ProcurementViewComponent } from './components/procurement-component/procurement-view/procurement-view.component';
import { NotificationComponent } from './components/notification/notification.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
const routes: Routes  = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-product',
    component: AdminProductComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-product-create',
    component: ProductCreateComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-house',
    component: AdminHouseComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-house-create',
    component: HouseCreateComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-type-body',
    component: AdminTypeBodyComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-house/:houseId',
    component: HouseDetailComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-product/:productId',
    component: AdminProductDetailComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-procurement/:procurementId',
    component: AdminProcurementDetailComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'admin-procurement',
    component: AdminProcurementComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] }, 
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['USER', 'ADMIN'] },
  },
  {
    path: 'procurement', 
    component: ProcurementComponent,
    canActivate: [AuthGuard],
    data: { roles: ['USER', 'ADMIN'] },
  },
  {
    path: 'procurement-detail/:procurementId', 
    component: ProcurementViewComponent,
    canActivate: [AuthGuard],
    data: { roles: ['USER', 'ADMIN'] },
  },
  {path: 'not-found', component: NotFoundComponent},
  {path: 'unauthorized', component: UnauthorizedComponent},
  {path: 'login', component: LoginComponent},
  {path: 'product/:productId', component: ProductDetailComponent},
  {path: 'for-who' , component: ForWhoComponent},
  {path: 'who' , component: WhoComponent},
  {path: 'products', component: ProductComponent},
  {path: 'products-search/:namesearch', component: ProductSearchComponent},
  {path: 'houses', component: HouseComponent},
  {path: 'contact', component: ContactComponent},
  {path: 'mention', component: LegalMentionComponent},
  {path: '', component: WelcomeComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'activation', component: ActivationComponent},
  {path: 'basket', component: BasketComponent},
  {path: '**', redirectTo: '/not-found'},
]


@NgModule({
  declarations: [
    AppComponent,
    HouseComponent,
    WelcomeComponent,
    FooterComponent,
    ProductComponent,
    UserComponent,
    ProcurementComponent,
    WhoComponent,
    ContactComponent,
    LegalMentionComponent,
    ForWhoComponent,
    ProductDetailComponent,
    HouseDetailComponent,
    LoginComponent,
    AdminComponent,
    AdminProductComponent,
    AdminHouseComponent,
    AdminTypeBodyComponent,
    HouseCreateComponent,
    AdminProductDetailComponent,
    ProductCreateComponent,
    SignUpComponent,
    ActivationComponent,
    ProductSearchComponent,
    BasketComponent,
    AdminProcurementComponent,
    AdminProcurementDetailComponent,
    MenuComponent,
    PictureContentComponent,
    ProcurementViewComponent,
    NotificationComponent,
    ConfirmDialogComponent,
    NotFoundComponent,
    UnauthorizedComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  exports: [
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

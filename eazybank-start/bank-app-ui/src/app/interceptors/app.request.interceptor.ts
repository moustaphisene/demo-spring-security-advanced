import { Injectable } from '@angular/core';
import { HttpInterceptor,HttpRequest,HttpHandler,HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import {Router} from '@angular/router';
import {tap} from 'rxjs/operators';
import { User } from 'src/app/model/user.model';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  user = new User();
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // Lecture de l'utilisateur dans le sessionStorage
    let httpHeaders = new HttpHeaders();

    if(sessionStorage.getItem('userdetails')){
      this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    }
    // Ajout de l'en-tête Authorization si utilisateur trouvé
    if(this.user && this.user.password && this.user.email){
      httpHeaders = httpHeaders.set('Authorization', 'Basic ' + window.btoa(this.user.email + ':' + this.user.password));
    }


    // Lecture de l'header XSRF-TOKEN et l'ajout à la requête.  Ajout du token CSRF depuis le cookie XSRF-TOKEN
    let xsrfToken = sessionStorage.getItem("XSRF-TOKEN");
    if(xsrfToken){
      httpHeaders = httpHeaders.set('X-XSRF-TOKEN', xsrfToken);
    }
    // Ajout de l'en-tête standard XMLHttpRequest
    httpHeaders = httpHeaders.set('X-Requested-With', 'XMLHttpRequest');


    //httpHeaders = httpHeaders.append('X-Requested-With', 'XMLHttpRequest');
    const xhr = req.clone({
      headers: httpHeaders,
      withCredentials: true
    });

  return next.handle(xhr).pipe(tap(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.router.navigate(['dashboard']);
        }
      }));
  }
// Lecture d'un cookie donné par son nom
  private getCookie(name: string): string | null {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') c = c.substring(1);
      if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length);
    }
    return null;
  }

}

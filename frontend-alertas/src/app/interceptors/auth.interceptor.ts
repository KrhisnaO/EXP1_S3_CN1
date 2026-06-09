import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { from, of, switchMap, catchError } from 'rxjs';
import { environment } from '../../environments/environment';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (!req.url.startsWith(environment.apiUrl)) {
    return next(req);
  }

  const msal = inject(MsalService);
  const account = msal.instance.getActiveAccount()
    ?? msal.instance.getAllAccounts()[0];

  if (!account) {
    return next(req);
  }

  return from(
    msal.instance.acquireTokenSilent({
      account,
      scopes: environment.loginRequest.scopes,
    })
  ).pipe(
    switchMap(result => {
      const token = result.accessToken;
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      return next(authReq);
    }),
    catchError(() => {
      return next(req);
    })
  );
};

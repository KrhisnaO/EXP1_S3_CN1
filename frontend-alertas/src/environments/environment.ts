export const environment = {
  production: false,

  apiUrl: 'http://localhost:8080/api',

  msalConfig: {
    auth: {
      clientId: 'XXXXX',
      authority: 'https://XXXXXXX',
      knownAuthorities: ['XXXXXX'],
      redirectUri: 'http://localhost:4200',
      postLogoutRedirectUri: 'http://localhost:4200',
    },
  },

  loginRequest: {
    scopes: ['openid', 'profile'],
  },
};

export const environment = {
  production: false,

  apiUrl: 'http://localhost:8080/api',

  msalConfig: {
    auth: {
      clientId: '7103a40c-5cf9-42dd-afb9-0a6811a014ef',
      authority: 'https://cn01duoc2026.b2clogin.com/cn01duoc2026.onmicrosoft.com/B2C_1_cn1_login',
      knownAuthorities: ['cn01duoc2026.b2clogin.com'],
      redirectUri: 'http://localhost:4200',
      postLogoutRedirectUri: 'http://localhost:4200',
    },
  },

  loginRequest: {
    scopes: ['openid', 'profile'],
  },
};
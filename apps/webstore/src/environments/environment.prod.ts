export const environment = {
  production: true,
  envName: 'production',
  debug: false,

  okta: {
    oktaDomain: window['env'] && window['env']['oktaDomain'],
    clientId: window['env'] && window['env']['oktaClientId'],
    idpGoogle: window['env'] && window['env']['idpGoogle'],
    idpFacebook: window['env'] && window['env']['idpFacebook'],
    idpLinkedIn: window['env'] && window['env']['idpLinkedIn'],
  },

  apiUrl: window['env'] ? window['env']['apiUrl'] : 'http://localhost:5000',
};

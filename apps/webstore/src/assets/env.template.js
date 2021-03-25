(function (window) {
  window['env'] = window['env'] || {};

  // Environment variables
  window['env']['apiUrl'] = '${API_URL}';

  window['env']['oktaDomain'] = '${OKTA_DOMAIN}';
  window['env']['oktaClientId'] = '${OKTA_CLIENT_ID}';
  window['env']['idpGoogle'] = '${IDP_GOOGLE}';
  window['env']['idpFacebook'] = '${IDP_FACEBOOK}';
  window['env']['idpLinkedIn'] = '${IDP_LINKEDIN}';
})(this);

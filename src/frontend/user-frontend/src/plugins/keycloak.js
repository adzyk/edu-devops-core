import Keycloak from 'keycloak-js'

const initOptions = {
    url: import.meta.env.VITE_KEYCLOAK_URL,
    realm: 'edu-portal',
    clientId: 'api'
}

const TOKEN_MIN_VALIDITY_SECONDS = 70

export const keycloak = new Keycloak(initOptions)

export async function updateToken() {
  await keycloak.updateToken(TOKEN_MIN_VALIDITY_SECONDS)
  return keycloak.token
}

export function login(onAuthenticatedCallback) {
  keycloak.init({ onLoad: 'login-required' }).then((auth) => {
    if (auth) {
      onAuthenticatedCallback()

      window.onfocus = () => {
        updateToken()
      }
    } else {
      window.location.reload()
    }
  })
}

export function getRole() {
    var currentRole = ''
    keycloak.realmAccess['roles'].map(function(role) {
        if (['USER', 'CURATOR', 'ADMIN'].includes(role) === true) {
            currentRole = role
        }
    })
    return currentRole
}

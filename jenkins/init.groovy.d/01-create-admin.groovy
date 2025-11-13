import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
import hudson.security.HudsonPrivateSecurityRealm
import jenkins.model.Jenkins

Jenkins instance = Jenkins.get()

if (!(instance.getSecurityRealm() instanceof HudsonPrivateSecurityRealm)) {
  HudsonPrivateSecurityRealm realm = new HudsonPrivateSecurityRealm(false)
  if (realm.getUser("admin") == null) {
    realm.createAccount("admin", "admin")
  }
  instance.setSecurityRealm(realm)
}

def strategy = instance.getAuthorizationStrategy()
if (!(strategy instanceof FullControlOnceLoggedInAuthorizationStrategy)) {
  FullControlOnceLoggedInAuthorizationStrategy newStrategy = new FullControlOnceLoggedInAuthorizationStrategy()
  newStrategy.setAllowAnonymousRead(false)
  instance.setAuthorizationStrategy(newStrategy)
}

instance.save()

#!groovy
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Create local user database
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "AdminPass123!")   // admin user
hudsonRealm.createAccount("pipelineuser", "PipelinePass123!") // limited user
instance.setSecurityRealm(hudsonRealm)

// Matrix Authorization Strategy
def strategy = new GlobalMatrixAuthorizationStrategy()

// Full rights for admin
strategy.add(Jenkins.ADMINISTER, "admin")

// Limited rights for pipelineuser
strategy.add(hudson.model.Item.READ, "pipelineuser")
strategy.add(hudson.model.Item.BUILD, "pipelineuser")

// Disable anonymous access
instance.setAuthorizationStrategy(strategy)
instance.save()

println("Security init applied: admin and pipelineuser created.")

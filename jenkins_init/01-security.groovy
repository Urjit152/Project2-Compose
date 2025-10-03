#!groovy
import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.matrixauth.*

// ------------------------------
// Security initialization script
// Creates admin and pipelineuser accounts
// Disables anonymous access
// ------------------------------

def instance = Jenkins.getInstance()

// Create local user database
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","AdminPass123!")          // Admin user
hudsonRealm.createAccount("pipelineuser","PipelinePass123!") // Limited user
instance.setSecurityRealm(hudsonRealm)

// Configure matrix-based security
def strategy = new GlobalMatrixAuthorizationStrategy()

// Grant full permissions to admin
strategy.add(Jenkins.ADMINISTER, "admin")

// Grant limited permissions to pipelineuser
strategy.add(hudson.model.Item.READ, "pipelineuser")
strategy.add(hudson.model.Item.BUILD, "pipelineuser")

// Disable anonymous access
instance.setAuthorizationStrategy(strategy)
instance.save()

println("Security init applied: admin and pipelineuser created, anonymous disabled.")

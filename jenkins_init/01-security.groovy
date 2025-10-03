import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Skip user creation since users already exist
// def hudsonRealm = new HudsonPrivateSecurityRealm(false)
// hudsonRealm.createAccount("admin","AdminPass123!")
// hudsonRealm.createAccount("pipelineuser","PipelinePass123!")
// instance.setSecurityRealm(hudsonRealm)

// Use existing security realm
def hudsonRealm = instance.getSecurityRealm()
instance.setSecurityRealm(hudsonRealm)

// Matrix Authorization Strategy
def strategy = new GlobalMatrixAuthorizationStrategy()

// Grant full permissions to urjit21838321 (admin)
def sidAdmin = "urjit21838321"
strategy.add(Jenkins.ADMINISTER, sidAdmin)
strategy.add(hudson.model.Item.BUILD, sidAdmin)
strategy.add(hudson.model.Item.READ, sidAdmin)
strategy.add(hudson.model.Run.DELETE, sidAdmin)
strategy.add(hudson.model.Computer.BUILD, sidAdmin)

// Grant limited permissions to pipelineuser
def sidPipeline = "pipeline-user"
strategy.add(hudson.model.Item.READ, sidPipeline)
strategy.add(hudson.model.Item.BUILD, sidPipeline)
strategy.add(hudson.model.Run.UPDATE, sidPipeline)

// Disable anonymous access
instance.setAuthorizationStrategy(strategy)
instance.save()

println("Security init applied: permissions set for urjit21838321 and pipeline-user.")

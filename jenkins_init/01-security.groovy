#!groovy
// Jenkins init script to enforce security using existing users
// Users (urjit21838321, pipeline-user) must already exist in Jenkins

import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Use the existing security realm (do not create new users)
def hudsonRealm = instance.getSecurityRealm()
instance.setSecurityRealm(hudsonRealm)

// Configure Matrix Authorization Strategy
def strategy = new GlobalMatrixAuthorizationStrategy()

// Grant full permissions to your admin user
def sidAdmin = "urjit21838321"
strategy.add(Jenkins.ADMINISTER, sidAdmin)
strategy.add(hudson.model.Item.BUILD, sidAdmin)
strategy.add(hudson.model.Item.READ, sidAdmin)
strategy.add(hudson.model.Run.DELETE, sidAdmin)
strategy.add(hudson.model.Computer.BUILD, sidAdmin)

// Grant limited permissions to pipeline-user
def sidPipeline = "pipeline-user"
strategy.add(hudson.model.Item.READ, sidPipeline)
strategy.add(hudson.model.Item.BUILD, sidPipeline)
strategy.add(hudson.model.Run.UPDATE, sidPipeline)

// Disable anonymous access (no permissions granted)
instance.setAuthorizationStrategy(strategy)
instance.save()

println("Security init applied: permissions set for urjit21838321 and pipeline-user, anonymous disabled.")

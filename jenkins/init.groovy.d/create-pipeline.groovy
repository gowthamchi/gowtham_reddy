#!groovy

import jenkins.model.*
import hudson.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Create admin user
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "admin123")
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

instance.save()

// Create a Freestyle job named 'my-freestyle-job'
def jobName = "my-freestyle-job"

if (instance.getItem(jobName) == null) {
    def job = instance.createProject(FreeStyleProject, jobName)

    // Optional: Add a simple shell build step
    def builder = new hudson.tasks.Shell("echo Hello from Freestyle Job!")
    job.getBuildersList().add(builder)

    job.save()
}

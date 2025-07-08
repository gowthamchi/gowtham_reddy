#!groovy

import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

// Create admin user
def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "admin123") // Username: admin, Password: admin123
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

instance.save()

// Create pipeline job named "my-pipeline"
def jobName = "my-pipeline"
if (instance.getItem(jobName) == null) {
    def pipelineJob = instance.createProject(WorkflowJob, jobName)

    def jenkinsfile = new File("/var/jenkins_home/Jenkinsfile")
    def pipelineScript = jenkinsfile.exists() ? jenkinsfile.text : """
        pipeline {
            agent any
            stages {
                stage('Default') {
                    steps {
                        echo 'Hello from default Jenkinsfile!'
                    }
                }
            }
        }
    """

    pipelineJob.setDefinition(new CpsFlowDefinition(pipelineScript, true))
    pipelineJob.save()
}

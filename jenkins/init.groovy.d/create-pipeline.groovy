import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.*
import org.jenkinsci.plugins.workflow.cps.*
import hudson.model.Cause

def instance = Jenkins.getInstance()
def jobName = "my-pipeline-job"

if (instance.getItem(jobName) == null) {
    def job = instance.createProject(WorkflowJob, jobName)

    def pipelineScript = """
        pipeline {
            agent any
            stages {
                stage('Build') {
                    steps {
                        echo 'Building...'
                    }
                }
                stage('Test') {
                    steps {
                        echo 'Testing...'
                    }
                }
                stage('Deploy') {
                    steps {
                        echo 'Deploying...'
                    }
                }
            }
        }
    """.stripIndent()

    def definition = new CpsFlowDefinition(pipelineScript, true)
    job.setDefinition(definition)
    job.save()

    // Trigger first build
    job.scheduleBuild(new Cause.UserIdCause())
}

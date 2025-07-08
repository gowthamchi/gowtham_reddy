import jenkins.model.*
import hudson.model.*
import hudson.model.Cause

def instance = Jenkins.getInstance()
def jobName = "my-freestyle-job"

if (instance.getItem(jobName) == null) {
    def job = instance.createProject(FreeStyleProject, jobName)

    // Add a basic shell command
    def shellStep = new hudson.tasks.Shell("echo Hello from Freestyle Job!")
    job.getBuildersList().add(shellStep)

    // Enable concurrent/manual builds
    job.setConcurrentBuild(true)

    // Save the job config
    job.save()

    // Trigger the first build automatically
    job.scheduleBuild(new Cause.UserIdCause())
}

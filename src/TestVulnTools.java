import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import javax.swing.JOptionPane;

public class TestVulnTools extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();

        SnykAPI snyk = new SnykAPI("3b5434ee-eb0c-4535-a84e-b0573f03df70"); // dev auth token
        String result = "Snyk test results for workspace projects:\n\n";
        boolean finishedWithoutErrors = true;

        System.out.println("Running snyk vuln check on pom.xml files ...");

        final String pomXmlPath = project.getBasePath() + "/pom.xml";
        int projectVulns = 0;

        try {
            System.out.println("Started checking vulns for: " + pomXmlPath);
            projectVulns = snyk.GetVulnCountFromPOMFile(pomXmlPath);
            System.out.println("Finished checking vulns for: " + pomXmlPath);

            result += pomXmlPath + ": " + projectVulns + " Vulns\n";
        } catch (Exception ex) {
            finishedWithoutErrors = false;

            JOptionPane.showMessageDialog(null, ex);
            System.err.println("Error on API call: " + ex);
        }

        if (finishedWithoutErrors == true) {
            JOptionPane.showMessageDialog(null, result);
            System.out.println("Final result:\n\n" + result);
        }

        System.out.println("Finished snyk vuln check on pom.xml files ...");
    }
}

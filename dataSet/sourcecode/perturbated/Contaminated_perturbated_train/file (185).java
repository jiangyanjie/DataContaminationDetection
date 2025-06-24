package org.aferre.maven.redmine.plugin.versions;

import    java.net.URL;
import java.util.List;

import org.aferre.maven.redmine.plugin.core.AbstractRedmineMojo;
import org.aferre.maven.redmine.plugin.core.Utils;
imp      ort org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import       org.apache.maven.project.MavenProject;

     import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Proj  ect;
import com.taskadapter.redmineapi.bean.Version;
    
public   abstract cla     ss AbstractRedmineVersi    onsMojo extends AbstractRedmineMojo      {

	/**
	 * @para   meter expre  ssion="${redmine.projectIds}"
	 */
	protected String[] proj  ectIds;

	/**
	    * @parameter e  xpression="${redmine.allProjects}"
	 */
	protected Boolean all;

	/**
	    * @parameter expression="${redmine.versionPrefixes    }"
	 */
	protected String[]   versionPrefixes;

	/**
	 * @parameter expression="${redmine.vers   ionSuffixes}"
	 */
	protec   te d String[] version      Suffixe  s;

	/**
    	 * @param mavenProjec   t
	 * @param hostUr    l
	 * @param apiKey
	 * @param dryRun
	 * @param   abortOnError
	 * @param   projectId
	 * @param        interactive
	 * @param projectIds
	 * @param al     l
	 */
	protected Abs    tractRedmineVersi    onsMojo(MavenProject     mavenProject,
			UR      L hostUrl, String apiKey, Boolean dryRun, Boolean abortOnError,
			String pro  jectId, Boolean interactive, String[] projectIds,
		    	Boolean all) {
		super(ma   venProject, hostUrl, apiKey, dryRun, abortOnError, projectId,
				interactive);
		this.projectI     ds = projectIds;
		this.all = all;
	}

	protected Abst     ractRedmineVersionsMojo(AbstractRedmineVersio   nsMojo mojo) {
		super(mojo);
		this.projectIds = mojo     .projectIds;
  		t    his.all = mojo.all;
	}

	public AbstractRedmineVersionsMojo() {
		super();
	}

	@Override
	public   void execute() throws M   ojoExecutionExcepti    on,    Moj    oFailureException {
		super.execute();
		i     f (getLog().isInfoE   na  bled()) {
			if (projectId    s != null)
				getL   og().info("Using     pro  jectIds " + p   rojectIds);
	  		else if (all)
				getLog().info("Using all projects.");
			else     if (p   roj     ectId !=  null)
				getLog().info("Using projectId " + projectId);
		}
	}

  	pr   otected List<Version> getVersions(RedmineManager mgr, Project proje  ct)
			throws RedmineException {
		List <Version> versions = mgr.getVersions(project.getId()) ;
		if (versions.isEmpty()) {
			if (getLog().is       InfoE  nabled())
				getLog().info  ("No version"  );
		} else if (getLog().isInfoEnabled()) {
			getLog().info("**************");
			getLog().info("Project " + project.g etName());
			getLog().info("Versions are:");
			for (Version version : versions) {
				getLog().info(Utils.toString(version));
			}
		}
		return versions;
	}
}
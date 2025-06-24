package net.apunch.maplet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.apunch.maplet.Debug.DebugType;
import net.apunch.maplet.api.Application;
import net.apunch.maplet.api.MapletAPI;
import net.apunch.maplet.api.Application.ApplicationIconSize;
import net.apunch.maplet.api.activity.Activity;
import net.apunch.maplet.api.exception.MapletApplicationLoadException;
import net.apunch.maplet.api.exception.MapletResourceLoadException;
import net.apunch.maplet.api.resource.ImageResource;
import net.apunch.maplet.api.resource.ResourceLoader;
import net.apunch.maplet.api.resource.XMLResource;
import net.apunch.maplet.api.util.AttributeSet;

public class ApplicationProvider {
    public static final String APP_DIRECTORY_NAME = "app";
    private static ApplicationProvider instance;

    private final Map<String, ResourceLoader> appResourceLoaders = new HashMap<String, ResourceLoader>();
    private final Map<String, Application> byMainActivityId = new HashMap<String, Application>();
    private final List<Application> applications = new ArrayList<Application>();
    private Constructor<?> applicationConstructor;
    private boolean loaded;

    private ApplicationProvider() {
    }

    public Activity createMainActivity(Application application) {
        Class<? extends Activity> mainActivityClass = application.getMainActivityClass();

        try {
            Activity activity = mainActivityClass.newInstance();
            activity.setApplication(application);
            byMainActivityId.put(activity.getId(), application);

            return activity;
        } catch (Exception e) {
            Debug.log(DebugType.IMMEDIATE, "Could not instantiate Activity for Application '%s'. The constructor must be empty.", application.getId());
            e.printStackTrace();
            return null;
        }
    }

    public Application getApplication(Activity activity) {
        return byMainActivityId.get(activity.getId());
    }

    public Collection<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    public ResourceLoader getResources(Application application) {
        return appResourceLoaders.get(application.getId());
    }

    public void loadApplications() {
        if (loaded) {
            return;
        }

        try {
            applicationConstructor = Application.class.getDeclaredConstructor(String.class, Class.class, Map.class);
            applicationConstructor.setAccessible(true);

            File appFolder = new File(MapletAPI.ROOT_DIRECTORY + APP_DIRECTORY_NAME);
            if (!appFolder.exists()) {
                appFolder.mkdirs();
            }

            for (File subFile : appFolder.listFiles()) {
                if (subFile.isDirectory()) {
                    loadApplication(subFile);
                }
            }

            loaded = true;
            Debug.log(DebugType.IMMEDIATE, "Loaded %s Applications.", applications.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApplicationProvider getInstance() {
        if (instance == null) {
            instance = new ApplicationProvider();
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    private void loadApplication(File applicationFolder) {
        String applicationName = applicationFolder.getName();
        Debug.log(DebugType.IMMEDIATE, "Loading Application '%s'...", applicationName);

        ResourceLoader resourceLoader = new BaseResourceLoader(applicationName);

        // Load app.xml
        Class<? extends Activity> mainActivityClass;
        String mainActivityClassName = null;
        try {
            XMLResource appProperties = resourceLoader.getOrLoadResource(XMLResource.class, "app", APP_DIRECTORY_NAME + File.separator + applicationName);
            AttributeSet attributes = appProperties.getAttributes();
            mainActivityClassName = attributes.getChild("application").getString("main_activity", null);
            if (mainActivityClassName == null) {
                throw new MapletApplicationLoadException("No Main Activity class found for Application '%s'.", applicationName);
            }

            mainActivityClass = (Class<? extends Activity>) Class.forName(mainActivityClassName);
        } catch (ClassNotFoundException e) {
            throw new MapletApplicationLoadException("Unable to locate main Activity class '%s'. Is the package name correct?", mainActivityClassName);
        } catch (MapletResourceLoadException e) {
            Debug.log(DebugType.IMMEDIATE, "Application '%s' is missing app.xml file. Did not load.", applicationName);
            return;
        }

        // Load icons
        File imageFolder = new File(applicationFolder, MapletAPI.RES_DIRECTORY_NAME + File.separator + ResourceLoader.IMAGE_DIRECTORY_NAME);
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        Map<ApplicationIconSize, ImageResource> icons = new HashMap<ApplicationIconSize, ImageResource>();
        for (File subFile : imageFolder.listFiles()) {
            String fileName = subFile.getName();
            String prefix = "app_icon_";
            if (fileName.startsWith(prefix) && fileName.endsWith(".png")) {
                String sizeString = fileName.substring(prefix.length(), fileName.indexOf('.')).toUpperCase();
                for (ApplicationIconSize size : ApplicationIconSize.values()) {
                    if (size.name().equals(sizeString)) {
                        String appIconId = prefix + size.name().toLowerCase();
                        ImageResource image = null;
                        try {
                            image = resourceLoader.getOrLoadResource(ImageResource.class, appIconId);
                        } catch (MapletResourceLoadException e) {
                            e.printStackTrace();
                        }

                        BufferedImage bufferedImage = image.getImage();
                        int imageSize = size.getSize();
                        if (bufferedImage.getWidth() != imageSize || bufferedImage.getHeight() != imageSize) {
                            Debug.log(DebugType.IMMEDIATE, "Invalid Application icon for '%s'. The image must be %sx%s. Using default.", applicationName, imageSize, imageSize);
                            continue;
                        }

                        icons.put(size, image);
                        continue;
                    }
                }
            }
        }

        try {
            Application application = (Application) applicationConstructor.newInstance(applicationName, mainActivityClass, icons);

            // Only cache objects after everything has been successful
            appResourceLoaders.put(applicationName, resourceLoader);
            applications.add(application);

            Debug.log(DebugType.IMMEDIATE, "Loaded Application '%s'...", applicationName);
        } catch (Exception e) {
            throw new MapletApplicationLoadException(e);
        }
    }
}

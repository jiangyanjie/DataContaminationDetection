package com.stundzia.grabImgur;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/*
 *  Created by Alex Stundzia
 *  alex@stundzia.com
 *  twitter: stundzia
 */

public class CrawlSite implements Runnable {

    static final Logger logger = Logger.getLogger(CrawlSite.class);
    
    String siteUrl;
    boolean onlyUnique;
    int minWidth;
    int minHeight;
    File directory;
    final String PAGE_CONSTANT = "/page/";
    final String IMGUR_PREFIX = "http://i.imgur.com/";
    final int SLEEP_BETWEEN_INDEX_MS = 1000;
    final int SLEEP_BETWEEN_IMAGE_MS = 300;
    int threadCount = 0;
    int runningSleepCounter = SLEEP_BETWEEN_INDEX_MS;
    private boolean isCrawling = false;
    
    ImageProperties imageProperties;
    
    int siteCounter = 0;
    int maxThreads = 4;
    int minHammingDistance = 5000;
    
    List<ImageReference> imageList = new ArrayList<ImageReference>();
    
    public CrawlSite(String siteUrl, boolean onlyUnique, int minWidth, int minHeight, File directory) {
        this.siteUrl = siteUrl;
        this.onlyUnique = onlyUnique;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.directory = directory;
        imageProperties = new ImageProperties(maxThreads);
    }
    
    public CrawlSite(String siteUrl, boolean onlyUnique, int hammingDistance, int minWidth, int minHeight, File directory, int maxDownloadThreads) 
    {
        this.siteUrl = siteUrl;
        this.onlyUnique = onlyUnique;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.directory = directory;
        if (maxDownloadThreads<=0)
        {
            this.maxThreads = 1;
        }
        else
        {
            this.maxThreads = maxDownloadThreads;
        }
        imageProperties = new ImageProperties(maxThreads);
    }

    @Override
    public void run() 
    {
        downloadSite(siteUrl);
        
        for (final ImageReference site: imageList)
        {
            // simple spin lock while we're beyond our max threads.
            while (getThreadCount()>maxThreads)
            {
                try 
                {
                    Thread.sleep(500);
                } 
                catch (InterruptedException ex) 
                {
                    logger.error("Interrupted exception sent.", ex);
                }
            }
            
            Runnable downloadImageThread = new Runnable() 
            {
                @Override
                public void run() 
                {
                    incrementThread();
                    downloadImage(site);
                    try 
                    {
                        Thread.sleep(SLEEP_BETWEEN_IMAGE_MS);
                    } 
                    catch (InterruptedException ex) 
                    {
                        logger.error("Interrupted exception sent.", ex);
                    }
                    decrementThread();
                }
            };

            Thread thread = new Thread(downloadImageThread);
            thread.start();
        }
        
        logger.info("Images Count:"+getImageProperties().getImageCount());
        logger.info("Images Found Count:"+getImageProperties().getImagesFoundCount());
        logger.info("Images Rejected Count:"+getImageProperties().getImagesRejectedCount());
        setCrawling(false);
    }

    private void downloadSite(String siteUrl) {
        int pageCount = 0;
        boolean isFinished = false;

        HttpConnectionManager mgr = new MultiThreadedHttpConnectionManager();
        HttpClient httpClient = new HttpClient(mgr);

        while (!isFinished && isCrawling()) 
        {
            if (pageCount>50)
            {
                break;
            }
            GetMethod get = new GetMethod(siteUrl + PAGE_CONSTANT + pageCount);
            get.setFollowRedirects(true);

            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

            try 
            {
                httpClient.executeMethod(get);
                String string = get.getResponseBodyAsString();

                try {
                    logger.info("Current page #"+pageCount);
                    String[] imageNames = StringUtils.substringsBetween(string, "src=\"//i.imgur.com/", "\" title=");
                    for (String imageName : imageNames) {
                        if (imageName.endsWith("b.jpg"))
                        {
                            imageName = imageName.replace("b.jpg", ".jpg");
                            ImageReference imgRef = new ImageReference(imageName,"jpg",IMGUR_PREFIX+imageName);
                            
                            if (!imageList.contains(imgRef))
                            {
                                imageList.add(imgRef);
                                imageProperties.incrementImagesFoundCount();
                            }
                            else
                            {
                                imageProperties.incrementImageRejectedCount();
                            }
                        }
                        else if (imageName.endsWith("b.gif"))
                        {
                            imageName = imageName.replace("b.gif", ".gif");
                            ImageReference imgRef = new ImageReference(imageName,"gif",IMGUR_PREFIX+imageName);
                            if (!imageList.contains(imgRef))
                            {
                                imageList.add(imgRef);
                                imageProperties.incrementImagesFoundCount();
                            }
                            else
                            {
                                imageProperties.incrementImageRejectedCount();
                            }
                        }
                        else if (imageName.endsWith("b.png"))
                        {
                            imageName = imageName.replace("b.png", ".png");
                            ImageReference imgRef = new ImageReference(imageName,"png",IMGUR_PREFIX+imageName);
                            if (!imageList.contains(imgRef))
                            {
                                imageList.add(imgRef);
                                imageProperties.incrementImagesFoundCount();
                            }
                            else
                            {
                                imageProperties.incrementImageRejectedCount();
                            }
                        }
                    }
                    
                    if (runningSleepCounter>SLEEP_BETWEEN_INDEX_MS)
                    {
                        runningSleepCounter-=500;
                    }
                    pageCount++;
                } 
                catch (NullPointerException eof) 
                {
                    if (string.contains("Imgur is over capacity"))
                    {
                        logger.warn("Imgur is over capacity. Slowing down index rate.");
                        runningSleepCounter += 1000;
                    }
                    else if (string.contains("No more images were found."))
                    {
                        isFinished = true;
                    }
                    else
                    {
                        isFinished = true;
                        logger.warn("Exception Thrown! "+string, eof);
                    }
                }

            }
            catch (IOException ex) 
            {
                logger.error("Some error occurred. Setting this job to finished.", ex);
                isFinished = true;
            }
            
            try 
            {
                Thread.sleep(runningSleepCounter);
            } 
            catch (InterruptedException ex) 
            {
                logger.error("Thread recevied an interrupted exception...", ex);
                isFinished = true;
            }
        }
    }

    private void downloadImage(ImageReference site) 
    {
        String pathSep = System.getProperty("file.separator");
        File fileToWrite = new File(directory.toString()+pathSep+site.getFileName());
        File rejectedFile = new File(directory.toString()+pathSep+"Rejected"+pathSep+site.getFileName());
        
        if (!fileToWrite.exists() && !rejectedFile.exists())
        {
            try 
            {
                BufferedImage downloadedImage = toolkitImage(site.getURL());

                if (downloadedImage==null)
                {
                    throw new IOException("Couldnt process image from toolkit.");
                }
                
                if (downloadedImage.getWidth()>=minWidth && downloadedImage.getHeight()>=minHeight)
                {
                    boolean similarToExistingImage = imageProperties.similarImageExists(downloadedImage);
                    if (!onlyUnique)
                    {
                        similarToExistingImage = false;
                    }

                    if (!similarToExistingImage)
                    {
                        String fileExtension = site.getFileExtension();
                        fileToWrite.getParentFile().mkdirs();
                        ImageIO.write(downloadedImage, fileExtension, fileToWrite);
                        imageProperties.incrementImageCount();
                    }
                    else
                    {
                        imageProperties.incrementImageRejectedCount();
                    }
                }
                else
                {
                    String fileExtension = site.getFileExtension();
                    rejectedFile.getParentFile().mkdirs();
                    ImageIO.write(downloadedImage, fileExtension, rejectedFile);
                    imageProperties.incrementImageRejectedCount();
                }
            } 
            catch (IOException e) 
            {
                logger.error("Exception Thrown When Downloading Image "+site.getFileName());
                logger.error(e);
            }
        }
    }

    private synchronized int getThreadCount() 
    {
        return threadCount;
    }
    
    private synchronized void decrementThread() 
    {
        threadCount--;
    }
    
    private synchronized void incrementThread() 
    {
        threadCount++;
    }

    private BufferedImage toolkitImage(String urlString)
    {
        try
        {
        int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
        ColorModel RGB_OPAQUE =
            new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);

        URL url = new URL(urlString);
        Image img = Toolkit.getDefaultToolkit().createImage(url);

        PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
        pg.grabPixels();
        int width = pg.getWidth(), height = pg.getHeight();

        if (width==0 || height==0)
        {
            throw new IOException("Invalid height or width for image.");
        }
        
        DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
        BufferedImage bi = new BufferedImage(RGB_OPAQUE, raster, false, null);
        return bi;
        }
        catch (Exception e)
        {
            logger.warn("Issues translating to toolkit image.", e);
        }
        return null;
    }
    
    public ImageProperties getImageProperties()
    {
        return imageProperties;
    }
    public boolean isCrawling()
    {
        return isCrawling;
    }
    
    public void setCrawling(boolean isCrawling)
    {
        this.isCrawling = isCrawling;
    }
    
}

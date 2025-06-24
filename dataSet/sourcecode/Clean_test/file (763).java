package de.ar56te876mis.CraftMinecartControl.Control;

import de.ar56te876mis.CraftMinecartControl.Control.Books.CraftBookJob;
import de.ar56te876mis.CraftMinecartControl.MCLogger;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Book.BookJob;
import de.ar56te876mis.MinecartControl.Book.BookJobStatus;
import de.ar56te876mis.MinecartControl.Manager.BookControlManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CraftBookControlManager implements BookControlManager {

    private HashMap<Minecart, List<BookJob>> minecartBookJobMap = new HashMap<Minecart, List<BookJob>>();
    private boolean enable = true;

    public void execute(Minecart minecart, MinecartControl mC, String r) {
        if (!enable) {
            return;
        }
        List<BookJob> bookJobs = minecartBookJobMap.get(minecart);

        if (bookJobs == null) {
            return;
        }

        for (BookJob bj : bookJobs) {
            if (bj.getReferenz().equalsIgnoreCase(r) && bj.getStatus() == BookJobStatus.TODO) {
                MCLogger.info("execute " + bj.getBookJobName() + " " + bj.getJob() + ".");
                mC.getJobControlManager().execute(minecart, (bj.getBookJobName() + " " + bj.getJob()).split(" "));
                bj.setStatus(BookJobStatus.FINISH);
            }
        }
    }

    public void registerBookJobs(BookMeta book, Minecart minecart, Player player) {
        if (!enable) {
            return;
        }

        if (minecartBookJobMap.get(minecart) != null){
            ItemStack bookItemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
            bookItemStack.setItemMeta(getBook(minecart));
            minecart.getLocation().getWorld().dropItem(minecart.getLocation(), bookItemStack);
        }
        
        List<BookJob> bookJobs = new ArrayList<BookJob>();
        List<String> pages = book.getPages();

        for (int x = 0; x < pages.size(); x++) {
            String[] lines = pages.get(x).split("\n");
            for (int y = 0; y < lines.length; y++) {
                if (!lines[y].contains("_")) {
                    continue;
                }
                String[] lineSplit = lines[y].split("_", 2);
                String[] job = {"", ""};
                if (lineSplit[1].contains(" ")) {
                    job = lineSplit[1].split(" ", 2);
                } else {
                    job[0] = lineSplit[1];
                }
                System.out.println(job[1]);
                if (job[1].contains("§")){
                    job[1] = job[1].split("§", 2)[0];
                }
                String bookJobName = lineSplit[0];

                BookJob bj = new CraftBookJob(bookJobName, job[0], job[1]);
                bookJobs.add(bj);
                MCLogger.info("add Bookjob: " + bj.toString());
                
                if (!player.hasPermission("minecartcontrol.bookjobs." + lineSplit[0].toLowerCase())) {
                    bj.setStatus(BookJobStatus.NO_PERMISSIONS);
                }
            }
        }
        minecartBookJobMap.put(minecart, bookJobs);
    }

    public void clearBookJobs(Minecart minecart) {
        minecartBookJobMap.remove(minecart);
    }

    public void enable() {
        enable = true;
    }

    public void disable() {
        enable = false;
    }

    public boolean isEnable() {
        return enable;
    }

    public List<BookJob> getBookJobs(Minecart minecart) {
        return minecartBookJobMap.get(minecart);
    }
    
    public BookMeta getBook(Minecart minecart){
        BookMeta book = (BookMeta) new ItemStack(Material.WRITTEN_BOOK).getItemMeta();
        List<BookJob> bookJobs = minecartBookJobMap.get(minecart);
        if (bookJobs == null){
            return book;
        }
        StringBuilder page = new StringBuilder();
        StringBuilder job;
        for (BookJob bj : bookJobs){
            job = new StringBuilder();
            job.append(bj.getBookJobName());
            job.append('_');
            job.append(bj.getReferenz());
            job.append(' ');
            job.append(bj.getJob());
            job.append(' ');
            job.append(bj.getStatus().getColor());
            job.append('#');
            job.append(bj.getStatus().toString());
            job.append("\n");
            if (page.length() + job.length() >= 246){
                book.addPage(page.toString());
                page = new StringBuilder();
            }
            page.append(job.toString());
        }
        book.addPage(page.toString());
        return book;
    }
}

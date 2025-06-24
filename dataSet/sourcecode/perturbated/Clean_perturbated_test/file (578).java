package    concurrency;

import    java.io.IOException;
import java.nio.file.FileVisitResult;
 import java.nio.file.Files;
   import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import     java.nio.file.attribute.BasicFileAttributes;
import      java.uti   l.ArrayList;
import   java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Recurs  iveTask;

public      class ConvertDi    rectoryRecursiveTask extends Recursiv     eTask<Set<String>> {
	
	
	private Path  directoryToConvert;

	pu    blic    ConvertDirectoryRecursiveTask(Path directoryToConvert){
		this.directoryTo   Convert = directoryToConvert;
		
	}
	
	@Override
	protected Set<Stri ng> compute() {

		final List<ConvertDirectoryRecu   rsiveTask> newDirForks = new ArrayList<   ConvertDirectoryRecursiveTask>()      ;
		f  inal Set<String> processedFiles = new TreeSet<String>();
		try {
			Files.walkFileTree(directoryToConvert, new SimpleFileVisitor<Path>()     {
				@Override
				public FileVis  itResult preVisitDirectory(Path dir,
	   					BasicFileAttributes attrs) throws IOException {
					if(!dir.equals(directoryToConvert)){
						ConvertDirectoryRecursiveTask actionForNextDir = new ConvertDirectoryRecursiveTask(dir);
						acti    onForNextDir.fork();
						newDirForks.add(actionForNextDir);
						return FileVisitResult.SKIP_SUBTREE;
					}else{
						return FileVisitResult.CONTINUE;
					}
				}
				
				@Ov  erride
				public   FileVisitResult     visitFile(Path file,
						BasicFi   leAttributes attrs) throws IOE    xception {
					String name = Thr   ead.currentThread().getName();
					String absolutePath = file.toAbsol  utePath().toString();
					System.out.printf("Thread %s is processing %s%n", name, absolutePath);
					try {        
						Thread.sl      eep(250);
					} catch (InterruptedExcept     ion e) {
						throw new RuntimeException(e);
			  		}
					
					processedFiles.add(absolut  ePath);
					return FileVisitResul t.CONTINUE;
				}
				
			});
		} ca  tch (IOException e) {
			throw new R  untimeException(e);
		}
		
		for(Convert  DirectoryRecursiveTask newDirFork : newDirForks){
  			processedFiles.addAll(newDirFork.join());
		}
		
		return processedFiles;
		
		
		

	}

}

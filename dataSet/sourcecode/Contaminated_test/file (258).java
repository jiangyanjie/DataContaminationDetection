package net.afterlifelochie.cogbot.database;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

import com.almworks.sqlite4java.SQLiteConnection;

public class DatabaseClosure extends Thread {

	private static class DeferredDBTask {
		private Object[] runArgs;
		private DatabaseTaskResult result;

		public final Object theLock;
		public IDatabaseTask theTask;

		public DeferredDBTask(IDatabaseTask theTask, Object[] runArgs) {
			this.theTask = theTask;
			this.runArgs = runArgs;
			this.theLock = new Object();
		}

		public void run(final SQLiteConnection database) {
			try {
				this.result = new DatabaseTaskResult(theTask.run(database, this.runArgs));
			} catch (Exception exception) {
				this.result = new DatabaseTaskResult(exception);
			} finally {
				synchronized (theLock) {
					theLock.notifyAll();
				}
			}
		}

		public DatabaseTaskResult getDatabaseTaskResult() {
			return result;
		}
	}

	private final String databaseName;
	private final File databaseFile;
	private SQLiteConnection databaseConnection;
	private LinkedBlockingQueue<DeferredDBTask> runQueue;
	private boolean isAlive = true;

	protected DatabaseClosure(String identifier, File dbFile) {
		this.databaseName = identifier;
		this.databaseFile = dbFile;
		this.runQueue = new LinkedBlockingQueue<DeferredDBTask>();
	}

	public void shutdown() {
		isAlive = false;
		this.databaseConnection.dispose();
	}

	public String getClosureName() {
		return databaseName;
	}

	public DatabaseTaskResult queueAndWaitIndefinitely(IDatabaseTask theTaskOf, Object[] runArgs)
			throws InterruptedException {
		if (!isAlive)
			throw new RuntimeException("Cannot queue job; the database has been shut down.");
		DeferredDBTask theTask = new DeferredDBTask(theTaskOf, runArgs);
		runQueue.put(theTask);
		synchronized (theTask.theLock) {
			theTask.theLock.wait();
		}
		return theTask.getDatabaseTaskResult();
	}

	public DatabaseTaskResult queueAndWaitFor(IDatabaseTask theTaskOf, Object[] runArgs, long waitTime)
			throws InterruptedException {
		if (!isAlive)
			throw new RuntimeException("Cannot queue job; the database has been shut down.");
		DeferredDBTask theTask = new DeferredDBTask(theTaskOf, runArgs);
		runQueue.put(theTask);
		synchronized (theTask.theLock) {
			theTask.theLock.wait(waitTime);
		}
		return theTask.getDatabaseTaskResult();
	}

	public void queueAndForget(IDatabaseTask theTaskOf, Object[] runArgs) throws InterruptedException {
		if (!isAlive)
			throw new RuntimeException("Cannot queue job; the database has been shut down.");
		DeferredDBTask theTask = new DeferredDBTask(theTaskOf, runArgs);
		runQueue.put(theTask);
	}

	@Override
	public void run() {
		this.setName(new StringBuilder().append("Closure for database ").append(databaseName).append(".").toString());
		this.databaseConnection = new SQLiteConnection(this.databaseFile);
		try {
			while (isAlive) {
				DeferredDBTask theTask = runQueue.take();
				if (theTask == null)
					continue;
				theTask.run(databaseConnection);
			}
		} catch (InterruptedException interrupt) {
			// TODO something smart?
		}
	}
}

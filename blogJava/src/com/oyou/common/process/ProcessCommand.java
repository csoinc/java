package com.oyou.common.process;

import java.io.IOException;

import org.apache.log4j.Logger;

public class ProcessCommand {
	private static final Logger logger = Logger.getLogger(ProcessCommand.class);

	Process proc;
	StreamGobblerThread errThread;
	StreamGobblerThread outThread;

	public Process getProc() {
		return proc;
	}

	public void setProc(Process proc) {
		this.proc = proc;
	}

	public boolean runCommand(String command) {
		boolean res = false;

		try {
			proc = Runtime.getRuntime().exec(command);

			errThread = new StreamGobblerThread(proc.getErrorStream(), StreamGobblerThread.STDERR);
			errThread.start();

			outThread = new StreamGobblerThread(proc.getInputStream(), StreamGobblerThread.STDOUT);
			outThread.start();

			int exitVal = proc.waitFor();
			if (exitVal != 0) {
				logger.fatal("Error on execute command: " + command);
			} else {
				logger.info("Sucess on execute command: " + command);
				res = true;
			}		
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
		return res;
	}

	public boolean runCommand(String[] commands) {
		boolean res = false;
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < commands.length; i++) {
				sb.append(commands[i] + " ");
			}
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec(commands);
			//TimeoutThread thread = new TimeoutThread(this);
			//thread.start();

			errThread = new StreamGobblerThread(proc.getErrorStream(), StreamGobblerThread.STDERR);
			errThread.start();

			outThread = new StreamGobblerThread(proc.getInputStream(), StreamGobblerThread.STDOUT);
			outThread.start();

			int exitVal = proc.waitFor();
			if (exitVal != 0) {
				logger.fatal("Error on execute command: " + sb.toString());
			} else {
				logger.info("Sucess on execute command: " + sb.toString());
				res = true;
			}
			//thread.interrupt();			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: " + e.getMessage());

		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
		return res;
	}

	public static void main(String[] args) {
		ProcessCommand command = new ProcessCommand();
		command.runCommand("convert -resize 100x75 C:/temp/test_01.jpg C:/temp/test_01_S.jpg");
	}

}

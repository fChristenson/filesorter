package se.fidde.filesorter.main;

import java.io.File;

public class Main {

	private static int desiredAmountOfFilesInDirectory = 50;

	public static void main(String[] args) {

		try {
			
			validateNumberOfArgs(args);
			
			String path = args[0];
			File root = new File(path);
			
			validateRoot(root);
			
			System.out.println("Sorting files...");
			
			sortFiles(path, root);
			
			System.out.println("Files sorted!");
			System.exit(0);

		} catch (Exception e) {
			System.out.println("There was an error: " + e.getMessage());
			System.exit(0);
		}
	}

	private static void sortFiles(String path, File root) {
		File[] rootFiles = root.listFiles();
		
		int filesMoved = 0;
		int newDirectoryName = findDirectoryWithBiggestIntName(rootFiles) + 1;
		
		for(File file : rootFiles){
			if(file.isDirectory())
				continue;
			
			createNewDirectory(path, newDirectoryName);
			
			file.renameTo(new File(path + "\\" + newDirectoryName + "\\" + file.getName()));
			++filesMoved;
			
			if(filesMoved >= desiredAmountOfFilesInDirectory){
				++newDirectoryName;
				filesMoved = 0;
			}
		}
	}

	private static int findDirectoryWithBiggestIntName(File[] rootFiles) {
		int biggestIntNameFound = 0;
		
		for(File file : rootFiles){
			if(file.isDirectory()){
				
				try {
				
					int directoryNameAsInt = Integer.parseInt(file.getName());
					
					if(directoryNameAsInt > biggestIntNameFound)
						biggestIntNameFound = directoryNameAsInt;
					
				} catch (Exception e) {

				}
			}
		}
		return biggestIntNameFound;
	}

	private static boolean createNewDirectory(String path, int newDirectoryName) {
		File directory = new File(path + "\\" + newDirectoryName);
		if(directory.exists())
			return false;
		
		boolean directoryWasCreated = directory.mkdir();
		
		checkIfNewDirectoryWasCreated(directoryWasCreated);
		return true;
	}

	private static void checkIfNewDirectoryWasCreated(
			boolean directoryWasCreated) {
		if(!directoryWasCreated){
			System.out.println("Could not create new directory in root!");
			System.exit(0);
		}
	}

	private static void validateRoot(File root) {
		if(!root.exists()){
			System.out.println("Invalid root! Directory does not exist!");
			System.exit(0);
		
		} else if(!root.isDirectory()){
			System.out.println("Invalid root! Provided file is not a directory!");
			System.exit(0);
		}
	}

	private static void validateNumberOfArgs(String[] args) {
		if(args.length != 1){
			System.out.println("Invalid amount of arguments! Only provide root path!");
			System.exit(0);
		}
	}

}

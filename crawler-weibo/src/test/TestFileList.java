package test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class TestFileList {

	/**
	 * TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("account");
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				System.out.println(f.getName());
			}
		}

		System.out.println("≈≈–Ú∫Û...................");
		File[] files = file.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				String f1Name = f1.getName().replaceAll("\\d*_", "")
						.replaceAll(".properties", "");
				String f2Name = f2.getName().replaceAll("\\d*_", "")
						.replaceAll(".properties", "");
				return f1Name.compareTo(f2Name);
			}
		});

		for (File f : files) {
			System.out.println(f.getName());
		}
	}

}

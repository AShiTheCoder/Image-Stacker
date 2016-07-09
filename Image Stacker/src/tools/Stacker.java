package tools;

import java.io.*;
import java.util.Scanner;

public class Stacker {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the folder path of the images to stack:");
		String path1 = reader.nextLine();
		File[] files = new File(path1).listFiles();
		System.out.println("Enter the folder path where you want the final image:");
		String path2 = reader.nextLine();
		reader.close();
		stack(files, path1, path2);
	}
	
	public static void stack(File[] images, String path1, String path2) throws IOException{
		int counter = 0;
		boolean bad = true;
		Scanner s = new Scanner(images[counter]);
		while (bad){
			if (!images[counter].isHidden()){
				s = new Scanner(images[counter]);
				bad = !bad;
			} else counter++;
		}
		
		s.nextLine();
		int width = s.nextInt();
		int height = s.nextInt();
		int[][] redSums = new int[height][width];
		int[][] greenSums = new int[height][width];
		int[][] blueSums = new int[height][width];
		s.close();
		
		for (int i = counter; i < images.length; i++){
			Scanner sc = new Scanner(images[i]);
			sc.nextLine();
			sc.nextLine();
			for (int j = 0; j < height * width; j++){
				sc.nextLine();
				redSums[j / width][j % width] += sc.nextInt();
				greenSums[j / width][j % width] += sc.nextInt();
				blueSums[j / width][j % width] += sc.nextInt();
			}
			sc.close();
		}
		
		for (int i = 0; i < height * width; i++){
			redSums[i / width][i % width] /= images.length;
			greenSums[i / width][i % width] /= images.length;
			blueSums[i / width][i % width] /= images.length;
		}
		
		String temp = path1.substring(0, path1.length() - 1);
		String fileName = path1.substring(temp.lastIndexOf("/") + 1, path1.length() - 1) + "_good.ppm/";
		File f = new File(path2 + fileName);
		
		PrintStream printer = new PrintStream(f);
		printer.println("P3");
		printer.println(width + " " + height);
		printer.println("255");
		for (int i = 0; i < height * width; i++){
			printer.println(redSums[i / width][i % width] + " " + 
							greenSums[i / width][i % width] + " " + 
							blueSums[i / width][i % width]);
		}
		printer.close();
		
		f.getParentFile().mkdirs(); 
		f.createNewFile();
		System.out.println("Stacking done. Clean image has name \"" + fileName.substring(0, fileName.length() - 1) + "\"");
	}

}
